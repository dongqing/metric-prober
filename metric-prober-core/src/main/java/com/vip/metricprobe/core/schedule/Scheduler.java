package com.vip.metricprobe.core.schedule;


import com.vip.metricprobe.core.config.ProbeConfig;
import com.vip.metricprobe.core.context.ComponentMetricContext;
import org.quartz.SchedulerException;

import java.util.concurrent.Callable;

/**
 *
 *    调度器接口，　用于在指定的时间间隔执行用户指定的任务。
 * Created by dongqingswt on 14-11-17.
 */
public interface Scheduler {


    /**
     * 根据probeInterval设定的时间间隔，做一个任务。
     * @param  context 组件的指标上下文
     * @param callable      定时处理的任务
     *
     */
    void startJob(ComponentMetricContext context, Callable callable) throws SchedulerException;


    /**|
     *  根据 probeConfig 设定的时间间隔， 做任务。
     * @param probeConfig    任务interval配置。
     * @param callable 任务。
     * @param jobName job的名称；
     */
    void startJob(ProbeConfig probeConfig , Callable callable,String jobName) throws SchedulerException;
}
