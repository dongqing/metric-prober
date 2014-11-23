package com.vip.metricprobe.taskkeeper;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 一直等待直到条件满足。
 * Created by dongqingswt on 14-11-23.
 */
public class WaitUntilConditionOKJobExecution extends BaseJobExecution {

    private final Lock lock = new ReentrantLock() ;

    private final Condition conditionOK = lock.newCondition();


    @Override
    public JobResult executeJob(final JobContext jobContext) {

        lock.lock();
        try{
            try {
                conditionOK.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return super.executeJob(jobContext);

        }finally{
            lock.unlock();
        }


    }


    public void onConditionOK(){

        lock.lock();
        try{
            conditionOK.signal();
        }finally{
            lock.unlock();
        }
    }





}
