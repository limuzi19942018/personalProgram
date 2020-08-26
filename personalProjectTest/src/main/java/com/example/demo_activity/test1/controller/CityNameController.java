package com.example.demo_activity.test1.controller;


import com.example.demo_activity.test1.model.CityName;
import com.example.demo_activity.test1.service.ICityNameService;
import com.example.demo_activity.test1.tips.SuccessTip;
import com.example.demo_activity.test1.utils.AliYunUploadFile;
import com.example.demo_activity.test1.utils.FileImportExportUtilss;
import com.example.demo_activity.test1.utils.FileUtils;
import com.example.demo_activity.test1.utils.ObjectUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yongli123
 * @since 2020-01-02
 */
@Controller
@RequestMapping("/cityName")
public class CityNameController {

    private final static Logger LOGGER = LoggerFactory.getLogger(CityNameController.class);

    @Autowired
    private ICityNameService cityNameService;




    @ResponseBody
    @RequestMapping(value = "/getCityNameById",method = RequestMethod.GET)
    public Object getPersonById(String cityId){
        CityName cityNameById = cityNameService.getCityNameById(cityId);
        return cityNameById;
    }

    @ResponseBody
    @PostMapping(value = "/insertVoList")
    public Object insertVoList(int size){
        cityNameService.insertVoList(size);
        return new SuccessTip();
    }

    /**
     * 测试模板上传
     * @param file
     * @param request
     * @return
     */
    @ApiOperation(value = "测试模板上传", notes = "测试模板上传", tags = {"测试模板上传"}, httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/testUpload",method = RequestMethod.POST)
    public Object testUpload(MultipartFile file, HttpServletRequest request){
        if(file.isEmpty()){
            return "文件是空";
        }
        //FileImportExportUtilss.zipDecompression(file);
        String originalFilename = file.getOriginalFilename();
        LOGGER.info("文件名称是:{}",originalFilename);
        try {
            String temUrl="C:\\Users\\Lenovo\\Desktop\\home_zlpg"+File.separator+originalFilename;
            File temFile = new File(temUrl);
            file.transferTo(temFile);
            //String aliyunUrl="report"+File.separator+ ObjectUtils.getShortUuid()+File.separator+originalFilename;
            String aliyunUrl="report"+File.separator+ "ee599658f1334e8bacc88f699362c53a"+File.separator+originalFilename;
            AliYunUploadFile.uploadFile(temUrl,aliyunUrl);
            LOGGER.info("阿里云路径是:{}",aliyunUrl);
            //删除本地文件
            FileUtils.deleteFile(temUrl);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "上传成功";
    }


    @ResponseBody
    @RequestMapping(value = "/deleteFile",method = RequestMethod.POST)
    public Object deleteFile(String filePath){
        Boolean exitPathUrl = AliYunUploadFile.isExitPathUrl(filePath);
        LOGGER.info("结果是:{}",exitPathUrl);
        if(exitPathUrl){
         AliYunUploadFile.deleteFile(filePath);
        }
        return "删除成功";
    }
}

