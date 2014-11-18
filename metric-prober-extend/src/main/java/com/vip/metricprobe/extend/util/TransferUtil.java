package com.vip.metricprobe.extend.util;

import com.vip.metricprobe.core.config.ProbeConfig;
import com.vip.metricprobe.core.config.ProbeInterval;
import com.vip.metricprobe.core.config.ProbeTimeUnit;
import com.vip.metricprobe.extend.domain.DataPushFeature;

/**
 * 对象转换器
 * Created by dongqingswt on 14-11-18.
 */
public class TransferUtil {

    public static ProbeConfig getProbeConfig(DataPushFeature dataPushFeature){

        if(dataPushFeature == null){
            return null ;
        }

        ProbeConfig probeConfig = new ProbeConfig();
        probeConfig.setProbeInterval(new ProbeInterval(dataPushFeature.getPushIntervalInMills(), ProbeTimeUnit.MILLS));
        return probeConfig;
    }
}
