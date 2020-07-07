package com.example.demo_activity.test1.service;

import com.example.demo_activity.test1.tips.SuccessTip;

/**
 * @Author: yongl
 * @DATE: 2020/7/7 13:56
 */

public interface IMongodbService {

    /**
     * 向mongodb数据库中插入一条vo实体对象
     * @return 返回结果集
     */
    SuccessTip insertVo();

    /**
     * 向mongodb数据库中插入一条json对象
     * @return 返回结果集
     */
    SuccessTip insertJsonObject();

    /**
     * 向mongodb数据库中插入一个json数组
     * @return 返回结果集
     */
    SuccessTip insertJsonArray();

    /**
     * 删除mongodb中的collection集合
     * @return 返回结果集
     */
    SuccessTip dropCollection();

    /**
     * 修改mongodb中的collection集合数据
     * @return 返回结果集
     */
    SuccessTip updateCollectionData();

    /**
     * 查询mongodb中的collection集合数据
     * @return 返回结果集
     */
    SuccessTip findCollectionData();
}
