package com.vip.metricprobe.extend.chain;

import com.vip.metricprobe.core.config.ProbeConfig;
import com.vip.metricprobe.core.schedule.Scheduler;
import com.vip.metricprobe.extend.client.DataPushClient;
import com.vip.metricprobe.extend.domain.APICallProbeData;
import com.vip.metricprobe.extend.domain.Data;
import com.vip.metricprobe.extend.domain.DataFeature;
import com.vip.metricprobe.extend.domain.DataPushFeature;
import com.vip.metricprobe.extend.util.TransferUtil;
import com.vip.metricprobe.telescope.sdk.domain.APICallMetric;
import com.vip.metricprobe.telescope.sdk.domain.APICallMetrics;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * 用于计算api 调用总量。
 * Created by dongqingswt on 14-11-8.
 */
public class ApiCallHandler extends BaseHandler implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger("apiCallHandlerLogger");


    /**
     * 做APICallHandler的计数， 表示名称用。
     */
    private static final AtomicInteger index = new AtomicInteger(0);

	//在spring容器初始化的时候，把xml配置中的
	//APICallMetric 列表注入，然后从APICallMetric 列表初始化需要计数
	//的api ;
	private Map<String,AtomicInteger> api2CallCountMap = new HashMap<String, AtomicInteger>();


	/**
	 * 数据推送开关，这个开关打开的时候，把api2CallCountMap 拷贝出来一份
	 * 推送出去 ，会短暂的阻塞ApiHandler所在的Handler;
	 * TODO 考虑更好的控制方式 。
	 */
	private AtomicBoolean dataPushSwitch =  new AtomicBoolean(false);

	private Object mutex = new Object();



	private APICallMetrics apiCallMetrics;
	private DataPushClient dataPushClient;
    private Scheduler scheduler;



	public void setApiCallMetrics(APICallMetrics apiCallMetrics) {
		this.apiCallMetrics = apiCallMetrics;
	}

	public void setDataPushClient(DataPushClient dataPushClient) {
		this.dataPushClient = dataPushClient;
	}

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * 做api调用的aggregate .
     * @param data
     */
    protected  void doRealWork(Data data){
        if(dataPushSwitch.get()){            // 如果 dataPushSwitch开关打开，表示正在做数据推送任务。
            synchronized (mutex){
                try {
                    mutex.wait();             //等mutex.notifyAll() 。
                } catch (InterruptedException e) {
                    logger.error("ApiCallHandler doRealWork {} -> mutex.wait() catch InterruptedException  ", data, e );
                }
            }
        }

        APICallProbeData apiCallProbeData = (APICallProbeData) data;

        String api =  apiCallProbeData.getApi();
        AtomicInteger atomicInteger = api2CallCountMap.get(api) ;
        if(atomicInteger != null){
            //简单的做原子变量的increment;
            atomicInteger.incrementAndGet() ;
        }
    }


	@Override
	public void afterPropertiesSet() throws Exception {
        //配置好需要进行访问计数的api  .
		List<APICallMetric> apiCallMetricList = apiCallMetrics.getApiCallMetricList();
		if(apiCallMetricList != null && apiCallMetricList.size() > 0) {
			for(APICallMetric apiCallMetric: apiCallMetricList){
				this.api2CallCountMap.put(apiCallMetric.getApi(),new AtomicInteger(0));
			}
		}

	
	}

    /**
     * 这里注意需要进行 深度拷贝 ,否则不同的pipeline使用的是同样的handler ;
     * @return
     */
	public Handler copySelf(){

        //api2CallCountMap 拷贝一个新的。
		ApiCallHandler apiCallHandler = new ApiCallHandler();
		apiCallHandler.api2CallCountMap = new HashMap<String, AtomicInteger>();
		Iterator<Map.Entry<String,AtomicInteger>> iterator = api2CallCountMap.entrySet().iterator();
		Map.Entry<String,AtomicInteger> entry = null;
		while(iterator.hasNext()){
			entry = iterator.next();
			apiCallHandler.api2CallCountMap.put(entry.getKey(), new AtomicInteger(entry.getValue().get()));
		}
		
		apiCallHandler.dataPushClient = this.dataPushClient;  //推送client 用一个。
        apiCallHandler.scheduler = this.scheduler; //调度器用一个 。
		return apiCallHandler;
	}

	public Map<String, Integer> getStatisticData() {
		dataPushSwitch.set(true);
		Map<String ,Integer> apiCallResult = null;
		try{

			apiCallResult = this.copyNewAndClearOldMap();

		} finally{
			dataPushSwitch.set(false);//这句很重要 ，否则线程就被阻塞了。 
			synchronized (mutex){
				mutex.notifyAll();
			}
		}
		return apiCallResult;

	}


	private final Map<String,Integer> copyNewAndClearOldMap(){
		Map<String,Integer> newMap = new HashMap<String,Integer>(api2CallCountMap.size());
		Iterator<Map.Entry<String,AtomicInteger>> iterator = api2CallCountMap.entrySet().iterator();
		Map.Entry<String,AtomicInteger> entry  = null ;
		while(iterator.hasNext()){
			entry = iterator.next();
			newMap.put(entry.getKey(), new Integer(entry.getValue().get()));  
			entry.getValue().set(0);//旧的值清零

		}
		return newMap ;

	}


	public void setDataFeature(DataFeature dataFeature){
		this.dataFeature = dataFeature; 

	}


    public void doOtherInitialWork(){
        //如果设置了数据推送属性，则启动定时任务。
        if(this.dataFeature != null && this.dataFeature instanceof DataPushFeature){

            ProbeConfig probeConfig =
                    TransferUtil.getProbeConfig((DataPushFeature)dataFeature);

            try {
                this.scheduler.startJob(probeConfig, new Callable() {
                    @Override
                    public Object call() throws Exception {
                        dataPushClient.push(getStatisticData());
                        return true;
                    }
                }, "ApiCallHandler_push_data_job_" + index.getAndIncrement());
            } catch (SchedulerException e) {

                logger.error("ApiCallHandler start job error {}", "", e);
            }

        }
    }

	






}

