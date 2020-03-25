package com.example.demo_activity.test1.utils;



import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @Author: yongl
 * @DATE: 2020/3/25 15:59
 */

public class FileImportExportUtilss {
    /**
     * 文件解压
     * @param file
     */
    public static void zipDecompression(MultipartFile file){
        /*
         *创建临时文件夹
         * 解压文件
         */
        String fileName = file.getOriginalFilename();
        String path = "d:/zip/";
        File dir = new File(path);
        dir.mkdirs();
        String filePath = "d:/test/";
        File fileDir = new File(filePath);
        fileDir.mkdirs();
        File saveFile = new File(fileDir, fileName);//将压缩包解析到指定位置
        List<String> list = new ArrayList<>();
        try {
            file.transferTo(saveFile);
            String newFilePath = filePath + fileName;
            File zipFile = new File(newFilePath);
            unZipFiles(zipFile, path);//解压文件，获取文件路径

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("解压执行失败");
        }
        //程序结束时，删除临时文件
        deleteFiles(filePath);//删除压缩包文件夹
        deleteFiles(path);//删除解压文件夹**
    }

    private static void deleteFiles(String filePath) {
        File file = new File(filePath);
        if ((!file.exists()) || (!file.isDirectory())) {
            System.out.println("file not exist");
            return;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (filePath.endsWith(File.separator)) {
                temp = new File(filePath + tempList[i]);
            }
            else {
                temp = new File(filePath + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                deleteFiles(filePath + "/" + tempList[i]);
            }
        }
        // 空文件的删除
        file.delete();
    }

    private static void unZipFiles(File srcFile, String destDirPath) {
        long start = System.currentTimeMillis();
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            throw new RuntimeException(srcFile.getPath() + "所指文件不存在");
        }
        // 开始解压
        ZipFile zipFile = null;
        try {
            //编码不设置的话，报java.lang.IllegalArgumentException: MALFORMED异常
            zipFile = new ZipFile(srcFile, Charset.forName("GBK"));
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                // 如果是文件夹，就创建个文件夹
                if (entry.isDirectory()) {
                    String dirPath = destDirPath + "/" + entry.getName();
                    File dir = new File(dirPath);
                    dir.mkdirs();
                    File[] files = dir.listFiles();
                    for (File file : files) {
                        System.out.println("名称"+file.getName()+"---路径"+file.getAbsolutePath());
                    }
                } else {
                    // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                    File targetFile = new File(destDirPath + "/" + entry.getName());
                    // 保证这个文件的父文件夹必须要存在
                    System.out.println("名称"+targetFile.getName()+"---路径"+targetFile.getAbsolutePath());
                    if(!targetFile.getParentFile().exists()){

                    }
                    targetFile.createNewFile();
                    // 将压缩文件内容写入到这个文件中
                    InputStream is = zipFile.getInputStream(entry);
                    FileOutputStream fos = new FileOutputStream(targetFile);
                    int len;
                    byte[] buf = new byte[1024];
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    // 关流顺序，先打开的后关闭
                    fos.close();
                    is.close();
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("解压完成，耗时：" + (end - start) +" ms");
        } catch (Exception e) {
            //throw new RuntimeException("unzip error from ZipUtils", e);
            e.printStackTrace();
        } finally {
            if(zipFile != null){
                try {
                    zipFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
