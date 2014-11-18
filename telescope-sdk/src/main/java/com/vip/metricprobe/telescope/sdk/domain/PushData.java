package com.vip.metricprobe.telescope.sdk.domain;

import java.util.LinkedList;
import java.util.List;



public class PushData {
    private String id;
    private String key;
    private long timestamp;
    private List<IndexEntry> index;
    private Double value;

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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public List<IndexEntry> getIndex() {

        return index;
    }

    public void setIndex(List<IndexEntry> index) {
        if (this.index == null) {
            this.index = new LinkedList<IndexEntry>();
        }
        this.index.addAll(index);
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(" [pushdata:").append("id:").append(this.id).append(",key:").append(this.key)
        .append("] ");
        return s.toString();
    }
}