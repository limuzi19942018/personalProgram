package com.example.demo_activity;

import com.example.demo_activity.test1.comenum.TemplatePathEnum;
import com.example.demo_activity.test1.utils.Base64Util;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.SpringVersion;

import java.io.*;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.*;

/**
 * @Author: yongl
 * @DATE: 2020/1/17 16:55
 */

public class TestDemo {

    private final static Logger LOGGER = LoggerFactory.getLogger(TestDemo.class);

    public void testOrder(Double d1) {
        Double[] d = {8.0, 9.2, 10.5, 87.3, 200.25, 312.5};
        List<Object> list = new ArrayList<>(d.length + 1);
        for (int i = 0; i < d.length; i++) {
            BigDecimal compareD1 = new BigDecimal(d1);
            List<Double> doubles = Arrays.asList(d);
            if (compareD1.compareTo(new BigDecimal(d[d.length - 1])) > 0) {
                list.addAll(doubles);
                list.add(d1);
                break;
            }
            if (compareD1.compareTo(new BigDecimal(d[0])) < 0) {
                list.add(d1);
                list.addAll(doubles);
                break;
            }
            list.add(d[i]);
            if (i < d.length - 1) {
                BigDecimal bigDecimald1 = new BigDecimal(d[i]);
                BigDecimal bigDecimald2 = new BigDecimal(d[i + 1]);
                if (compareD1.compareTo(bigDecimald1) > 0 && compareD1.compareTo(bigDecimald2) < 0) {
                    list.add(d1);
                }
            }
        }
        System.out.println(list.toString());
    }

    @Test
    public void test() {
        //testOrder(7.55);
        int i1 = 100;
        Double d1 = 2555.25;
        BigDecimal divide = new BigDecimal(d1).divide(new BigDecimal(i1));
        System.out.println(divide.toString());
    }

    @Test
    public void testFormula() {
        boolean blank1 = StringUtils.isBlank("");
        boolean blank2 = StringUtils.isBlank(null);
        boolean empty1 = StringUtils.isEmpty("");
        boolean empty2 = StringUtils.isEmpty(null);
        System.out.println("111" + blank1);
        System.out.println("222" + blank2);
        System.out.println("333" + empty1);
        System.out.println("444" + empty2);
        //第一次提交
        //第二次提交
        //第三次提交


    }

    @Test
    public void testVersion() {
        String springVersion = SpringVersion.getVersion();
        LOGGER.info("spring版本号:{}", springVersion);
        String springBootVersion = SpringBootVersion.getVersion();
        LOGGER.info("springboot版本号:{}", springBootVersion);
    }

    @Test
    public void testBase() {
        String str = "http://demohost/api/fileAcl/bishengoffice/admin";
        String str44 = Base64Util.encodeData(str);
        System.out.println(str44);
    }

    @Test
    public void testHash() throws Exception {
        String ps = "abcdefgsww";
        MessageDigest messageDigest;
        messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(ps.getBytes("UTF-8"));
        String encodeStr = byte2Hex(messageDigest.digest());
        System.out.println(encodeStr);
    }

    private static String byte2Hex(byte[] bytes) {
        StringBuilder stringBuffer = new StringBuilder();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //byte转16进制若结果是一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    @Test
    public void testGetFile() throws Exception {
        File file = new File("https://bisheng-cache.nodoc.cn/bbbbbbbaa/temp/version.xlsx?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=LTAI4GJWmpviPMpeHxo9Cen9%2F20200420%2Foss-cn-beijing%2Fs3%2Faws4_request&X-Amz-Date=20200420T031059Z&X-Amz-Expires=18000&X-Amz-SignedHeaders=host&X-Amz-Signature=506a4cd04a7990a26c4fab411c3a63a368c0b3c64c4a69d2cc21a77922f471ea");
        String name = file.getName();
        File destFile = new File("C:\\Users\\Lenovo\\Desktop\\copy.xls");
        //copyFileUsingFileStreams(file,destFile);
        System.out.println("--------------------" + name);

    }

    private static void copyFileUsingFileStreams(File source, File dest) throws IOException

    {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            input.close();
            output.close();
        }
    }

    @Test
    public void testEnum() {
        String exPath = TemplatePathEnum.getExPath(1);
        System.out.println(exPath);
    }
    @Test
    public void testException(){
        System.out.println("我要开始执行了");
        try {
            System.out.println("-----");
            int number=1/0;
            System.out.println("我应该不打印");
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("我倒偶滴------");
        long time = new Date().getTime();
        //1589433878904
        System.out.println(String.valueOf(time));
        String fileName="资产基础法.xls";
        String prefix = fileName.substring(0, fileName.lastIndexOf("."));
        String fileSufix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String finalName = String.valueOf(new Date().getTime()) +"_"+ prefix + "." + fileSufix;
        System.out.println(finalName);
    }

    @Test
    public void testRuntime(){
        int number = Runtime.getRuntime().availableProcessors();
        System.out.println(number);
    }
}
