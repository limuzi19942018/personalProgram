package com.example.demo_activity.test1.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import com.example.demo_activity.test1.properties.AliyunProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 阿里云上传下载
 * @author: ligang
 * @return
 */
public class AliYunUploadFile {
    private final static Logger LOGGER = LoggerFactory.getLogger(AliYunUploadFile.class);


	private static String endpoint;

    private static String accessKeyId;

    private static String accessKeySecret;

    private static String bucketName;

    private static String SERVER_PATH;


    static {
        AliyunProperties aliyunProperties = SpringContextHolder.getBean(AliyunProperties.class);
        endpoint = aliyunProperties.getEndpoint();
        accessKeyId = aliyunProperties.getAccessId();
        accessKeySecret = aliyunProperties.getAccessKey();
        bucketName = aliyunProperties.getBucket();
        SERVER_PATH = aliyunProperties.getServerPath();
    }
    public static String getServerPath(){
        return SERVER_PATH;
    }
    /**
     * 根据名称上传
     *
     * @param pathUrl  上传的文件路径 （/home/zlpg/report/yyyyMM/0/operation/0/资产基础法.xls）
     * @param filePath 要保存到阿里云的路径 （report/yyyyMM/0/operation/0/类型名称.xls）
     * @return
     */
    public static long uploadFile(String pathUrl, String filePath) throws IOException {
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            System.out.println("阿里云配置endpoint："+endpoint);
            System.out.println("阿里云配置accessKeyId："+accessKeyId);
            System.out.println("阿里云配置accessKeySecret："+accessKeySecret);
            System.out.println("阿里云配置bucketName："+bucketName);
            client.putObject(new PutObjectRequest(bucketName, filePath, new File(pathUrl)));
            //获取文件大小
            SimplifiedObjectMeta objectMeta = client.getSimplifiedObjectMeta(bucketName, filePath);
            return (new Double(Math.floor(objectMeta.getSize())).longValue());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.shutdown();
        }
        return 0;
    }


    /**
     * 删除阿里云上的文件
     * @param filePath
     * @return
     */
    public static boolean deleteFile(String filePath) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        boolean exist = ossClient.doesObjectExist(bucketName, filePath);
        LOGGER.info("文件路径:{}",filePath);
        if (!exist) {
            LOGGER.info("当前文件不存在");
            return false;
        }
        ossClient.deleteObject(bucketName, filePath);
        ossClient.shutdown();
        return true;
    }

    public static long uploadFileToAliyun(File file, String filePath) throws IOException {
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        System.out.println("阿里云配置endpoint："+endpoint);
        System.out.println("阿里云配置accessKeyId："+accessKeyId);
        System.out.println("阿里云配置accessKeySecret："+accessKeySecret);
        System.out.println("阿里云配置bucketName："+bucketName);
        try {
            client.putObject(new PutObjectRequest(bucketName, filePath, file));
            //获取文件大小
            SimplifiedObjectMeta objectMeta = client.getSimplifiedObjectMeta(bucketName, filePath);
            return (new Double(Math.floor(objectMeta.getSize())).longValue());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.shutdown();
        }
        return 0;
    }
    /**
     * 下载oss文件 ==类型
     *
     * @param urlFile 下载到要保存的路径 （/home/zlpg/yyyyMM/0/operation/0/类型名称.xls）
     * @param pathUrl 阿里云文件存储的路径 （yyyyMM/0/operation/0/类型名称.xls）
     * @return
     * @author: xiangdong.chang
     * @create 2017/11/22 0022 19:10
     */
    public static Map<String, Object> downloadFile(String urlFile, String pathUrl) {
        System.out.println("-----------下载文件地址：（downloadFile）urlFile："+urlFile+" pathUrl："+pathUrl+"----------------------");
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            Map<String, Object> map = new HashMap<>();
            //创建文件夹
            FileUtils.createFile(urlFile);
            //下载到本地
            ObjectMetadata clientObject = client.getObject(new GetObjectRequest(bucketName, pathUrl), new File(urlFile));
            System.out.println(clientObject);
            //获取文件大小
            SimplifiedObjectMeta objectMeta = client.getSimplifiedObjectMeta(bucketName, pathUrl);
            long fileSize = (new Double(Math.round(objectMeta.getSize() / (1024 * 1024))).longValue());
            map.put("urlFile", urlFile);
            map.put("fileSize", fileSize);
            return map;
        } catch (IOException e) {
            //删除出错文件
            FileUtils.deleteFileByPath(urlFile);
            e.printStackTrace();
        } finally {
            client.shutdown();
        }
        return null;
    }

    /**
     * 获取文件的真实路径
     * @param fileUrl yyyyMM/0/operation/0/类型名称.xls
     * @return
     */
    public static String getRealPath(String fileUrl){
        String realPath;
        if(ObjectUtils.isNotNull(fileUrl)){
            //Windows存放路径  c:/zlpg/yyyyMM/0/operation/0/类型名称.xls
            realPath = SERVER_PATH + "/" + fileUrl;
        }else{
            //Windows存放路径  c:/zlpg/
            realPath = SERVER_PATH + "/";
        }
    	return realPath;
    }
    
    /**
     * 判断阿里云文件是否存在
     *
     * @param aliyunUrl 存储文件路径
     */
    public static Boolean isExitPathUrl( String aliyunUrl){
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        Boolean result=ossClient.doesObjectExist(bucketName,aliyunUrl);
        // 关闭OSSClient。
        ossClient.shutdown();
        return result;
    }
    
    /**
     * 获取系统模板文件路径
     *
     * @param temFileName 文件名称
     */
    public static String getTemFileUrl(String temFileName) {
        String realPath = "";
        if(ObjectUtils.isNotNull(temFileName)){
            //Windows存放路径  
            realPath = SERVER_PATH + File.separator + "systemplate" + File.separator + temFileName;
        }
    	return realPath;
    }

    /**
     * 获得阿里云url链接
     * http://files2018.oss-cn-beijing.aliyuncs.com/report/images/201805/035e6915b5d84ce18fd6d613b7b73d17.jpg?Expires=1842505135&OSSAccessKeyId=LTAIyFWyEHfzLlXb&Signature=9TI6tfHMsuKhxSKlBJZRAu346CA%3D
     * http://files2018.oss-cn-beijing.aliyuncs.com/report/images/2018-06-01/3d3688639b644ea89d4a9499f5373803.jpg?Expires=1842505135&OSSAccessKeyId=LTAIyFWyEHfzLlXb&Signature=9TI6tfHMsuKhxSKlBJZRAu346CA%3D
     * @param key
     * @return
     * @author songsl
     */
    public static String getAlyUrl(String key) {
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        System.out.println("阿里云配置endpoint："+endpoint);
        System.out.println("阿里云配置accessKeyId："+accessKeyId);
        System.out.println("阿里云配置accessKeySecret："+accessKeySecret);
        System.out.println("阿里云配置bucketName："+bucketName);
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = client.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            System.out.println("阿里云可访问路径："+url.toString());
            return url.toString();
        }
        return null;
    }
    
    public static Map<String, Object> getOSSClient(String key){
    	Map<String, Object> map = new HashMap<String,Object>();
    	OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    	OSSObject oSSObject = ossClient.getObject(bucketName, key);
    	map.put("ossClient", ossClient);
    	map.put("oSSObject", oSSObject);
        return map;
    }
    //判断文件是否存在阿里云
    public static boolean doesObjectExist(String key){
        System.out.println("doesObjectExist___key："+key);
        System.out.println("阿里云配置endpoint："+endpoint);
        System.out.println("阿里云配置accessKeyId："+accessKeyId);
        System.out.println("阿里云配置accessKeySecret："+accessKeySecret);
        System.out.println("阿里云配置bucketName："+bucketName);
    	OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    	boolean result = ossClient.doesObjectExist(bucketName, key);
    	ossClient.shutdown();
        return result;
    }
}