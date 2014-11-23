package com.vip.metricprobe.taskkeeper;

/**
 * Created by dongqingswt on 14-11-23.
 */
public class EndJobExecution extends  BaseJobExecution  {



    @Override
    public JobExecution getNext(JobResult jobResult){
        return null;
    }


    @Override
    public JobResult executeJob(JobContext jobContext) {
        return null ;
    }


}
