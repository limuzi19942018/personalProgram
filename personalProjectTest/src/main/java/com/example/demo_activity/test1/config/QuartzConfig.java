package com.example.demo_activity.test1.config;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;

/** 这个类暂时不用
 * @Author: yongl
 * @DATE: 2020/3/26 17:15
 */

//@Configuration
public class QuartzConfig {


    //注意：！！！！
    //下列方法只适用于springboot2.0版本以上，因为springboot2.0版本会自动注入一个scheduler调度器，你定义的bean会自动绑定到调度器上
    //1.5版本没有自动注入一个调度器，你可以试试手动注入，通过factory

//    @Bean
//    public JobDetail testQuartz1() {
//        return JobBuilder.newJob(TestTask1.class).withIdentity("testQuartzTrigger1").storeDurably().build();
//    }
//
//    @Bean
//    public Trigger testQuartzTrigger1() {
//        //5秒执行一次
//        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
//                .withIntervalInSeconds(5)
//                .repeatForever();
//        return TriggerBuilder.newTrigger().forJob(testQuartz1())
//                .withIdentity("testQuartzTrigger1")
//                .usingJobData("msg", "Hello Quartz")
//                .withSchedule(scheduleBuilder)
//                .build();
//    }
//
//    @Bean
//    public JobDetail testQuartz2() {
//        return JobBuilder.newJob(TestTask2.class).withIdentity("testTask2").storeDurably().build();
//    }
//
//    @Bean
//    public Trigger testQuartzTrigger2() {
//        //cron方式，每隔5秒执行一次
//        return TriggerBuilder.newTrigger().forJob(testQuartz2())
//                .withIdentity("testTask2")
//                .usingJobData("msg", "Hello Quartz")
//                .withSchedule(CronScheduleBuilder.cronSchedule("*/2 * * * * ?"))
//                .build();
//    }
    //------------------------------------------------------
}

