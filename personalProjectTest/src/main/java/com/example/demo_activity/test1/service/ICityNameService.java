package com.example.demo_activity.test1.service;

import com.example.demo_activity.test1.model.CityName;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yongli123
 * @since 2020-01-02
 */
public interface ICityNameService extends IService<CityName> {

    String getCityNameById(String cityId);

}
