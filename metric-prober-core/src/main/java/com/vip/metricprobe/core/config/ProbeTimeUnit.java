package com.vip.metricprobe.core.config;

/**
 * 探测时间单位 ,先提供millseconds这种最常用的单位。
 */
public enum ProbeTimeUnit{

    MILLS(0);
    int value;

    ProbeTimeUnit (int value){
        this.value = value;
    }

}