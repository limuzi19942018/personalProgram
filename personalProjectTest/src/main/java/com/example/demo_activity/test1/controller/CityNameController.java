package com.example.demo_activity.test1.controller;


import com.example.demo_activity.test1.service.ICityNameService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

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
        String cityName = cityNameService.getCityNameById(cityId);
        return cityName;
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
        String originalFilename = file.getOriginalFilename();
        LOGGER.info("文件名称是:{}",originalFilename);
        return null;
    }

}

