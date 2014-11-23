package com.vip.metricprobe.taskkeeper;

/**
 * Created by dongqingswt on 14-11-23.
 */
public interface JobExecutionMachine {


    public void setStartJobExecution(JobExecution jobExecution);

    public void startJobExecution(JobContext context);
}
