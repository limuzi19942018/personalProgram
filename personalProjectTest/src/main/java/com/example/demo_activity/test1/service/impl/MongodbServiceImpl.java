package com.example.demo_activity.test1.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo_activity.test1.model.CityName;
import com.example.demo_activity.test1.model.Person;
import com.example.demo_activity.test1.service.IMongodbService;
import com.example.demo_activity.test1.tips.SuccessTip;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
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
        //方式一查询：根据查询条件query,返回实例的class文件，集合名称
        List<Map> cityNames = mongoTemplate.find(query, Map.class,"cityName");
        //方式二：只需根据返回实例的class文件，和结合名称，查询所有
        List<JSONObject> all = mongoTemplate.findAll(JSONObject.class,"cityName");
        //方式三：findByid,这里面的id指的是mongodb为我们生成的一个objectId
        Map map = mongoTemplate.findById("5f0421932d757613602fa658", Map.class,"cityName");
        //看能否在一个方法里面切换数据库(下行代码的person时属于我ceshi_database数据库的，
        // 上面cityname是属于我test数据库的，最后发现在同一方法里面如果要跨库查询，需要手动切换数据库)
        Map map2 = mongoTemplate.findById("5efb14aa1e67deabcf511918", Map.class,"person"); //查询出的结果为null,因为没有切换数据库
        LOGGER.info("集合长度为:{}",map2.size());
        return new SuccessTip("查询成功");
    }


    /**
     * 查询mongodb中的collection集合数据(自定义指定数据库)
     * @return 返回结果集
     */
    @Override
    public SuccessTip findDataByCustom() {
        //自定义指定一个mongodb的模板（可以自己指定数据库）
        MongoTemplate mongoTemplate_custom = new MongoTemplate(new MongoClient(), "ceshi_database");
        //aggregate聚合函数
        //mongoTemplate_custom.aggregate()
        //查询
        List<JSONObject> list = mongoTemplate_custom.findAll(JSONObject.class, "person");
        //根据id来查询(一定要记住这个id参数是mongodb里面的objectId,entiyClass是Map)
        Map person = mongoTemplate_custom.findById("5efb14aa1e67deabcf511918", Map.class, "person");
        LOGGER.info("集合长度为:{}", person.size());
        return null;
    }


    /**
     * 根据条件删除集合中的数据
     * @return 返回结果集
     */
    @Override
    public SuccessTip deleteData() {
        Query query = Query.query(Criteria.where("city").is("定远"));
        //根据条件删除(先查询再删除)
        WriteResult cityName = mongoTemplate.remove(query, "cityName");
        System.out.println(cityName.toString());
        return new SuccessTip("删除成功");
    }


    /**
     * 自定义创建一个集合
     * @return 返回结果集
     */
    @Override
    public SuccessTip createCollection() {
        //先自定义一个mongodb连接（确定数据库）
        MongoTemplate mongoTemplate_custom = new MongoTemplate(new MongoClient(), "ceshi_database");
        DBCollection product = mongoTemplate_custom.createCollection("product");
        System.out.println(product.toString());
        //打印结果为DBCollection{database=DB{name='ceshi_database'}, name='product'}
        return new SuccessTip("创建集合成功");
    }
}
