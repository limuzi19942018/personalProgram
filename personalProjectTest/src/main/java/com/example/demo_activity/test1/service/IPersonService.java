package com.example.demo_activity.test1.service;

//import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.service.IService;
import com.example.demo_activity.test1.model.Person;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yongli
 * @since 2019-11-26
 */
public interface IPersonService extends IService<Person> {

    String getPersonById(String personId);

}
