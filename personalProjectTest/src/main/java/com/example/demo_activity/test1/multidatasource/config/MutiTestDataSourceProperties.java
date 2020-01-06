package com.example.demo_activity.test1.multidatasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: yongl
 * @DATE: 2020/1/3 10:21
 */
@Component
@ConfigurationProperties(prefix = "test.datasource")
@EnableConfigurationProperties(MutiTestDataSourceProperties.class)
public class MutiTestDataSourceProperties {
    private String url;

    private String username;

    private String password;
    public void config(DruidDataSource dataSource) {
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
