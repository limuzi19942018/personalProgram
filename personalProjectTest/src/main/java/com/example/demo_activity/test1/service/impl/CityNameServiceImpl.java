package com.example.demo_activity.test1.service.impl;

import com.example.demo_activity.test1.model.CityName;
import com.example.demo_activity.test1.dao.CityNameMapper;
import com.example.demo_activity.test1.multidatasource.DSEnum;
import com.example.demo_activity.test1.multidatasource.annoation.DataSource;
import com.example.demo_activity.test1.multidatasource.annoation.TestMethod;
import com.example.demo_activity.test1.service.ICityNameService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yongli123
 * @since 2020-01-02
 */
@Service
public class CityNameServiceImpl extends ServiceImpl<CityNameMapper, CityName> implements ICityNameService {
    @Resource
    private CityNameMapper cityNameMapper;

    @Override
    @DataSource(name = DSEnum.DATA_SOURCE_TEST)
    //@TestMethod
    public String getCityNameById(String cityId) {
        return cityNameMapper.getCityNameById(cityId);
    }
}
