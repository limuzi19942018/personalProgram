package com.example.demo_activity.test1.controller;

import com.example.demo_activity.test1.service.IMongodbService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private IMongodbService iMongodbService;



    /**
     * 向mongodb中插入一条实体对象
     * @return  返回结果集
     */
    @PostMapping("/insertVo")
    @ResponseBody
    public Object insertVo(){
        return iMongodbService.insertVo();
    }

    /**
     * 向mongodb中插入一条json对象
     * @return 返回结果集
     */
    @PostMapping("/insertJsonObject")
    @ResponseBody
    public Object insertJsonObject(){
        return iMongodbService.insertJsonObject();
    }

    /**
     * 向mongodb中插入一个json数组
     * @return 返回结果集
     */
    @PostMapping("/insertJsonArray")
    @ResponseBody
    public Object insertJsonArray(){
        return iMongodbService.insertJsonArray();
    }

    /**
     * 删除mongodb中的collection集合
     * @return 返回结果集
     */
    @PostMapping("/dropCollection")
    @ResponseBody
    public Object dropCollection(){
        return iMongodbService.dropCollection();
    }

    /**
     * 修改mongodb中的collection集合数据
     * @return 返回结果集
     */
    @PostMapping("/updateCollectionData")
    @ResponseBody
    public Object updateCollectionData(){
        return iMongodbService.updateCollectionData();
    }

    /**
     * 查询mongodb中的collection集合数据
     * @return 返回结果集
     */
    @PostMapping("/findCollectionData")
    @ResponseBody
    public Object findCollectionData(){
        return iMongodbService.findCollectionData();
    }
}
