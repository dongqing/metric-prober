package com.vip.metricprobe.core.collector;

import com.vip.metricprobe.core.domain.Component;
import com.vip.metricprobe.core.domain.Metric;
import com.vip.metricprobe.core.domain.MetricValue;

/**
 *
 * 指标收集器
 * Created by dongqingswt on 14-11-17.
 */
public interface MetricValueCollector {

    /**
     * 收集指标值。
     * @param component
     * @param metric
     * @return
     */
    public MetricValue collectMetricValue(Component component ,Metric metric);
}
