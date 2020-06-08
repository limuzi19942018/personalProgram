package com.example.demo_activity.test1.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.demo_activity.test1.model.Person;
import com.example.demo_activity.test1.service.IPersonService;
import com.example.demo_activity.test1.tips.SuccessTip;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yongli
 * @since 2019-11-26
 */
@Controller
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private IPersonService iPersonService;

    @ResponseBody
    @RequestMapping(value = "/getPersonById", method = RequestMethod.GET)
    public Object getPersonById(String personId) {
        String addressName = iPersonService.getPersonById(personId);
        //Person person = iPersonService.selectById(personId);
        //Person person = iPersonService.selectOne(new EntityWrapper<Person>().eq("id", personId));
        /*Map<String, Object> map = new HashMap<>();
        map.put("person",person);
        map.put("addressName",addressName)*/
        ;
        return new SuccessTip(new Person());
    }

    @ResponseBody
    @RequestMapping(value = "/insertObject", method = RequestMethod.POST)
    public Object insertObject(@RequestBody Person person) {
        boolean insert = iPersonService.insert(person);
        return new SuccessTip("成功");
    }


    @ResponseBody
    @RequestMapping(value = "/insertMap", method = RequestMethod.POST)
    public Object insertMap(@RequestBody ConcurrentHashMap map) throws Exception {
        System.out.println(map.get("type").toString());
        Object object = map.get("objectName");
        if (object != null) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.getDeserializationConfig();
            //将object类转成实体对象
            Person person = mapper.convertValue(object, Person.class);
            boolean insert = iPersonService.insert(person);
        }
        return new SuccessTip("成功");
    }

    @ResponseBody
    @RequestMapping(value = "/testPerson", method = RequestMethod.GET)
    public Object testPerson() {
        Person person = new Person();
        person.setId(9);
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        list.add(map);
        iPersonService.insert(person);
        return new SuccessTip("成功");
    }


    /**
     * 测试@Transactional的事务回滚（这种方式是方法里面有try catch）
     *
     * @param personId
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/testTran")
    public Object testTran(String personId) {
        return new SuccessTip(iPersonService.update(personId));
    }

    /**
     * 测试throw exception的方式(这种方式是方法上有throw exception)
     *
     * @param personId
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/testThrows")
    public Object testThrows(String personId) {
        try {
            return new SuccessTip(iPersonService.updateByThrows(personId));
        } catch (Exception e) {
            e.printStackTrace();
            return new SuccessTip(1, "更改失败", false);
        }
    }

    /**
     * 测试事务（这种方式是方法上有thorw Exception 并且方法体内有try catch）
     *
     * @param personId
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/testThrowsAndTryCatch")
    public Object testThrowsAndTryCatch(String personId) {
        try {
            return new SuccessTip(iPersonService.testThrowsAndTryCatch(personId));
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof NullPointerException) {
                return new SuccessTip(1, "我是空指针异常", e.getMessage());
            }
            return new SuccessTip(1, e.getMessage(), false);
        }
    }

    //@ResponseBody
    //@GetMapping(value = "/downloadAliyunFile")
    public static File downloadAliyunFile(String urlPath, String downloadDir) {
        File file = null;
        try {
            // 统一资源
            URL url = new URL(urlPath);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            // 设定请求的方法，默认是GET
            httpURLConnection.setRequestMethod("GET");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
            httpURLConnection.connect();

            // 文件大小
            int fileLength = httpURLConnection.getContentLength();

            // 文件名
            String filePathUrl = httpURLConnection.getURL().getFile();
            String fileFullName = filePathUrl.substring(filePathUrl.lastIndexOf(File.separatorChar) + 1);

            System.out.println("file length---->" + fileLength);

            URLConnection con = url.openConnection();

            BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());

            String path = downloadDir + File.separatorChar + fileFullName;
            file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(file);
            int size = 0;
            int len = 0;
            byte[] buf = new byte[1024];
            while ((size = bin.read(buf)) != -1) {
                len += size;
                out.write(buf, 0, size);
                // 打印下载百分比
                // System.out.println("下载了-------> " + len * 100 / fileLength +
                // "%\n");
            }
            bin.close();
            out.close();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            return file;
        }
    }

    public static void downloadAliyunFileByNio(String urlPath, String downloadDir) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(urlPath);
            fos = new FileOutputStream(downloadDir);
            try {
                byte[] bytes = new byte[2048];
                int i;
                while ((i = fis.read(bytes)) != -1) {
                    fos.write(bytes, 0, i);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fis != null){
                        fis.close();
                    }
                    if (fos != null){
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        //downloadAliyunFile("https://cailian-evaluation-test.oss-cn-beijing.aliyuncs.com/template/combination/zip/1591350238433/信托业.zip","C:\\Users\\Lenovo\\Desktop\\code");
        downloadAliyunFileByNio("E:\\业务文件夹\\home_zlpg\\1_2月份工作计划.xls","C:\\Users\\Lenovo\\Desktop\\code\\22.xls");
    }
}

