package com.vip.metricprobe.extend.api;

/**
 *
 * api 接口调用监听器
 * Created by dongqingswt on 14-11-18.
 */
public interface ApiCallListener {


    /**
     * api被调用， api格式. className.methodName(argtype,argtype);
     * 比如worker.doWork(string,string) ;
     * @param api
     */
    public void onCallApi(String api );


    /**
     * api被调用 ， api格式. className.methodName(argtype,argtype);
     * 比如worker.doWork(string,string) ;
     * @param api    被调用的api  .
     * @param count  被调用的次数 。
     */
    public void onCallApi(String api, int count);


}
