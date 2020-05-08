package com.example.demo_activity.test1.model;

/**
 * @Author: yongl
 * @DATE: 2020/3/27 17:28
 */

public class JobInfo {

    private String jobClassName;

    private String jobGroupName;

    private String cronExpression;

    private String jobType;

    private Integer timeType;


    public String getJobClassName() {
        return "com.example.demo_activity.test1.base." + jobClassName.trim();
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    public String getJobGroupName() {
        return jobGroupName;
    }

    public void setJobGroupName(String jobGroupName) {
        this.jobGroupName = jobGroupName;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

    //有参构造
    public JobInfo(String jobClassName, String jobGroupName, String cronExpression, String jobType, Integer timeType) {
        this.jobClassName = jobClassName;
        this.jobGroupName = jobGroupName;
        this.cronExpression = cronExpression;
        this.jobType = jobType;
        this.timeType = timeType;
    }

    //无参构造
    public JobInfo() {
    }
}
