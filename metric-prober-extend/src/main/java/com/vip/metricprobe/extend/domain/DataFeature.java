package com.vip.metricprobe.extend.domain;

/**
 * 数据特性，用于{@link com.vip.metricprobe.extend.datachannel.BaseDataChannel} 在进行数据分发时使用。
 * 基本的思路是，被分发的数据{@link com.vip.metricprobe.extend.domain.Data} 如果有一个DataFeature ,那么
 * 它会被分发到具有相同DataFeature的Pipeline中去 。
 * @author dongqingswt
 *
 */
public interface DataFeature {


}
