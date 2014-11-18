package com.vip.metricprobe.core.config;

public class ProbeInterval{

    private int count;

    private ProbeTimeUnit probeTimeUnit;


    public ProbeInterval(int count , ProbeTimeUnit probeTimeUnit){
        this.count = count;
        this.probeTimeUnit = probeTimeUnit;
    }


   public  ProbeInterval(int count){
        this.count = count;
        this.probeTimeUnit = ProbeTimeUnit.MILLS;
    }




    public long getTimeInMills(){
        if(probeTimeUnit == ProbeTimeUnit.MILLS){
            return this.count;
        }
        return this.count;
    }

}
