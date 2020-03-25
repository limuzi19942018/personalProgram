package com.example.demo_activity.test1.service.impl;


//import com.baomidou.mybatisplus.core.conditions.Wrapper;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo_activity.test1.dao.CityNameMapper;
import com.example.demo_activity.test1.model.CityName;
import com.example.demo_activity.test1.multidatasource.DSEnum;
import com.example.demo_activity.test1.multidatasource.annoation.DataSource;
import com.example.demo_activity.test1.service.ICityNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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

    @Autowired
    private StringRedisTemplate redisTemplate;
    //private RedisTemplate redisTemplate;//用这个注入，图形化界面打开key会出现乱码,因为redis的key和value需要序列化

    @Autowired
    private ICityNameService iCityNameService;

    @Override
    @DataSource(name = DSEnum.DATA_SOURCE_TEST)
    //@TestMethod
    public String getCityNameById(String cityId) {
        /*redisTemplate.opsForValue().set("test123","123456abc123456");
        Object test = redisTemplate.opsForValue().get("test");
        System.out.println("key值为"+test.toString());*/
       // return cityNameMapper.getCityNameById(cityId);
//        CityName cityName = iCityNameService.getById(1);
//        CityName cityName1 = new CityName();
//        cityName1.setCity("xinyang");
//        cityName1.setCountry("zhongguo");
//        iCityNameService.save(cityName1);
//        return cityName.getCountry();
        CityName cityName = iCityNameService.selectById(cityId);
        String city = cityName.getCity();
        System.out.println("名字是"+city);
        return city;
    }
}
