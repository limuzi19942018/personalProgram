package com.example.demo_activity.test1.controller;


import cn.hutool.core.util.ObjectUtil;
import com.example.demo_activity.test1.service.IAliyunFileService;
import com.example.demo_activity.test1.tips.SuccessTip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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
        if(file.isEmpty()){
            return new SuccessTip(1,"文件不能为空",null);
        }
        try {
            SuccessTip successTip = iAliyunFileService.uploadFile(file);
            return successTip;
        }catch (Exception e){
            e.printStackTrace();
            return new SuccessTip("上传失败");
        }
    }
}

