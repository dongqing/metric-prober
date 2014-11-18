package com.vip.metricprobe.core.context;

import com.vip.metricprobe.core.domain.Component;
import com.vip.metricprobe.core.exception.MetricProbeException;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 *   组件的指标上下文容器，因为一个组件可能关心不同的 指标，每一个指标的处理有不同的上下文，
 *
 *   所以这里用了一个容器类进行了封装。
 * Created by dongqingswt on 14-11-17.
 */
public class ComponentMetricContextContainer {


    private final Map<Component, List<ComponentMetricContext>>  component2MetricContextsMap;

    private final  ReentrantLock lock  = new ReentrantLock();


    public ComponentMetricContextContainer() {
        component2MetricContextsMap = new HashMap<Component, List<ComponentMetricContext>>();
    }


    public final  void add(Component component,ComponentMetricContext componentMetricContext){

        lock.lock();
        try{
            List<ComponentMetricContext> list = component2MetricContextsMap.get(component);
            if(list == null){
                list = new ArrayList<ComponentMetricContext>();
                this.component2MetricContextsMap.put(component,list);
            }
            list.add(componentMetricContext) ;
        }finally{
            lock.unlock();
        }

    }


    public final Map getComponent2MetricContextsMap(){
        return Collections.unmodifiableMap(this.component2MetricContextsMap);
    }



    public boolean isContainerValid() throws MetricProbeException {
        if(component2MetricContextsMap == null || component2MetricContextsMap.size() == 0){
            return false;
        }

        Iterator iterator = component2MetricContextsMap.entrySet().iterator();
        Map.Entry<Component, List<ComponentMetricContext>> entry = null;
        Component component = null;

        StringBuilder errmsg = new StringBuilder();
        while(iterator.hasNext()){
           entry = (Map.Entry<Component, List<ComponentMetricContext>>) iterator.next();
           component = entry.getKey();
            if(component == null){
                errmsg.append(" [component can not be null] ");
                errmsg.append("\r\n");
            } else{
                if(component.getId() == null){
                    errmsg.append(" [component id can not be null ] ");
                    errmsg.append("\r\n");
                }else{
                    List<ComponentMetricContext> contexts = entry.getValue();
                    if(contexts == null || contexts.size() == 0){
                        errmsg.append(" [the ComponentMetricContext of component ").
                                append(component).append(" can not be empty] ") ;
                        errmsg.append("\r\n");

                    }else{
                        for(ComponentMetricContext componentMetricContext:contexts){
                              if(componentMetricContext.getProbeConfig() == null
                                  || componentMetricContext.getProbeConfig().getProbeInterval()
                                  == null ){
                                  errmsg.append(" [the probeconfig of  ")
                                          .append(componentMetricContext).append(" is incorrect] ");
                                  errmsg.append("\r\n");
                              }
                        }
                    }
                }
            }


        }


        if(errmsg != null && errmsg.length() > 0){
            throw new MetricProbeException("ComponentMetricContextContainer is incorrect ,errmsg:"
            + errmsg.toString());


        }

        return true ;


    }


}
