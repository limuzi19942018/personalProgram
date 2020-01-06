package com.example.demo_activity.test1.multidatasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/** 动态数据源
 * @Author: yongl
 * @DATE: 2020/1/3 10:50
 */

public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSourceType();
    }
}
