package com.vip.metricprobe.core.engine;

import com.vip.metricprobe.core.GetMetricCallback;
import com.vip.metricprobe.core.collector.MetricValueCollector;
import com.vip.metricprobe.core.config.ProbeConfig;
import com.vip.metricprobe.core.context.ComponentMetricContext;
import com.vip.metricprobe.core.context.ComponentMetricContextBuilder;
import com.vip.metricprobe.core.context.ComponentMetricContextContainer;
import com.vip.metricprobe.core.domain.BizMetric;
import com.vip.metricprobe.core.domain.Component;
import com.vip.metricprobe.core.domain.MetricValue;
import com.vip.metricprobe.core.domain.SystemMetric;
import com.vip.metricprobe.core.exception.MetricProbeException;
import com.vip.metricprobe.core.listener.MetricValueListener;
import com.vip.metricprobe.core.schedule.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 指标收集引擎
 * Created by dongqingswt on 14-11-17.
 */
public class MetricCollectEngine {

    private static final  Logger logger = LoggerFactory.getLogger("MetricCollectEngine");

    private  volatile AtomicBoolean isEngineStart = new AtomicBoolean(false);

    @Resource
    private ComponentMetricContextContainer componentMetricContextContainer;

    @Resource
    private ComponentMetricContextBuilder componentMetricContextBuilder;

    @Resource
    private Scheduler scheduler;


    /**
     * 检查 componentMetricContextContainer中的内容是否有效。
     */
    private boolean  checkComponentMetricContextContainer() throws MetricProbeException {

        return this.componentMetricContextContainer.isContainerValid();


    }

    private void start(List<ComponentMetricContext> list) throws MetricProbeException {

        for(ComponentMetricContext c:list){
            this.start(c);
        }

    }

    private void start(final ComponentMetricContext context) throws MetricProbeException {


        if(context == null){
            return ;
        }

        logger.info("start ComponentMetricContext {}", context);


        ProbeConfig probeConfig = context.getProbeConfig();
        if(probeConfig.getProbeInterval() != null){
            //有定时探测的配置, 则启动Job;
            try{
                this.scheduler.startJob(context, new Callable() {

                    @Override
                    public Object call() throws Exception {
                        MetricValueCollector collector = context.getMetricValueCollector();
                        MetricValue metricValue = collector.collectMetricValue(context.getComponent(),context.getMetric());

                        List<MetricValueListener> listeners = context.getListeners();
                        if(listeners != null && listeners.size() > 0){
                            for(MetricValueListener listener:listeners){
                                listener.onGetMetricValue(metricValue);
                            }
                        }
                        return true ;

                    }
                });
            } catch(SchedulerException e){
                logger.error("[ startJob error, the ComponentMetricContext:{} ] ",context,e);
                throw new MetricProbeException(e);
            }


        }


    }


    /**

     * 启动指标收集引擎
     */
    public void start() throws MetricProbeException {



        this.checkComponentMetricContextContainer();


        if(isEngineStart.compareAndSet(false,true)){

            logger.info("start MetricCollectEngine  on {}",new Date() );
            Map<Component, List<ComponentMetricContext>> map = this.componentMetricContextContainer.getComponent2MetricContextsMap();
            if(map != null && map.size() > 0) {
                Iterator<Map.Entry<Component,List<ComponentMetricContext>>> iterator =  map.entrySet().iterator() ;
                Map.Entry<Component,List<ComponentMetricContext >>  entry = null;
                while(iterator.hasNext()){
                    entry = iterator.next();
                    try {
                        this.start(entry.getValue());
                    }catch (MetricProbeException e) {
                        isEngineStart.set(false);          //把引擎启动标识设置为false;
                        throw e ;
                    }
                }

            }
        }else{

            logger.info("Threre is a thread  which has  started the  MetricCollectEngine " );

        }


    }

    /**
     *  注册某一个组件关心一个系统指标， 并设置系统指标的探测配置。
     * @param component
     * @param systemMetric
     * @param probeConfig
     */
    public void register(Component component , SystemMetric systemMetric , ProbeConfig probeConfig){

        //构建一个上下文
        ComponentMetricContext context = componentMetricContextBuilder.build(component, systemMetric, probeConfig);
        componentMetricContextContainer.add(component, context);

    }


    /**
     * 注册某一个组件关心一个业务指标， 并设置系统指标的探测配置
     * @param component
     * @param bizMetric
     * @param probeConfig
     * @param  getMetricCallback
     */
    public void register(Component component, BizMetric bizMetric, ProbeConfig probeConfig, GetMetricCallback getMetricCallback, List<MetricValueListener> listeners) {
        ComponentMetricContext  context = componentMetricContextBuilder.build(component, bizMetric, probeConfig, getMetricCallback, listeners);
        componentMetricContextContainer.add(component, context);

    }

}
