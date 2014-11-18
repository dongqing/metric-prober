package com.vip.metricprobe.extend.datachannel;


import com.vip.metricprobe.extend.domain.Data;

/**
 *  数据通道
 * Created by dongqingswt on 14-11-8.
 */
public interface DataChannel {

    /**
     * 推送数据给数据通道
     * @param data
     */
    public void pushData(Data data);
    

    
    
    
    
}
