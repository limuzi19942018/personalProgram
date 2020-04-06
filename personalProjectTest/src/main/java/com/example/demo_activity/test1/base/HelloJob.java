package com.example.demo_activity.test1.base;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @Author: yongl
 * @DATE: 2020/3/27 17:41
 */

public class HelloJob implements BaseJob {
    private static Logger log = LoggerFactory.getLogger(HelloJob.class);

    public HelloJob() {

    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String msg = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("msg");
        log.info("msg的消息是:{}",msg);
        log.info("Hello Job执行时间: " + new Date());
    }
}
