package com.example.demo_activity.test1.base;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Author: yongl
 * @DATE: 2020/3/27 17:38
 */

public interface BaseJob  extends Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException;

}
