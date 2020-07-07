package com.example.demo_activity.test1.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo_activity.test1.model.CityName;
import com.example.demo_activity.test1.service.IMongodbService;
import com.example.demo_activity.test1.tips.SuccessTip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: yongl
 * @DATE: 2020/7/7 13:57
 */

@Service
public class MongodbServiceImpl implements IMongodbService {

    private final static Logger LOGGER = LoggerFactory.getLogger(MongodbServiceImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * 向mongodb数据库中插入一条数据
     *
     * @return 返回结果集
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public SuccessTip insertVo() {
        CityName cityName = new CityName();
        cityName.setCountry("美国");
        cityName.setId(14);
        cityName.setCity("芝加哥");
        mongoTemplate.insert(cityName);
        return new SuccessTip("插入成功");
    }

    /**
     * 向mongodb数据库中插入一条json对象
     *
     * @return 返回结果集
     */
    @Override
    public SuccessTip insertJsonObject() {
        JSONObject jsonObject2 = new JSONObject(2);
        jsonObject2.put("city", "横滨2");
        jsonObject2.put("country", "Japan2");
        //插入数据时指定集合名称
        mongoTemplate.insert(jsonObject2, "cityName");
        return new SuccessTip("插入成功");
    }

    /**
     * 向mongodb数据库中插入一个json数组
     *
     * @return 返回结果集
     */
    @Override
    public SuccessTip insertJsonArray() {
        JSONArray jsonArray = new JSONArray(2);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("city", "北京");
        jsonObject1.put("country", "China");
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("city", "莫斯科");
        jsonObject2.put("country", "Russia");
        jsonArray.add(jsonObject1);
        jsonArray.add(jsonObject2);
        //插入数据时指定collectionName,如果没有这个集合名称，mongodb会新建一个名字叫做该名称的集合
        mongoTemplate.insert(jsonArray, "cityName");
        return new SuccessTip("插入成功");
    }

    /**
     * 删除mongodb中的collection集合
     *
     * @return 返回结果集
     */
    @Override
    public SuccessTip dropCollection() {
        //这种删除collection集合的方式适用于，mongodb中的集合和代码中有一个一一对应的vo实体类
        mongoTemplate.dropCollection(CityName.class);
        //直接根据collection集合的名称删除,jSONObject是我mongodb库中一个集合的名称
        mongoTemplate.dropCollection("jSONObject");
        return new SuccessTip("删除成功");
    }


    /**
     * 修改mongodb中的collection集合数据
     *
     * @return 返回结果集
     */
    @Override
    public SuccessTip updateCollectionData() {
        //构建查询条件对象
        Query query = Query.query(Criteria.where("city").is("周党"));
        //构造更新对象
        Update update = new Update().set("city", "定远");
        //指定collection集合名称
        String collectionName = "cityName";
        //这个修改方法是当查询条件查出多条时，只修改第一条
        mongoTemplate.updateFirst(query, update, collectionName);
        //这个修改方法是当查询条件查出多条时，修改全部的
        mongoTemplate.updateMulti(query, update, collectionName);
        //upsert方法的意思是，当用query查询条件查询时，查不到数据，就会把更新语句当做新增语句插入到mongodb数据库中
        mongoTemplate.upsert(query, update, collectionName);
        return new SuccessTip("修改成功");
    }

    /**
     * 查询mongodb中的collection集合数据
     *
     * @return 返回结果集
     */
    @Override
    public SuccessTip findCollectionData() {
        //构建查询条件对象
        Query query = Query.query(Criteria.where("city").is("定远"));
        List<Map> cityNames = mongoTemplate.find(query, Map.class,"cityName");
        for (Map map : cityNames) {
            LOGGER.info("cityName为:{}",map.get("city").toString());
        }
        return new SuccessTip("查询成功");
    }
}
