package com.vip.metricprobe.extend.datachannel;

import com.vip.metricprobe.extend.domain.DataFeature;

import java.util.HashSet;
import java.util.Set;


/**
 * 数据特性持有器....
 * @author dongqingswt
 *
 */
public class DataFeatureHolder  {


	private Set<DataFeature> dataFeatures = new HashSet<DataFeature>();

	public Set<DataFeature> getDataFeatures() {
		return dataFeatures;
	}

	public void setDataFeatures(Set<DataFeature> dataFeatures) {
		this.dataFeatures = dataFeatures;
	} 
	
	public void addDataFeature(DataFeature dataFeature){
		this.dataFeatures.add(dataFeature); 
	}
	
	
	
	
	
	
}
