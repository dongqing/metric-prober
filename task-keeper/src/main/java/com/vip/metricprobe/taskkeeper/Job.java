package com.vip.metricprobe.taskkeeper;

/**
 * Created by dongqingswt on 14-11-23.
 */
public interface Job {

    public JobResult doWork(JobContext jobContext);
}
