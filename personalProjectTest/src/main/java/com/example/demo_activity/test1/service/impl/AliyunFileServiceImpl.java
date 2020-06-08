package com.example.demo_activity.test1.service.impl;

import com.example.demo_activity.test1.model.AliyunFile;
import com.example.demo_activity.test1.dao.AliyunFileMapper;
import com.example.demo_activity.test1.service.IAliyunFileService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo_activity.test1.tips.SuccessTip;
import com.example.demo_activity.test1.utils.AliYunUploadFile;
import com.example.demo_activity.test1.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.Timestamp;
import java.util.*;
import java.util.concurrent.*;

/**
 * <p>
 * 阿里云存放文件表 服务实现类
 * </p>
 *
 * @author yongli
 * @since 2020-05-30
 */
@Service
public class AliyunFileServiceImpl extends ServiceImpl<AliyunFileMapper, AliyunFile> implements IAliyunFileService {
    private final static Logger LOGGER = LoggerFactory.getLogger(AliyunFileServiceImpl.class);


    @Transactional
    @Override
    public SuccessTip uploadFile(MultipartFile file) throws Exception {
        ArrayList<File> fileList = new ArrayList<>();
        long start = System.currentTimeMillis();
        FileUtils.zipDecompression(file, fileList);
        long end = System.currentTimeMillis();
        LOGGER.info("解压压缩包时间耗时:{}s", (end - start) / 1000);
        //采用单个线程一个个上传文件，上传111个文件大概需要12s左右
        //zipHandleTemplate(fileList);
        //采用多线程上传（采用的是实现Runnable的方式），上传111个文件大概需要6s左右
        //threadMethod(fileList);
        //采用多线程上传（采用Callable的方式），上传111个文件大概需要3.5秒左右
        threadByCallable(fileList);
        return new SuccessTip("上传成功");
    }

    private void threadByCallable(ArrayList<File> fileList) throws Exception {
        //线程安全
        List<AliyunFile> aliFileList = Collections.synchronizedList(new ArrayList<>());
        //一个线程处理20条数据
        int count = 15;
        //集合的长度
        int listSize = fileList.size();
        //线程数
        int runSize = (listSize / count) + 1;
        //存放每个线程的数据
        List<File> files = null;
        //创建一个线程池，数量和开启线程的数量一样
        ExecutorService pool = Executors.newFixedThreadPool(runSize);
        List<Future<Integer>> futures = new ArrayList<Future<Integer>>(runSize);
        long start2 = System.currentTimeMillis();
        for (int i = 0; i < runSize; i++) {
            if ((i + 1) == runSize) {
                int startIndex = (i * count);
                int endIndex = listSize;
                files = fileList.subList(startIndex, endIndex);
            } else {
                int startIndex = i * count;
                int endIndex = (i + 1) * count;
                files = fileList.subList(startIndex, endIndex);
            }
            final List<File> newFiles = files;
           /* Callable<Integer> callable = new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    uploadFileToAli(newFiles, aliFileList);
                    return 1;
                }
            };
            Future < Integer > submit = pool.submit(callable);
            futures.add(submit);*/
            //用Lambda表达式,下面的task就相当于上文的callable对象
            Callable<Integer> task = () ->
            {
                uploadFileToAli(newFiles, aliFileList);
                return 1;
            };
            Future<Integer> submit = pool.submit(task);
            futures.add(submit);
        }
        //关闭线程池
        pool.shutdown();
        while (true) {
            if (pool.isTerminated()) {
                System.out.println("数据已经添加到集合里面去了！");
                break;
            }
            Thread.sleep(50);
        }
        long end2 = System.currentTimeMillis();
        LOGGER.info("上传操作耗时:{}s", (end2 - start2) / 1000);
        if (aliFileList.size() > 0) {
            this.insertBatch(aliFileList);
        }
    }

