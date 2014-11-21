package com.vip.metricprobe.extend.api;

import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by dongqingswt on 14-11-18.
 */
public class ApiCallListenerTest extends BaseTestCase {

    @Resource
    ApiCallListener apiCallListener;



    @Test
    public void testOnCallApi() throws InterruptedException {

        for(int i = 0; i < 1000; ++i){
            apiCallListener.onCallApi("className.methodName(argtype...)");
            apiCallListener.onCallApi("className.methodName2(argtype...)");
            Thread.currentThread().sleep(500);

        }


    }
}
