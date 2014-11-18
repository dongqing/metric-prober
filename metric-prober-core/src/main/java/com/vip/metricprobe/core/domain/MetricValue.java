package com.vip.metricprobe.core.domain;

/**
 * 某一个指标的值
 * Created by dongqingswt on 14-11-17.
 */
public class MetricValue<T>{

    /**
     * 哪个指标的值
     */
    private Component component;

    /**
     * 什么指标
     */
    private Metric metric;

    /**
     * 值是什么
     */
    private T value ;


    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }


    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" [metricValue:").append(component).append(metric).append(",value:").append(value)
                .append("] ") ;
        return stringBuilder.toString();
    }

}
