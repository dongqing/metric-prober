package com.vip.metricprobe.extend.probe;


import com.vip.metricprobe.extend.datachannel.DataChannel;
import com.vip.metricprobe.extend.domain.Data;

/**
 *
 * 数据采集器  ...
 * Created by dongqingswt on 14-11-8.
 */
public interface DataProber {

    /**
     * 获取被采集的数据；
     * @param data  被采集的数据
     */
    public void getProbeData(Data data);

    /**
     * 在数据探测器上注册数据通道， 当探测器探测到数据时，会分发到指定的数据通道.
     * @param  data  采集的数据
     * @param dataChannel　采集数据对应的数据通道
     */
    public void registerDataChannel(Class<? extends Data> data, DataChannel dataChannel);


}
