package com.vip.metricprobe.extend.domain;


/**
 * API调用量数据。
 * Created by dongqingswt on 14-11-8.
 */
public class APICallProbeData extends ProbeData {

    /**
     * 调用的api ;
     */
    private String api ;

    /**
     *调用次数 。
     */
    private int count;

    public APICallProbeData(String api ,int count){
        this.api  = api ;
        this.count = count;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("[APICallProbeData][api:").append(api).append(",").append("count:").append(this.count).append("]");
        return s.toString();
    }


}
