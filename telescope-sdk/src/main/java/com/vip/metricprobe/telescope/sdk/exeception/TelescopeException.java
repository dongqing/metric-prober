package com.vip.metricprobe.telescope.sdk.exeception;

/**
 * Created by dongqingswt on 14-11-7.
 */
public class TelescopeException extends Exception {


    public TelescopeException(String msg){
        super(msg);
    }

    public TelescopeException(Throwable t){
        super(t);
    }

    public  TelescopeException(String msg , Throwable t ){
        super(msg,t );
    }
}
