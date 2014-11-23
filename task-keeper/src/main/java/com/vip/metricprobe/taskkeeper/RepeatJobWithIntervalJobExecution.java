package com.vip.metricprobe.taskkeeper;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by dongqingswt on 14-11-23.
 */
public class RepeatJobWithIntervalJobExecution extends BaseJobExecution {

    private int maxRepeatTimes;

    private long intervalInMills;


    private AtomicInteger repeatTimes = new AtomicInteger(1) ;

    public RepeatJobWithIntervalJobExecution(int maxRepeatTimes ,long intervalInMills){

        this.maxRepeatTimes = maxRepeatTimes;
        this.intervalInMills = intervalInMills;
    }



    @Override
    public JobResult executeJob(JobContext context) {

        JobResult jobResult = null ;
        while((jobResult = super.executeJob(context)) != null &&  !jobResult.isSuccess() && repeatTimes.incrementAndGet() <= maxRepeatTimes){
            try {
                Thread.currentThread().sleep(intervalInMills);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return jobResult;

    }









}
