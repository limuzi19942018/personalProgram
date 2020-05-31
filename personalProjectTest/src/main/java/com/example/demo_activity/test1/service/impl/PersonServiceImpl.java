package com.example.demo_activity.test1.service.impl;


//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo_activity.test1.dao.PersonMapper;
import com.example.demo_activity.test1.model.Person;
import com.example.demo_activity.test1.multidatasource.annoation.TestMethod;
import com.example.demo_activity.test1.service.IPersonService;
import com.example.demo_activity.test1.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yongli
 * @since 2019-11-26
 */
@Service
public class PersonServiceImpl extends ServiceImpl<PersonMapper, Person> implements IPersonService {

    @Resource
    private PersonMapper personMapper;


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

    @Transactional
    @Override
    public Object update(String personId) {
        Person person = this.selectById(personId);
        try {
            if (ObjectUtils.isNotNull(person)) {
                person.setAddress("信阳666");
                boolean flag = this.updateById(person);
                int number=1/0;
                return flag;
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("我是异常后面的语句");
            //直接抛出exception，接口会报500
            //throw new RuntimeException();
            //设置事务回滚，此时接口依然返回200
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return false;
    }

    @Transactional
    @Override
    public Object updateByThrows(String personId) throws Exception{
        Person person = this.selectById(personId);
        if (ObjectUtils.isNotNull(person)) {
            person.setAddress("河南");
            boolean flag = this.updateById(person);
            //int number=1/0;
            return flag;
        }
        return false;
    }

    @Transactional
    @Override
    public Object testThrowsAndTryCatch(String personId)throws Exception {
        Person person = this.selectById(personId);
        try {
            if (ObjectUtils.isNotNull(person)) {
                person.setAddress("信阳666");
                boolean flag = this.updateById(person);
                int number=1/0;
                return flag;
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("我是异常后面的语句");
            throw new NullPointerException();
            //直接抛出exception，接口会报500
            //throw new RuntimeException();
            //设置事务回滚，此时接口依然返回200
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return false;
    }
}
