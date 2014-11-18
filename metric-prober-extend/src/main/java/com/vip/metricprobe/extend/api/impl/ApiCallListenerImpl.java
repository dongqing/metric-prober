package com.vip.metricprobe.extend.api.impl;

import com.vip.metricprobe.extend.api.ApiCallListener;
import com.vip.metricprobe.extend.datachannel.DataChannel;
import com.vip.metricprobe.extend.domain.APICallProbeData;
import com.vip.metricprobe.extend.domain.DataFeature;
import com.vip.metricprobe.extend.domain.OneApiCallProbeData;
import com.vip.metricprobe.extend.probe.DataProber;
import com.vip.metricprobe.extend.telescope.TelescopeDataFeatureHolder;


/**
 * api调用侦听器的实现类 ，业务系统在使用的时候，直接调用onCallApi 方法进行api调用通知
 * 即可。
 *  ...
 *
 * Created by dongqingswt on 14-11-8.
 */
public class ApiCallListenerImpl  implements ApiCallListener {

    private DataProber dataProber;

    private DataChannel dataChannel ;


    private TelescopeDataFeatureHolder telescopeDataFeatureHolder;


    public ApiCallListenerImpl(DataProber dataProber
            , DataChannel dataChannel,TelescopeDataFeatureHolder telescopeDataFeatureHolder){

        this.dataProber  = dataProber;
        this.dataChannel = dataChannel ;

        this.telescopeDataFeatureHolder = telescopeDataFeatureHolder;

        //注册两种数据的处理通道。
        this.dataProber.registerDataChannel(OneApiCallProbeData.class,this.dataChannel);
        this.dataProber.registerDataChannel(APICallProbeData.class,this.dataChannel);


    }




    @Override
    public void onCallApi(String api) {
        this.onCallApi(api,1);
    }

    @Override
    public void onCallApi(String api, int count) {

        //找到某一个api的处理特性。
        DataFeature dataFeature = this.telescopeDataFeatureHolder.getApiCallDataFeature(api);
        if(dataFeature == null){
            return; // 找不到对应api的数据特性 ，不处理。
        }
        APICallProbeData apiCallProbeData = new APICallProbeData(api,count);
        apiCallProbeData.setDataFeature(dataFeature);
        this.dataProber.getProbeData(apiCallProbeData);

    }


}
