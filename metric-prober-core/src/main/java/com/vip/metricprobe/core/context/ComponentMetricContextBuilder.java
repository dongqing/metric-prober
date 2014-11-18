package com.vip.metricprobe.core.context;

import com.vip.metricprobe.core.GetMetricCallback;
import com.vip.metricprobe.core.collector.BizMetricValueCollector;
import com.vip.metricprobe.core.collector.SystemMetricValueCollector;
import com.vip.metricprobe.core.config.ProbeConfig;
import com.vip.metricprobe.core.domain.BizMetric;
import com.vip.metricprobe.core.domain.Component;
import com.vip.metricprobe.core.domain.SystemMetric;
import com.vip.metricprobe.core.listener.MetricValueListener;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用于构建{@link ComponentMetricContext}
 * Created by dongqingswt on 14-11-17.
 */
public class ComponentMetricContextBuilder {


    @Resource
    private SystemMetricValueCollector systemMetricValueCollector;


    /**
     * 构建上下文
     * @param component  组件
     * @param systemMetric  系统指标
     * @param probeConfig   指标探测配置　。
     * @return
     */
    public  ComponentMetricContext build(Component component, SystemMetric systemMetric, ProbeConfig probeConfig) {

        ComponentMetricContext context = new ComponentMetricContext();
        context.setComponent(component);
        context.setMetric(systemMetric);
        context.setMetricValueCollector(systemMetricValueCollector);
        context.setProbeConfig(probeConfig);

        return context;
    }

    /**
     * 构建上下文
     * @param component　组件
     * @param bizMetric　业务指标
     * @param probeConfig   指标探测配置。
     * @param  getMetricCallback 获取指标值的回调函数
     * @return
     */
    public ComponentMetricContext build(Component component, BizMetric bizMetric, ProbeConfig probeConfig, GetMetricCallback getMetricCallback,List<MetricValueListener> listenerList) {
        ComponentMetricContext context = new ComponentMetricContext();
        context.setComponent(component);
        context.setMetric(bizMetric);
        context.setProbeConfig(probeConfig);
        context.setMetricValueCollector(new BizMetricValueCollector(getMetricCallback));
        context.setListeners(listenerList) ;
        return context;
    }
}
