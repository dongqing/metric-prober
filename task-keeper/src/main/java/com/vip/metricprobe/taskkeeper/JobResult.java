package com.vip.metricprobe.taskkeeper;

/**
 * Created by dongqingswt on 14-11-23.
 */
public class JobResult {


    public  static final JobResult SUCCESS = new JobResult(1);
    public  static final JobResult FAILURE = new JobResult(2);

    private int value ;


    public boolean isSuccess(){
        return SUCCESS.equals(this);
    }

    public JobResult(int value){
        this.value = value ;
    }

    public int hashCode(){
        return this.value;
    }

    public boolean equals(Object obj){
        if(obj == null || !(obj instanceof  JobResult)){
            return false;
        }
        JobResult jobResult = (JobResult)obj;
        return jobResult.value == this.value;
    }
}
