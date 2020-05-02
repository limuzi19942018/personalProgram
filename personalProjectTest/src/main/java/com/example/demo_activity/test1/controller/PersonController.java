package com.example.demo_activity.test1.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.demo_activity.test1.model.Person;
import com.example.demo_activity.test1.service.IPersonService;
import com.example.demo_activity.test1.tips.SuccessTip;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
}

