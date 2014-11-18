package com.vip.metricprobe.telescope.sdk.domain;

import java.util.List;

/**
 * 一个telescope系统的指标配置。
 * Created by dongqingswt on 14-11-8.
 */
public class TelescopeMetric {

    /**
     * 指标名称；
     */
    private String name ;

    /**
     *  指标说明
     */
    private String description;

    /**
     * 所属系统
     */
    private String system ;

    /**
     * 数据单位
     */
    private String unit;


    /**
     * 上报周期（单位 ：ms）
     */
    private int  scanIntervalInMills ;

    /**
     * 负责人
     */
    private String owner;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 联系人邮箱。
     */
    private String contactEmail ;

    /**
     * 指标维度
     */
    private List<TelescopeMetricDimension> dimensionList;

    /**
     * 在 telescope系统注册统计指标时生成的id;
     */
    private String id;

    /**
     * 在 telescope系统注册统计指标时生成的key ;
     */
    private String key;

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

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int  getScanIntervalInMills() {
        return scanIntervalInMills;
    }

    public void setScanIntervalInMills(int  scanIntervalInMills) {
        this.scanIntervalInMills = scanIntervalInMills;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public List<TelescopeMetricDimension> getDimensionList() {
        return dimensionList;
    }

    public void setDimensionList(List<TelescopeMetricDimension> dimensionList) {
        this.dimensionList = dimensionList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
