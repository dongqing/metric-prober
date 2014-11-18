package com.vip.metricprobe.core.api.impl;

import com.vip.metricprobe.core.GetMetricCallback;
import com.vip.metricprobe.core.api.MetricProberClient;
import com.vip.metricprobe.core.config.ProbeConfig;
import com.vip.metricprobe.core.domain.BizMetric;
import com.vip.metricprobe.core.domain.Component;
import com.vip.metricprobe.core.domain.SystemMetric;
import com.vip.metricprobe.core.engine.MetricCollectEngine;
import com.vip.metricprobe.core.exception.MetricProbeException;
import com.vip.metricprobe.core.listener.MetricValueListener;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * 默认的探测器实现
 * Created by dongqingswt on 14-11-17.
 */
public class MetricProberClientImpl implements MetricProberClient {



    @Resource
    private MetricCollectEngine metricCollectEngine;


    @Override
    public void register(Component component, SystemMetric systemMetric, ProbeConfig probeConfig) {

        metricCollectEngine.register(component,systemMetric, probeConfig);
    }

    @Override
    public void register(Component component, BizMetric bizMetric, ProbeConfig probeConfig,GetMetricCallback getMetricCallback
    ,List<MetricValueListener> listenerList) {

        metricCollectEngine.register(component,bizMetric,probeConfig,getMetricCallback, listenerList);
    }

    @Override
    public void startProbe() throws MetricProbeException {

        this.metricCollectEngine.start();

    }
}
