package com.vip.metricprobe.core.api.impl;

import com.vip.metricprobe.core.CleanableResource;
import com.vip.metricprobe.core.GetMetricCallback;
import com.vip.metricprobe.core.api.MetricProberClient;
import com.vip.metricprobe.core.config.ProbeConfig;
import com.vip.metricprobe.core.domain.BizMetric;
import com.vip.metricprobe.core.domain.Component;
import com.vip.metricprobe.core.domain.SystemMetric;
import com.vip.metricprobe.core.engine.MetricCollectEngine;
import com.vip.metricprobe.core.exception.MetricProbeException;
import com.vip.metricprobe.core.listener.MetricValueListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * 默认的探测器实现
 * Created by dongqingswt on 14-11-17.
 */
public class MetricProberClientImpl implements MetricProberClient, ApplicationContextAware {



    @Resource
    private MetricCollectEngine metricCollectEngine;

    private ApplicationContext ac;

    private void addCleanResourceHook(){
        /**
         * 做一个关闭资源的钩子。
         */
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){
            @Override
            public void run() {
                if(ac == null){
                    return;
                }

                Map<String,CleanableResource> map =   ac.getBeansOfType(CleanableResource.class);
                if(map  != null && map.size() > 0){
                    Iterator<CleanableResource> iterator = map.values().iterator();
                    CleanableResource cleanableResource = null;
                    while(iterator.hasNext()){
                        cleanableResource =  iterator.next();
                        if(cleanableResource != null){
                            cleanableResource.cleanResource();
                        }
                    }

                }
            }
        }));
    }

    @Override
    public void register(Component component, SystemMetric systemMetric, ProbeConfig probeConfig) {

        metricCollectEngine.register(component,systemMetric, probeConfig);
    }

    @Override
    public void register(Component component, BizMetric bizMetric, ProbeConfig probeConfig,GetMetricCallback getMetricCallback
    ,List<MetricValueListener> listenerList) {

        metricCollectEngine.register(component,bizMetric,probeConfig,getMetricCallback, listenerList);
    }

    @Override
    public void startProbe() throws MetricProbeException {

        this.metricCollectEngine.start();



    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.ac = applicationContext;



        this.addCleanResourceHook();
    }


}
