package com.vip.metricprobe.telescope.sdk.domain;

import java.util.LinkedHashMap;
import java.util.Map;

public class IndexEntry {
    private Double value;
    private Map<String,String> dimension;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Map<String,String> getDimension() {
        return dimension;
    }

    public void setDimension(Map<String,String> dimension) {
        if (this.dimension == null) {
            this.dimension = new LinkedHashMap<String,String>();
        }
        this.dimension.putAll(dimension);
    }

    public void setDimension(String dimension, String value) {
        if (this.dimension == null) {
            this.dimension = new LinkedHashMap<String,String>();
        }
        this.dimension.put(dimension, value);
    }



}
