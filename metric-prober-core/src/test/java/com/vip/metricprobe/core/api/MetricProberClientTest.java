package com.vip.metricprobe.core.api;

import com.vip.metricprobe.core.BaseTestCase;
import com.vip.metricprobe.core.GetMetricCallback;
import com.vip.metricprobe.core.config.ProbeConfig;
import com.vip.metricprobe.core.config.ProbeInterval;
import com.vip.metricprobe.core.config.ProbeTimeUnit;
import com.vip.metricprobe.core.domain.BizMetric;
import com.vip.metricprobe.core.domain.Component;
import com.vip.metricprobe.core.domain.Metric;
import com.vip.metricprobe.core.domain.MetricValue;
import com.vip.metricprobe.core.exception.MetricProbeException;
import com.vip.metricprobe.core.listener.MetricValueListener;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


public class MetricProberClientTest extends BaseTestCase {

    @Resource
    MetricProberClient metricProberClient;



    @Test
    public  void testStartProbe() throws InterruptedException {


        //探测配置。
        ProbeConfig probeConfig = new ProbeConfig();
        probeConfig.setProbeInterval(new ProbeInterval(2000, ProbeTimeUnit.MILLS));

        //组件。
        Component component = new Component() ;
        component.setName("component1,");
        component.setOwner("dongqing.swt");
        component.setId("id1");


        //对metricvalue的监听器。
        MetricValueListener listener = new MetricValueListener() {
            @Override
            public void onGetMetricValue(MetricValue metricValue) {

                System.out.println("listener get  :" + metricValue);
            }
        }       ;
        List<MetricValueListener> listenerList= new ArrayList<MetricValueListener>(1);
        listenerList.add(listener);


        //设置component关心什么bizmetric,以probeConfig里面的时间间隔采集一次,获取metric的回调函数是什么
        metricProberClient.register(component, new BizMetric(), probeConfig,new GetMetricCallback(){
            @Override
            public MetricValue getMetricValue(Component component, Metric metric) {

                MetricValue metricValue = new MetricValue();
                metricValue.setComponent(component);
                metricValue.setMetric(metric);
                metricValue.setValue(1);
                return metricValue;

            }
        },listenerList );






        ///--------------

        //探测配置。
//        ProbeConfig probeConfig2 = new ProbeConfig();
//        probeConfig2.setProbeInterval(new ProbeInterval(10000, ProbeTimeUnit.MILLS));
//
//        //组件。
//        Component component2 = new Component() ;
//        component2.setName("component2,");
//        component2.setOwner("dongqing.swt");
//        component2.setId("id2");
//
//
//        //对metricvalue的监听器。
//        MetricValueListener listener2 = new MetricValueListener() {
//            @Override
//            public void onGetMetricValue(MetricValue metricValue) {
//
//                System.out.println("listener2 get  :" + metricValue);
//            }
//        }       ;
//        List<MetricValueListener> listenerList2 = new ArrayList<MetricValueListener>(1);
//        listenerList2.add(listener2);
//
//                                                                                                                    QuartzScheduler
//        //设置component关心什么bizmetric,以probeConfig里面的时间间隔采集一次,获取metric的回调函数是什么
//        metricProberClient.register(component2, new BizMetric(), probeConfig2,new GetMetricCallback(){
//            @Override
//            public MetricValue getMetricValue(Component component, Metric metric) {
//
//                MetricValue metricValue = new MetricValue();
//                metricValue.setComponent(component);
//                metricValue.setMetric(metric);
//                metricValue.setValue(12);
//                return metricValue;
//
//            }
//        },listenerList2 );
//
//
//        //---------
//
        try {
            metricProberClient.startProbe();
        } catch (MetricProbeException e) {
            e.printStackTrace();
        }
//
//



    }

}
