package com.vip.metricprobe.core.schedule;

import com.vip.metricprobe.core.config.ProbeConfig;
import com.vip.metricprobe.core.context.ComponentMetricContext;
import com.vip.metricprobe.core.domain.Component;
import com.vip.metricprobe.core.domain.Metric;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.Callable;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 *  使用Quartz进行任务调度。
 * Created by dongqingswt on 14-11-18.
 */
public class QuartzScheduler implements  Scheduler {

    private static final String JOB_GROUP_NAME = "jobGroup";

    private static final String TRIGGER_GROUP_NAME = "triggerGroup";


    private final SchedulerFactory schedulerFactory  ;

    private final org.quartz.Scheduler scheduler ;



    public QuartzScheduler() throws SchedulerException {
        this.schedulerFactory = new StdSchedulerFactory();
        this.scheduler = this.schedulerFactory.getScheduler();
        this.scheduler.start();

    }


    @Override
    public void startJob(ComponentMetricContext context, Callable callable) throws SchedulerException {

        String jobName = this.getJobName(context) ;
        String triggerName = this.getTriggerName(context);
        ProbeConfig probeConfig = context.getProbeConfig();


        JobDetail jobDetail = newJob(QuartzJob.class).
                withIdentity(jobName, JOB_GROUP_NAME).build();
        jobDetail.getJobDataMap().put("job_callable",callable);

        Trigger trigger = newTrigger().withIdentity(triggerName,TRIGGER_GROUP_NAME).
                startNow().
                withSchedule(simpleSchedule().
                 withIntervalInMilliseconds(probeConfig.getProbeInterval().getTimeInMills()).
                        repeatForever())
                .build();

        this.scheduler.scheduleJob(jobDetail,trigger);




    }

    @Override
    public void startJob(ProbeConfig probeConfig, Callable callable,String jobName) throws SchedulerException {

        String triggerName = this.getTriggerName(jobName);


        JobDetail jobDetail = newJob(QuartzJob.class).
                withIdentity(jobName, JOB_GROUP_NAME).build();
        jobDetail.getJobDataMap().put("job_callable",callable);

        Trigger trigger = newTrigger().withIdentity(triggerName,TRIGGER_GROUP_NAME).
                startNow().
                withSchedule(simpleSchedule().
                        withIntervalInMilliseconds(probeConfig.getProbeInterval().getTimeInMills()).
                        repeatForever())
                .build();

        this.scheduler.scheduleJob(jobDetail,trigger);




    }



    private String getTriggerName(ComponentMetricContext context){

        if(context == null){
            return null;
        }

        return this.getJobName(context) + "_trigger";
    }

    private String getTriggerName(String jobName){
        return jobName + "_trigger";
    }

    private String getJobName(ComponentMetricContext context){
        if(context == null)
            return null;
        Component component = context.getComponent();
        Metric metric = context.getMetric();
        StringBuilder jobName = new StringBuilder() ;
        jobName.append(" [collect_metric_job:[component id:").
                append(component != null ? component.getId()  : null) .
                append("],[metric:").append(metric).append("]] ");
        return jobName.toString();

    }
}
