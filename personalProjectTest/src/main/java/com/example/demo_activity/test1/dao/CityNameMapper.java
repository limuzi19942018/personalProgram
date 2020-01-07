package com.example.demo_activity.test1.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo_activity.test1.model.CityName;

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
