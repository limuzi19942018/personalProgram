package com.example.demo_activity.test1.mybatisconfig;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.example.demo_activity.test1.datasource.DruidProperties;
import com.example.demo_activity.test1.multidatasource.DSEnum;
import com.example.demo_activity.test1.multidatasource.DynamicDataSource;
import com.example.demo_activity.test1.multidatasource.config.MutiTestDataSourceProperties;
import org.apache.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * @Author: yongl
 * @DATE: 2020/1/3 11:00
 */
@Configuration
@EnableTransactionManagement(order = 2)
@MapperScan("com.example.demo_activity.test1.dao")
public class MybatisPlusConfig {
    private Logger log = Logger.getLogger(this.getClass());
    @Autowired
    DruidProperties druidProperties;

    @Autowired
    MutiTestDataSourceProperties mutiTestDataSourceProperties;
    /**
     * 核心数据源
     */
    private DruidDataSource coreDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        druidProperties.config(dataSource);
        return dataSource;
    }

    /**
     * 另一个数据源
     */
    private DruidDataSource testDataSource() {
        DruidDataSource dataTestSource = new DruidDataSource();
        mutiTestDataSourceProperties.config(dataTestSource);
        return dataTestSource;
    }
    /**
     * 单数据源连接池配置
     */
    @Bean
    @ConditionalOnProperty(prefix = "guns", name = "muti-datasource-open", havingValue = "false")
    public DruidDataSource singleDatasource() {
        return coreDataSource();
    }

    /**
     * 多数据源连接池配置
     */
    @Bean
    @ConditionalOnProperty(prefix = "guns", name = "muti-datasource-open", havingValue = "true")
    public DynamicDataSource mutiDataSource() {


        DruidDataSource coreDataSource = coreDataSource();
        DruidDataSource testDataSource = testDataSource();

        try {
            coreDataSource.init();
            testDataSource.init();
            //log.info("我要设置数据源");
        } catch (SQLException sql) {
            sql.printStackTrace();
        }

        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put(DSEnum.DATA_SOURCE_CORE, coreDataSource);
        hashMap.put(DSEnum.DATA_SOURCE_TEST, testDataSource);

        dynamicDataSource.setTargetDataSources(hashMap);
        dynamicDataSource.setDefaultTargetDataSource(coreDataSource);
        return dynamicDataSource;
    }

    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

/*    *//**
     * 数据范围mybatis插件
     *//*
    @Bean
    public DataScopeInterceptor dataScopeInterceptor() {
        return new DataScopeInterceptor();
    }*/

    /**
     * 乐观锁mybatis插件
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
}
