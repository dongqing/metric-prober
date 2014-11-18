package com.vip.metricprobe.core.api;

import com.vip.metricprobe.core.GetMetricCallback;
import com.vip.metricprobe.core.config.ProbeConfig;
import com.vip.metricprobe.core.domain.BizMetric;
import com.vip.metricprobe.core.domain.Component;
import com.vip.metricprobe.core.domain.SystemMetric;
import com.vip.metricprobe.core.exception.MetricProbeException;
import com.vip.metricprobe.core.listener.MetricValueListener;

import java.util.List;

/**
 * 宿主 app使用的api
 * Created by dongqingswt on 14-11-17.
 */
public interface MetricProberClient {

    /**
     *  注册某一个组件关心一个系统指标， 并设置系统指标的探测配置。
     * @param component
     * @param systemMetric
     * @param probeConfig
     */
    public void register(Component component , SystemMetric systemMetric , ProbeConfig probeConfig);


    /**
     * 注册某一个组件关心一个业务指标， 并设置系统指标的探测配置
     * @param component
     * @param bizMetric
     * @param probeConfig
     * @param getMetricCallback
     * @param  listenerList
     */
    public void register(Component component, BizMetric bizMetric ,ProbeConfig probeConfig ,
                         GetMetricCallback getMetricCallback,
                         List<MetricValueListener> listenerList);


    /**
     * 开始启动指标探测。
     */
    public void startProbe() throws MetricProbeException;

}
