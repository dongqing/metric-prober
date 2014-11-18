package com.vip.metricprobe.extend.telescope;

import com.vip.metricprobe.extend.datachannel.DataFeatureHolder;
import com.vip.metricprobe.extend.domain.DataFeature;
import com.vip.metricprobe.extend.domain.DataPushFeature;
import com.vip.metricprobe.telescope.sdk.domain.APICallMetric;
import com.vip.metricprobe.telescope.sdk.domain.APICallMetrics;
import org.springframework.beans.factory.InitializingBean;

import java.util.HashMap;
import java.util.List;


/**
 *  telescope 系统的数据特性持有器...
 */
public class TelescopeDataFeatureHolder extends DataFeatureHolder implements InitializingBean {


    private APICallMetrics apiCallMetrics;


    private HashMap<String, DataFeature> api2DataFeatureMap;


    public void setApiCallMetrics(APICallMetrics apiCallMetrics) {
        this.apiCallMetrics = apiCallMetrics;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<APICallMetric> apiCallMetricList = apiCallMetrics.getApiCallMetricList();

        api2DataFeatureMap = new HashMap<String, DataFeature>(apiCallMetricList.size()) ;

        DataPushFeature dataPushFeature = null;
        for(APICallMetric apiCallMetric:apiCallMetricList){
            dataPushFeature = new DataPushFeature(apiCallMetric.getScanIntervalInMills());
            //存放API的数据推送特性。
            api2DataFeatureMap.put(apiCallMetric.getApi(),dataPushFeature);
            this.addDataFeature(dataPushFeature);
        }

    }

    public DataFeature getApiCallDataFeature(String api){
        return this.api2DataFeatureMap.get(api);
    }

}
