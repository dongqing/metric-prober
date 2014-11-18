package com.vip.metricprobe.core.collector;

import com.vip.metricprobe.core.GetMetricCallback;
import com.vip.metricprobe.core.domain.Component;
import com.vip.metricprobe.core.domain.Metric;
import com.vip.metricprobe.core.domain.MetricValue;

/**
 * 业务指标值的收集器 。
 * Created by dongqingswt on 14-11-17.
 */
public class BizMetricValueCollector implements MetricValueCollector {

    private GetMetricCallback getMetricCallback;

    public  BizMetricValueCollector(GetMetricCallback getMetricCallback){
        this.getMetricCallback = getMetricCallback;
    }

    @Override
    public MetricValue collectMetricValue(Component component, Metric metric) {

        return this.getMetricCallback.getMetricValue(component,metric);
    }
}
