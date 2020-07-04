package com.example.demo_activity.test1.controller;

import com.example.demo_activity.test1.model.CityName;
import com.example.demo_activity.test1.tips.SuccessTip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: yongl
 * @DATE: 2020/6/30 19:30
 */

@Controller
@RequestMapping("/mongodb")
public class MongodbController {


    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping("/insertVo")
    @ResponseBody
    public Object insertVo(){
        CityName cityName = new CityName();
        cityName.setCountry("中国");
        cityName.setId(12);
        cityName.setCity("河南");
        //mongoTemplate.save(cityName);
        mongoTemplate.save(cityName);
        return new SuccessTip("插入成功");

    }
}
