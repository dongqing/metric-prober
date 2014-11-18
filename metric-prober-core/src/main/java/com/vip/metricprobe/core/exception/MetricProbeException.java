package com.vip.metricprobe.core.exception;

/**
 * Created by dongqingswt on 14-11-18.
 */
public class MetricProbeException extends Exception {

    public MetricProbeException(String msg){
        super(msg);
    }

    public MetricProbeException(String msg ,Throwable t ){
        super(msg,t);
    }


    public MetricProbeException(Throwable t){
        super(t);
    }
}
