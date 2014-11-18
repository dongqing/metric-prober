package com.vip.metricprobe.extend.chain;


import com.vip.metricprobe.extend.domain.Data;
import com.vip.metricprobe.extend.domain.DataFeature;

/**
 * 处理器
 * Created by dongqingswt on 14-11-8.
 */
public interface Handler {


    public void handle(Data data);
    
    public Handler next();

    public void setNext(Handler handler);

    /**
     * 处理器的深度拷贝 ，注意不是拷贝引用。
     * @return
     */
    public Handler copySelf();
    
    public void setDataFeature(DataFeature dataFeature) ;
    
    
    public void doOtherInitialWork(); 

}
