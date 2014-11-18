package com.vip.metricprobe.extend.telescope;

import com.vip.metricprobe.extend.client.OuterSystemClient;
import com.vip.metricprobe.telescope.sdk.client.TelescopeClient;
import com.vip.metricprobe.telescope.sdk.domain.APICallMetric;
import com.vip.metricprobe.telescope.sdk.domain.APICallMetrics;
import com.vip.metricprobe.telescope.sdk.domain.PushData;
import org.springframework.beans.factory.InitializingBean;

import java.util.*;


/**
 * 接口适配器，把telescope的接口转换过来。
 * Created by dongqingswt on 14-11-9.
 */
public class TelescopeClientAdaptor implements OuterSystemClient, InitializingBean{

    private TelescopeClient telescopeClient;

    private APICallMetrics apiCallMetrics;

    private Map<String,APICallMetric> api2APIMetric = null;

    public void setTelescopeClient(TelescopeClient telescopeClient) {
        this.telescopeClient = telescopeClient;
    }

    public void setApiCallMetrics(APICallMetrics apiCallMetrics) {
        this.apiCallMetrics = apiCallMetrics;
    }

    private  final List<PushData> getPushDataList(Map<String, Integer> api2Count){

        if(api2Count != null && api2Count.size() > 0 ){
            Iterator<Map.Entry<String,Integer>> iterator = api2Count.entrySet().iterator();
            List<PushData> pushDatas = new ArrayList<PushData>(api2Count.size());
            Map.Entry<String,Integer> entry = null;
            PushData pushData = null;
            while(iterator.hasNext()){
                entry = iterator.next();
                pushData  =   this.getPushData(entry.getKey(), entry.getValue());
                if(pushData == null ){
                    continue;
                }
                pushDatas.add(pushData);
            }
            return pushDatas;
        } else{
            return  null ;
        }
    }

    private PushData getPushData(String api, Integer count) {

        APICallMetric apiCallMetric = this.api2APIMetric.get(api);
        if(apiCallMetric == null){
            return null;
        }

        PushData pushData = new PushData();
        pushData.setId(apiCallMetric.getId());
        pushData.setKey(apiCallMetric.getKey());
        pushData.setTimestamp(System.currentTimeMillis());
        pushData.setValue(new Double(count));
        return pushData ;

    }


    @Override
    public void pushApiCall(Map<String, Integer> api2Count) {


        List<PushData> pushDatas = this.getPushDataList(api2Count);
        if(pushDatas == null || pushDatas.size() == 0){
            return ;
        }
       
        this.telescopeClient.push(pushDatas); 
        


    }


    @Override
    public void afterPropertiesSet() throws Exception {
        List<APICallMetric> apiCallMetricList = apiCallMetrics.getApiCallMetricList();
        if(apiCallMetricList != null && apiCallMetricList.size() > 0){
            api2APIMetric = new HashMap<String, APICallMetric>(apiCallMetricList.size());
            for(APICallMetric apiCallMetric:apiCallMetricList){
                api2APIMetric.put(apiCallMetric.getApi(), apiCallMetric);
            }
        }

    }
}