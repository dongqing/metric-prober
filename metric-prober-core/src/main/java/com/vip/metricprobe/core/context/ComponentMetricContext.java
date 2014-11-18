package com.vip.metricprobe.core.context;

import com.vip.metricprobe.core.collector.MetricValueCollector;
import com.vip.metricprobe.core.config.ProbeConfig;
import com.vip.metricprobe.core.domain.Component;
import com.vip.metricprobe.core.domain.Metric;
import com.vip.metricprobe.core.listener.MetricValueListener;

import java.util.List;

/**
 * 组件的指标上下文 。
 * Created by dongqingswt on 14-11-17.
 */
public class ComponentMetricContext {

    /**
     *  什么指标
     */
    private Metric metric;


    /**
     *  和探测相关的配置
     */

    private ProbeConfig probeConfig;


    /**
     * 指标收集器
     */
    private MetricValueCollector metricValueCollector;

    /***
     * 什么组件
     */
    private Component component;

    /**
     * 接收metricvalue的监听器。
     */
    private List<MetricValueListener> listeners;

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    public ProbeConfig getProbeConfig() {
        return probeConfig;
    }

    public void setProbeConfig(ProbeConfig probeConfig) {
        this.probeConfig = probeConfig;
    }

    public MetricValueCollector getMetricValueCollector() {
        return metricValueCollector;
    }

    public void setMetricValueCollector(MetricValueCollector metricValueCollector) {
        this.metricValueCollector = metricValueCollector;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public List<MetricValueListener> getListeners() {
        return listeners;
    }

    public void setListeners(List<MetricValueListener> listeners) {
        this.listeners = listeners;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(" ComponentMetricContext:[").append(this.component).append(",")
        .append(this.metric).append("] ");
        return s.toString();
    }
}
