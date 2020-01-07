package com.example.demo_activity.test1.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo_activity.test1.dao.PersonMapper;
import com.example.demo_activity.test1.model.Person;
import com.example.demo_activity.test1.multidatasource.annoation.TestMethod;
import com.example.demo_activity.test1.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yongli
 * @since 2019-11-26
 */
@Service
public class PersonServiceImpl extends ServiceImpl<PersonMapper, Person> implements IPersonService {

    @Resource
    private PersonMapper personMapper;

    @Autowired
    private IPersonService iPersonService;

    @Override
    @TestMethod
    public String getPersonById(String personId) {
       //Person person = personMapper.selectById(1);
        //iPersonService.deleteById(1);
//        Person person = new Person();
//        person.setId(8);
//        person.setAddress("中国");
//        personMapper.insert(person);
        String s = personMapper.selectTest();
        return "1";
    }
}
