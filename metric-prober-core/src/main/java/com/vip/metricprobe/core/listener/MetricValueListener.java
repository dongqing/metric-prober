package com.vip.metricprobe.core.listener;

import com.vip.metricprobe.core.domain.MetricValue;

/**
 * 监听MetricValue的值。
 * Created by dongqingswt on 14-11-17.
 */
public interface MetricValueListener {

    /**
     * 得到指标值。
     * @param metricValue
     */
    public void onGetMetricValue(MetricValue metricValue);
}
