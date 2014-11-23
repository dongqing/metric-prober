package com.vip.metricprobe.taskkeeper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dongqingswt on 14-11-23.
 */
public class BaseJobExecution implements JobExecution {

    private final  Map<JobResult , JobExecution> map = new HashMap<JobResult,JobExecution>(2) ;
    private Job job;



    @Override
    public JobResult executeJob(JobContext jobContext) {
        return job.doWork(jobContext);
    }


    @Override
    public void addJobTransition(JobResult jobResult, JobExecution next) {
        map.put(jobResult,next);
    }

    @Override
    public void setJob(Job job) {

        this.job = job;

    }

    @Override
    public JobExecution getNext(JobResult jobResult) {
        return this.map.get(jobResult);
    }


}
