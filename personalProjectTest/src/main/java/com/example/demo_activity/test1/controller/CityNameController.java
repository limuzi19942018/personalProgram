package com.example.demo_activity.test1.controller;


import com.example.demo_activity.test1.service.ICityNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @Autowired
    private ICityNameService cityNameService;

    @ResponseBody
    @RequestMapping(value = "/getCityNameById",method = RequestMethod.GET)
    public Object getPersonById(String cityId){
        String cityName = cityNameService.getCityNameById(cityId);
        return cityName;
    }

}

