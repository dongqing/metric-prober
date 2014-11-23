package com.vip.metricprobe.taskkeeper;

/**
 * Created by dongqingswt on 14-11-23.
 */
public interface JobExecution {

//    public JobResult executeJob();

    public JobResult executeJob(JobContext jobContext);


    public void addJobTransition(JobResult jobResult,JobExecution next) ;

    public void setJob(Job job);


    public JobExecution getNext(JobResult jobResult);

}
