package com.example.demo_activity;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

@SpringBootApplication
@MapperScan("com.example.demo_activity.test1.dao")
public class DemoActivityApplication {
	private final static Logger logger = LoggerFactory.getLogger(DemoActivityApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(DemoActivityApplication.class, args);
		logger.info("GunsApplication is success!");
	}
}
