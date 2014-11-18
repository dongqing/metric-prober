package com.vip.metricprobe.extend.domain;


/**
 * 采集的数据
 * Created by dongqingswt on 14-11-8.
 */
public   class ProbeData extends  Data {

    /**
     * 来自于哪个采集点。
     */
    private ProbeDataJoinPoint probeDataJoinPoint;


    public ProbeDataJoinPoint getProbeDataJoinPoint() {
        return probeDataJoinPoint;
    }

    public void setProbeDataJoinPoint(ProbeDataJoinPoint probeDataJoinPoint) {
        this.probeDataJoinPoint = probeDataJoinPoint;
    }



}
