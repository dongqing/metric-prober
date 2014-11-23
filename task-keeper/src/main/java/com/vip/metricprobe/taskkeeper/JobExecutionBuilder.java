package com.vip.metricprobe.taskkeeper;

/**
 * Created by dongqingswt on 14-11-23.
 */
public class JobExecutionBuilder {

    private static final JobExecution SUCCESS_END_JOB_EXECUTION =
            new SuccessEndJobExecution();

    private  static final JobExecution FAILURE_END_JOB_EXECUTION =
            new FailureEndJobExecution() ;

    private  static volatile  JobExecutionBuilder jobExecutionBuilder;

    private int maxRepeatTimes = 0;
    private long repeatIntervalInMills = 0;

    private JobExecutionBuilder(){

    }

    public static JobExecutionBuilder getInstance(){

        if(null == jobExecutionBuilder){
            synchronized (JobExecutionBuilder.class){
                if(null == jobExecutionBuilder){
                    jobExecutionBuilder = new JobExecutionBuilder();
                }
            }
        }
        return jobExecutionBuilder;

    }


    public static JobExecution getSuccessEnd(){
        return SUCCESS_END_JOB_EXECUTION;
    }

    public static JobExecution getFailureEnd(){
        return FAILURE_END_JOB_EXECUTION;
    }


    public JobExecutionBuilder repeat(int maxRepeatTimes){
        this.maxRepeatTimes = maxRepeatTimes;
        return this;
    }

    public JobExecutionBuilder repeatIntervalInMills(long repeatIntervalInMills){
        this.repeatIntervalInMills = repeatIntervalInMills;
        return this;
    }

    public JobExecution build(){
           if(maxRepeatTimes > 0  &&  repeatIntervalInMills > 0){
               return  new RepeatJobWithIntervalJobExecution(this.maxRepeatTimes , this.repeatIntervalInMills);
           }else{

               return new BaseJobExecution();
           }
    }
}
