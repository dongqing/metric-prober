package com.vip.metricprobe.extend.chain;


import com.vip.metricprobe.extend.domain.Data;
import com.vip.metricprobe.extend.domain.DataFeature;

/**
 * Created by dongqingswt on 14-11-8.
 */
public abstract class BaseHandler implements Handler{

    private Handler nextHandler;
    
    protected DataFeature dataFeature;

    public BaseHandler(Handler nextHandler){
        this.nextHandler = nextHandler ;
    }

    public BaseHandler() {

    }

    public Handler copySelf(){
        return this;
    }






    @Override
    public final void handle(Data data){
        this.doRealWork(data) ;
        if(this.nextHandler != null){
            this.nextHandler.handle(data);
        }
    }

    protected void doRealWork(Data data) {
            // do noop default;
    }


    @Override
    public Handler next() {
        return this.nextHandler;
    }

    @Override
    public void setNext(Handler nextHandler) {
          this.nextHandler = nextHandler;
    }
    
    public void setDataFeature(DataFeature dataFeature){
    	this.dataFeature = dataFeature; 
    }

	public DataFeature getDataFeature() {
		return dataFeature;
	}
    
	public void doOtherInitialWork(){
		//默认空操作。
	}
    
}

