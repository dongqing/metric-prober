package com.vip.metricprobe.extend.domain;

/**
 * 表示一次api调用的探测数据 。
 */
public class OneApiCallProbeData extends APICallProbeData{

    public OneApiCallProbeData(String api){
        super(api ,1 );
    }
}