    /**
     * 多线程往阿里云服务器上传文件(采用的是Runnable的方式)
     *
     * @param fileList
     */
    private void threadMethod(ArrayList<File> fileList) throws Exception {
        //线程安全
        List<AliyunFile> aliFileList = Collections.synchronizedList(new ArrayList<>());
        //一个线程处理20条数据
        int count = 20;
        //集合的长度
        int listSize = fileList.size();
        //线程数
        int runSize = (listSize / count) + 1;
        //存放每个线程的数据
        List<File> files;
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < runSize; i++) {
            if ((i + 1) == runSize) {
                int startIndex = (i * count);
                int endIndex = listSize;
                files = fileList.subList(startIndex, endIndex);
            } else {
                int startIndex = i * count;
                int endIndex = (i + 1) * count;
                files = fileList.subList(startIndex, endIndex);
            }
            final List<File> newFiles = files;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    uploadFileToAli(newFiles, aliFileList);
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
            //thread.join();
        }
        long end1 = System.currentTimeMillis();
        LOGGER.info("当前集合的长度是:{}", aliFileList.size());
        while (true) {
            if (String.valueOf(fileList.size()).equals(String.valueOf(aliFileList.size()))) {
                break;
            } else {
                try {
                    TimeUnit.MILLISECONDS.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        LOGGER.info("上传操作耗时:{}s", (end1 - start1) / 1000);
        long start2 = System.currentTimeMillis();
        //主线程进行插入操作
        this.insertBatch(aliFileList);
        long end2 = System.currentTimeMillis();
        LOGGER.info("插入操作耗时:{}s", (end2 - start2) / 1000);
    }

    private void uploadFileToAli(List<File> fileList, List<AliyunFile> aliFileList) {
        if (fileList != null) {
            long start1 = System.currentTimeMillis();
            for (File file : fileList) {
                String realPath = "reportTemplate" + "/" + String.valueOf(System.currentTimeMillis());
                String aliPath = realPath + "/" + file.getName();
                //上传到阿里云oss
                try {
                    AliYunUploadFile.uploadFile(file.getAbsolutePath(), aliPath);
                } catch (IOException e) {
                    e.printStackTrace();
                    //如果上传失败，则不new一个新的记录对象，直接跳出循环，再次重新循环
                    continue;
                }
                AliyunFile aliyunFile = new AliyunFile();
                Date date = new Date();
                aliyunFile.setFileName(file.getName());
                aliyunFile.setFilePath(aliPath);
                aliyunFile.setCreateTime(date);
                aliyunFile.setStatus(1);
                aliFileList.add(aliyunFile);
                //获得阿里云url链接
                String alyUrl = AliYunUploadFile.getAlyUrl(aliPath);
                //LOGGER.info("阿里云连接为:{}", alyUrl);
                //LOGGER.info("本地路径为:{}", file.getAbsolutePath());
                //LOGGER.info("阿里云路径为:{}", aliPath);
                //关于文档路径，需要调用在线office上传文件来获取路径
                //删除临时文件
                long start2 = System.currentTimeMillis();
                FileUtils.deleteFile(file);
                long end2 = System.currentTimeMillis();
                //LOGGER.info("文件删除操作耗时:{}s", (end2 - start2) / 1000);
            }
            long end1 = System.currentTimeMillis();
            LOGGER.info("单个线程上传操作耗时:{}s", (end1 - start1) / 1000);
        }

    }

    /**
     * 压缩包模式上传模板
     *
     * @param list 存放文件的集合
     */
    private void zipHandleTemplate(List<File> list) {
        try {
            if (list != null) {
                String realPath = "reportTemplate" + File.separator + String.valueOf(System.currentTimeMillis());
                Date date = new Date();
                ArrayList<AliyunFile> aliyunFileList = new ArrayList<>();
                long start1 = System.currentTimeMillis();
                for (File file : list) {
                    AliyunFile aliyunFile = new AliyunFile();
                    String aliPath = realPath + File.separator + file.getName();
                    //上传到阿里云oss
                    AliYunUploadFile.uploadFile(file.getAbsolutePath(), aliPath);
                    aliyunFile.setFileName(file.getName());
                    aliyunFile.setFilePath(aliPath);
                    aliyunFile.setCreateTime(date);
                    aliyunFile.setStatus(1);
                    aliyunFileList.add(aliyunFile);
                    //获得阿里云url链接
                    String alyUrl = AliYunUploadFile.getAlyUrl(aliPath);
                    LOGGER.info("阿里云连接为:{}", alyUrl);
                    LOGGER.info("本地路径为:{}", file.getAbsolutePath());
                    LOGGER.info("阿里云路径为:{}", aliPath);
                    //关于文档路径，需要调用在线office上传文件来获取路径
                    //删除临时文件
                    FileUtils.deleteFile(file);
                }
                long end1 = System.currentTimeMillis();
                LOGGER.info("上传操作耗时:{}", (end1 - start1) / 1000, "s");
                long start2 = System.currentTimeMillis();
                if (aliyunFileList.size() > 0) {
                    this.insertBatch(aliyunFileList);
                }
                long end2 = System.currentTimeMillis();
                LOGGER.info("插入操作耗时:{}", (end2 - start2) / 1000, "s");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 多文件上传用的io模式
     *
     * @param files
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public SuccessTip multiUploadFile(List<MultipartFile> files) {
        //封装一个方法
        return copyFileByIo(files);
    }

    private SuccessTip copyFileByIo(List<MultipartFile> files) {
        long start = System.currentTimeMillis();
        for (MultipartFile file : files) {
            //获取文件的名称
            String originalFilename = file.getOriginalFilename();
            //拼接要保存的文件路径
            String destinationPath = "E:\\destination" + File.separator + originalFilename;
            File destinationFile = new File(destinationPath);
            try {
                file.transferTo(destinationFile);
            } catch (IOException e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new SuccessTip(1, "上传失败", null);
            }
        }
        long end = System.currentTimeMillis();
        LOGGER.info("多文件上传耗时:{}s,起始时间是:{},截止时间是:{}", (end - start) / 1000, start, end);
        return new SuccessTip("上传成功");
    }


    /**
     * 多文件上传用的是NIO模式
     *
     * @param files
     * @return
     */
    @Transactional
    @Override
    public SuccessTip multiUploadFileByNIO(List<MultipartFile> files) {
        //封装一个方法
        return copyFileByNio(files);
    }

    private SuccessTip copyFileByNio(List<MultipartFile> files) {
        long start = System.currentTimeMillis();
        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            String destinationPath = "E:\\destination" + File.separator + originalFilename;
            FileInputStream fis = null;
            FileOutputStream fos = null;
            FileChannel inChannel = null;
            FileChannel outChannel = null;
            try {
                //FileInputStream继承了FileInputStream，所以可以强转（父转子，类似于Object转String）
                fis = (FileInputStream) file.getInputStream();
                fos = new FileOutputStream(new File(destinationPath));
                inChannel = fis.getChannel();
                outChannel = fos.getChannel();
         /*   int capacity = 1024;
            ByteBuffer buffer = ByteBuffer.allocate(capacity);
            while (true) {
                buffer.clear();
                if (inChannel.read(buffer) != -1) {
                    break;
                }
                buffer.flip();
                outChannel.write(buffer);
            }*/
                //inChannel.transferTo的方法类似于上面的方法，只不过底层实现了read方法
                inChannel.transferTo(0, inChannel.size(), outChannel);
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new SuccessTip(1, "上传失败", null);
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (inChannel != null) {
                    try {
                        inChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (outChannel != null) {
                    try {
                        outChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        long end = System.currentTimeMillis();
        LOGGER.info("多文件上传耗时:{}s,起始时间是:{},截止时间是:{}", (end - start) / 1000, start, end);
        return new SuccessTip("上传成功");
    }
}
