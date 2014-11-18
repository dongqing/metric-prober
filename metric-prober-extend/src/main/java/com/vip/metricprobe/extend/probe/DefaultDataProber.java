package com.vip.metricprobe.extend.probe;


import com.vip.metricprobe.extend.datachannel.DataChannel;
import com.vip.metricprobe.extend.domain.Data;

import java.util.HashMap;
import java.util.Map;

/**
 *  数据采集器的默认实现
 *  ...
 * Created by dongqingswt on 14-11-8.
 */
public  class DefaultDataProber implements  DataProber{

    private DataChannel dataChannel;

    private Map<Class<? extends Data>, DataChannel> data2ChannelMap =
            new HashMap<Class<? extends Data>, DataChannel>(3);



    public void setDataChannel(DataChannel dataChannel) {
        this.dataChannel = dataChannel;
    }


    @Override
    public void getProbeData(Data data) {

    	//分发数据给对应的数据通道。
        DataChannel dataChannel =     data2ChannelMap.get(data.getClass()) ;
        dataChannel.pushData(data);

    }

    @Override
    public void registerDataChannel(Class<? extends Data> data, DataChannel dataChannel) {
        data2ChannelMap.put(data,dataChannel);
    }


}
