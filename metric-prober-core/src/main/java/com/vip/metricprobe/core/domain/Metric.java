package com.vip.metricprobe.core.domain;

/**
 * 指标
 * Created by dongqingswt on 14-11-17.
 */
public class Metric {

    /**
     * 名称
     */
    private String name ;

    /**
     * 描述
     */
    private String description;




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(" metric:[name:").append(this.name).append(",description:").append(description)
        .append("] ");
        return s.toString();
    }
}
