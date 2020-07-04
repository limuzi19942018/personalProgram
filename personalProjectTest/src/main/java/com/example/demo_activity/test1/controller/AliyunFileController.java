package com.example.demo_activity.test1.controller;


import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.example.demo_activity.test1.service.IAliyunFileService;
import com.example.demo_activity.test1.tips.SuccessTip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 阿里云存放文件表 前端控制器
 * </p>
 *
 * @author yongli
 * @since 2020-05-30
 */
@Controller
@RequestMapping("/aliyunFile")
public class AliyunFileController {

    @Autowired
    private IAliyunFileService iAliyunFileService;

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            return new SuccessTip(1, "文件不能为空", null);
        }
        try {
            SuccessTip successTip = iAliyunFileService.uploadFile(file);
            return successTip;
        } catch (Exception e) {
            e.printStackTrace();
            return new SuccessTip(1,"上传失败",e.getMessage());
        }
    }

    /**
     * 上传文件到本地保存
     *
     * @param files
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/multiUploadFile", method = RequestMethod.POST)
    public Object multiUploadFile(List<MultipartFile> files, HttpServletRequest request) {
        if (files.isEmpty()) {
            return new SuccessTip(1, "文件不能为空", null);
        }
        return iAliyunFileService.multiUploadFile(files);
    }

    @ResponseBody
    @RequestMapping(value = "/multiUploadFileByNIO", method = RequestMethod.POST)
    public Object multiUploadFileByNIO(List<MultipartFile> files, HttpServletRequest request) {
        if (files.isEmpty()) {
            return new SuccessTip(1, "文件不能为空", null);
        }
        return iAliyunFileService.multiUploadFileByNIO(files);
    }

    public static void main(String[] args) {
       /* Map<String, Object> map = new HashMap<>();
        Map<String, Object> retrunMap = new HashMap<>();
        map.put("a",123);
        map.put("b",123);
        retrunMap.put("key",map);
        String s = JSONObject.toJSONString(retrunMap);
        System.out.println(s);*/
       String str="中国,美国";
       String ss="韩国,上海";
        StringBuffer stringBuffer = new StringBuffer();
         stringBuffer.append(str).append(",").append(ss);
        System.out.println(stringBuffer.toString());

    }
}

