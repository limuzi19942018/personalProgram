package com.example.demo_activity.test1.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo_activity.test1.config.ElasticsearchUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Random;

/**
 * @Author: yongl
 * @DATE: 2020/6/9 15:03
 */

@Controller
@RequestMapping("/es")
public class ElasticSearchController {


    /**
     * 创建索引
     * @param request
     * @param response
     * @param indexName 索引名称
     * @return
     */
    @RequestMapping("/createIndex")
    @ResponseBody
    public String createIndex(HttpServletRequest request, HttpServletResponse response,String indexName) {
        if(!ElasticsearchUtil.isIndexExist(indexName)) {
            ElasticsearchUtil.createIndex(indexName);
        }
        else{
            return "索引已经存在";
        }
        return "索引创建成功";
    }

    /**
     *
     * @param indexName 索引名称(相当于数据库名)
     * @param esType es类型（相当于数据库的表名）
     * @return
     */
    @RequestMapping("/insertJson")
    @ResponseBody
    public String insertJson(String indexName,String esType) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", System.currentTimeMillis());
        jsonObject.put("age", 25);
        jsonObject.put("name", "j-" + new Random(100).nextInt());
        jsonObject.put("date", new Date());
        String id = ElasticsearchUtil.addData(jsonObject, indexName, esType, jsonObject.getString("id"));
        return id;
    }
}
