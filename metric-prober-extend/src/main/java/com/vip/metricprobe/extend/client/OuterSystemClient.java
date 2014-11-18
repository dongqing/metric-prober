package com.vip.metricprobe.extend.client;

import java.util.Map;

/**
 *  监控系统的client;
 * Created by dongqingswt on 14-11-9.
 */
public interface OuterSystemClient {

    /**
     * 推送api调用量。
     * @param api2Count
     */
    public void pushApiCall(Map<String, Integer> api2Count);


}
