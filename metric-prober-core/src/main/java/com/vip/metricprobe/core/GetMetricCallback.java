package com.vip.metricprobe.core;

import com.vip.metricprobe.core.domain.Component;
import com.vip.metricprobe.core.domain.Metric;
import com.vip.metricprobe.core.domain.MetricValue;

/**
 * 获取指标值的回调函数
 * Created by dongqingswt on 14-11-17.
 */
public interface GetMetricCallback {


    /**
     * 业务方通过这个回调接口提供指标的值 。
     * @param component
     * @param metric
     * @return
     */
    public MetricValue getMetricValue(Component component, Metric metric);
}
