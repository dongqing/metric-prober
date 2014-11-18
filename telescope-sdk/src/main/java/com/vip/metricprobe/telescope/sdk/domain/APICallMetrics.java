package com.vip.metricprobe.telescope.sdk.domain;

import java.util.List;

/**
 * Created by dongqingswt on 14-11-8.
 */
public class APICallMetrics {

    private List<APICallMetric> apiCallMetricList;

    public List<APICallMetric> getApiCallMetricList() {
        return apiCallMetricList;
    }

    public void setApiCallMetricList(List<APICallMetric> apiCallMetricList) {
        this.apiCallMetricList = apiCallMetricList;
    }
}
