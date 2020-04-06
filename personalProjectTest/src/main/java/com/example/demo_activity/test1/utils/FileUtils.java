package com.example.demo_activity.test1.utils;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

/**
 * @Author: yongl
 * @DATE: 2020/4/1 15:18
 */

public class FileUtils {


    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(FileUtils.class);

    /**
     * 将文件流发送至另外服务器的方法（这里有个fileName
     * 本来是想将文件名放在流里面一起带过去的后来出现问题，如果有朋友知道在这种方法里面怎么把fileName 传过去，麻烦告知一下，万分感谢）
     *
     * @param bytes
     * @param fileName
     * @return 从服务器端 响应的流 可通过 new String(bytes); 转换
     */
    public byte[] httpPost(byte[] bytes, String fileName) {
        try {
            String url = "http://192.168.0.104:8080/reportManageTemplate/receiveReportFile";
            URL console = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) console
                    .openConnection();
            conn.setConnectTimeout(60000);
            conn.setReadTimeout(60000);
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            /*conn.setRequestProperty("Content-Type","text/html");
            conn.setRequestProperty("Cache-Control","no-cache");
            conn.setRequestProperty("Charsert", "UTF-8");*/
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("contentType", "utf-8");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.addRequestProperty("fileName",fileName);
            conn.addRequestProperty("projectId","15268754");
            conn.addRequestProperty("investorId","11112222");
            conn.addRequestProperty("type","1");
            conn.addRequestProperty("templateTypeId","1");
            conn.connect();
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.write(bytes);
            //out.write(String.valueOf(bytes).toString().getBytes("utf-8"));
            int responseCode = conn.getResponseCode();
            InputStream is = conn.getInputStream();
            if (is != null) {
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                is.close();
                logger.info("文件发送成功++++++++++++++++++++++++++");
                return outStream.toByteArray();
            }
            // 刷新、关闭
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("文件发送失败++++++++++++++++++++++++++");
        }
        return null;
    }

    /**
     * 将文件转换成byte[]
     *
     * @param filePath
     * @return
     */
    public byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public void testSocekt(String filePath) throws Exception
    {
        //建立Socket服务
        Socket fileLoaderSocket = new Socket("192.168.0.104", 7200);
        //从客户端本地读取文件,并写入socket的输出流中
        OutputStream out = fileLoaderSocket.getOutputStream();
        //实例化对象fileReader
        InputStream fileRead = new FileInputStream(filePath);
        //建立数组
        byte[] buf = new byte[1024];
        int len = 0;
        //判断是否读到文件末尾
        while((len=fileRead.read(buf)) != -1)
        {
            out.write(buf, 0, len);
        }
        //告诉服务端，文件已传输完毕
        fileLoaderSocket.shutdownOutput();
        //获取从服务端反馈的信息
        BufferedReader in = new BufferedReader(new InputStreamReader(fileLoaderSocket.getInputStream()));
        String serverBack = in.readLine();
        System.out.println(serverBack);
        //资源关闭
        fileLoaderSocket.close();
        fileRead.close();
    }
}
