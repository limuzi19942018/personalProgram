package com.example.demo_activity.test1.controller;


import com.example.demo_activity.test1.service.IPersonService;
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
 * @author yongli
 * @since 2019-11-26
 */
@Controller
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private IPersonService iPersonService;

    @ResponseBody
    @RequestMapping(value = "/getPersonById",method = RequestMethod.GET)
    public Object getPersonById(String personId){
        String addressName = iPersonService.getPersonById(personId);
        return addressName;
    }
}

