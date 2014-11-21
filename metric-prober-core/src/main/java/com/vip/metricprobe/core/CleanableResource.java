package com.vip.metricprobe.core;

/**
 *  需要在关闭应用时做资源清理的类需要实现这个接口。
 * Created by dongqingswt on 14-11-21.
 */
public interface CleanableResource {

    /**
     * 清理资源。
     */
    public void cleanResource();


}
