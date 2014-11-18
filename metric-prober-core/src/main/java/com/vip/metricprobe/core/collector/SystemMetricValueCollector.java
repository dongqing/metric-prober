package com.vip.metricprobe.core.collector;

import com.vip.metricprobe.core.domain.Component;
import com.vip.metricprobe.core.domain.Metric;
import com.vip.metricprobe.core.domain.MetricValue;

/**
 * 系统指标收集器 。
 *
 * Created by dongqingswt on 14-11-17.
 */
public class SystemMetricValueCollector implements MetricValueCollector {

    @Override
    public MetricValue collectMetricValue(Component component, Metric metric) {
        return null;
    }
}
