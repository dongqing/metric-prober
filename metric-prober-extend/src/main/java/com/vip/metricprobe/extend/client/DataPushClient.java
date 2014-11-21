package com.vip.metricprobe.extend.client;

import java.util.List;
import java.util.Map;

/**
 * 数据推送Client;
 * Created by dongqingswt on 14-11-9.
 */
public class DataPushClient {


//    private ThreadPoolExecutor threadPoolExecutor ;

    private List<OuterSystemClient> outerSystemClientList;

    public DataPushClient(){
//    	threadPoolExecutor = new ThreadPoolExecutor(5,10, 20000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5) ,new ThreadPoolExecutor.DiscardPolicy());
    }

    public void setOuterSystemClientList(List<OuterSystemClient> outerSystemClientList) {
        this.outerSystemClientList = outerSystemClientList;
    }

    public void push(final Map<String,Integer> apiCallResult) {

        for(final OuterSystemClient outerSystemClient: outerSystemClientList){
            outerSystemClient.pushApiCall(apiCallResult);
        }


    }




}
