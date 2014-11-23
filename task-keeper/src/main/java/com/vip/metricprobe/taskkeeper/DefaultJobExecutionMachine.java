package com.vip.metricprobe.taskkeeper;

/**
 * Created by dongqingswt on 14-11-23.
 */
public class DefaultJobExecutionMachine implements JobExecutionMachine {

    private JobExecution start;






    @Override
    public void setStartJobExecution(JobExecution jobExecution) {
        this.start = jobExecution;
    }

    @Override
    public void startJobExecution(JobContext context) {
        assert(start != null) ;

        JobExecution jobExecution = start;

        while(jobExecution != null ){
            JobResult jobResult =  jobExecution.executeJob(context);
            jobExecution = jobExecution.getNext(jobResult);
        }
    }
}
