package com.example.demo_activity.test1.dao;

import com.example.demo_activity.test1.model.CityName;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yongli123
 * @since 2020-01-02
 */
public interface CityNameMapper extends BaseMapper<CityName> {

    String getCityNameById(String cityId);
}
