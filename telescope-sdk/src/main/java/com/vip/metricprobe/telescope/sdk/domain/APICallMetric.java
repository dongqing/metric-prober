package com.vip.metricprobe.telescope.sdk.domain;

/**
 * API调用指标。
 * Created by dongqingswt on 14-11-8.
 */
public class APICallMetric extends TelescopeMetric {

    /**
     * 被调用的api ;
     */
    private String api  ;


    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }
}
