package com.vip.metricprobe.core.schedule;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * Created by dongqingswt on 14-11-18.
 */
public class QuartzJob implements Job {


    private static final  Logger logger  = LoggerFactory.getLogger("QuartzJobLogger");

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {


        Callable callable = (Callable) context.getJobDetail().getJobDataMap().get("job_callable");
        try {
            callable.call();
        } catch (Exception e) {
            logger.error("execute QuartzJob error {}",  "",  e );

        }
    }


}
