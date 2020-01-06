package com.example.demo_activity.test1.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.demo_activity.test1.model.Person;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yongli
 * @since 2019-11-26
 */
//@Mapper
public interface PersonMapper extends BaseMapper<Person> {
    String selectTest();

}
