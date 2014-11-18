package com.vip.metricprobe.extend.domain;


/**
 * 数据被推送的特性。
 * @author dongqingswt
 *
 */
public class DataPushFeature extends BaseDataFeature {

	/**
	 * 推送的时间间隔 ，单位ms;
	 */
	public int  pushIntervalInMills;

	public DataPushFeature(int  pushIntervalInMills) {
		this.pushIntervalInMills = pushIntervalInMills; 
	}

	public int  getPushIntervalInMills() {
		return pushIntervalInMills;
	}

	public void setPushIntervalInMills(int  pushIntervalInMills) {
		this.pushIntervalInMills = pushIntervalInMills;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null  || !(obj instanceof DataPushFeature)){
			return false;
		}
		DataPushFeature dataPushFeature = (DataPushFeature)obj; 
		return dataPushFeature.pushIntervalInMills == this.pushIntervalInMills; //如果推送的时间间隔一样，就认为是DataPushDataFeature一样的。
	} 
	
	public int hashCode(){
		return String.valueOf(pushIntervalInMills).hashCode(); 
	}
	
	
	
}
