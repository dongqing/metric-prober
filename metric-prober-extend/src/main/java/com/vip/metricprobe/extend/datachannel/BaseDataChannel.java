package com.vip.metricprobe.extend.datachannel;


import com.vip.metricprobe.core.CleanableResource;
import com.vip.metricprobe.extend.chain.HandlerChain;
import com.vip.metricprobe.extend.domain.Data;
import com.vip.metricprobe.extend.domain.DataFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 数据通道的默认实现 ...
 * TODO　pipeline 数据队列满了以后  ，怎么持久化存储的问题。
 * Created by dongqingswt on 14-11-8.
 */
public  class BaseDataChannel implements  DataChannel, CleanableResource {

    private static final Logger logger = LoggerFactory.getLogger("BaseDataChannelLogger");

	private Pipeline pipeline;



	public BaseDataChannel(HandlerChain handlerChain ,DataFeatureHolder dataFeatureHolder){
        //启动一个Pipeline的数组，每一个Pipeline对不同的数据特性进行处理。
		this.pipeline = new PipelineGroup(dataFeatureHolder.getDataFeatures(), handlerChain);
	}
	
	public BaseDataChannel(){
		
	}

	@Override
	public void pushData(Data data) {

		this.pipeline.receiveData(data);

	}

    @Override
    public void cleanResource() {

        this.pipeline.shutdown();
    }


    /**
	 * 数据管道，数据通道接收到数据以后， 丢到数据管道中去。
	 */
	public interface Pipeline{
		/**
		 * 接收数据
		 * @param data
		 */
		public void receiveData(Data data);

		/**
		 * 获取管道要处理的数据特性。
		 * @return
		 */
		public DataFeature getDataFeature();


        /**
         * 关闭管道 。
         */
        public void shutdown();


	}

	/**
	 *  默认的数据管道。
	 */
	class DefaultPipeline implements  Pipeline{

		/**
		 * 存储数据的队列。
		 */
		private BlockingQueue<Data> dataQueue = new ArrayBlockingQueue<Data>(50);

		private DataHandlerTask dataHandlerTask;

		private final DataFeature dataFeature;


        /**
         * 初始化一个数据管道，默认会启动一个Task .
         * @param handlerChain
         * @param dataFeature
         */
		DefaultPipeline(HandlerChain handlerChain ,DataFeature dataFeature){
			this.dataHandlerTask = new DataHandlerTask(dataQueue,handlerChain );
			this.dataHandlerTask.startTask();
			this.dataFeature = dataFeature; 
		}

		@Override
		public void receiveData(Data data) {
			this.dataQueue.offer(data) ;
		}

		@Override
		public DataFeature getDataFeature() {
			return this.dataFeature;
		}

        @Override
        public void shutdown() {
            dataHandlerTask.shutdown();
        }
    }

	/**
	 * 用于从PipelineGroup中选择一个Pipeline.
	 */
	interface PipelineSelector{

        /**
         * 根据数据特性从管道中选择一个管道。
         * @param pipelines
         * @param data
         * @return
         */
		Pipeline  selector(List<Pipeline> pipelines, Data data);
		
		

	}

	/**
	 * 多个管道组成的一个管道组 。
	 */
	class PipelineGroup implements Pipeline{

		private final List<Pipeline> pipelineList ;

		private final PipelineSelector pipelineSelector;

		private final AtomicInteger pipelineCount ;



		public PipelineGroup(Set<DataFeature> dataFeatures,HandlerChain handlerChain){
			assert(dataFeatures != null && dataFeatures.size() > 0 );
            //根据数据特性个数，初始化多个pipeline;
			this.pipelineList = new ArrayList<Pipeline>(dataFeatures.size());
			HandlerChain handlerChain2 = null; 
			for(DataFeature dataFeature:dataFeatures){
				//注意这里要做handlerChain的copy .也就是每一个pipeline有一个自己的处理器链 。
				handlerChain2 = handlerChain.copySelf();
				handlerChain2.setDataFeature(dataFeature);
				handlerChain2.doOtherInitialWork(); 
				this.pipelineList.add(new DefaultPipeline(handlerChain2, dataFeature));
			}
			//基于数据特性的Pipeline选择器 。
			this.pipelineSelector = new DataFeatureBasedSelector(this.pipelineList);
            int  pipelineCount = this.pipelineList.size();
            this.pipelineCount = new AtomicInteger(pipelineCount);
		}




		/**
		 * 新增管道
		 * @param pipeline
		 */
		public void addPipeline(Pipeline pipeline){
			this.pipelineList.add(pipeline);
			pipelineCount.incrementAndGet();
		}

		/**
		 * 移除管道
		 * @param pipeline
		 */
		public void removePipeline(Pipeline pipeline){
			this.pipelineList.remove(pipeline);
			pipelineCount.decrementAndGet();
		}

		/**
		 * 获取管道的个数
		 * @return
		 */
		public int getPipelineCount(){
			return this.pipelineCount.get();
		}

		/**
		 * 取某一个位置的pipeline;
		 * @param index
		 * @return
		 */
		public Pipeline get(int index){
			return this.pipelineList.get(index);
		}

		@Override
		public void receiveData(Data data) {

            //使用pipeline selector选择Pipeline ;
			Pipeline pipeline = this.pipelineSelector.selector(this.pipelineList,data);
			pipeline.receiveData(data);
		}




		@Override
		public DataFeature getDataFeature() {
			return null;
		}

        @Override
        public void shutdown() {
            for(Pipeline pipeline: pipelineList){
                pipeline.shutdown();
            }
        }
    }

	/**
	 * 轮询的方式从PipelineGroup中获取Pipeline,和Pipeline需要处理的数据无关。
	 */
	class RoundRobinPipelineSelector implements PipelineSelector{

		private AtomicInteger index = new AtomicInteger(0);

		@Override
		public Pipeline selector(List<Pipeline> pipelines,Data data) { 

			int pipelineCount = pipelines.size(); 
			int result  ;
			synchronized (this){
				result = index.get() ;
				if(index.incrementAndGet() >= pipelineCount){
					index.set(0);
				}
			}
			return pipelines.get(result);
		}
	}

    /**
     * 基于数据特性的选择器 ，它会选择具有相同DataFeature的Pipeline .
     */
	class DataFeatureBasedSelector implements PipelineSelector{

		private Map<DataFeature ,Pipeline> dataFeature2Pipeline = new HashMap<DataFeature,Pipeline>() ;
		
		public DataFeatureBasedSelector(List<Pipeline> pipelines){
			for(Pipeline pipeline:pipelines){
				dataFeature2Pipeline.put(pipeline.getDataFeature(), pipeline); 
			}
		}

		public  Pipeline  selector(List<Pipeline> pipelines ,Data data){
			
			Pipeline pipeline = this.dataFeature2Pipeline.get(data.getDataFeature()); 
			return pipeline ;
			
		}

	}


	/**
	 * 这个任务的作用是从Pipeline的Queue中提取Data 到FilterChain中进行计算。
	 */
	class DataHandlerTask{

		private BlockingQueue<Data> dataQueue ;

		private HandlerChain handlerChain;

        private Thread thread;

		DataHandlerTask(final BlockingQueue<Data> dataQueue, final HandlerChain handlerChain){
			this.dataQueue = dataQueue;
			this.handlerChain = handlerChain ; 
			this.thread = new Thread( new Runnable(){
                @Override
                public void run() {
                    Data data = null;
                    for(;;){
                        try {
                            data = (Data) dataQueue.take();
                            handlerChain.handle(data);
                        } catch (InterruptedException e) {
                            logger.error("DataHandlerTask handle data {} error",data,e );
                        }
                    }
                }
            });

			
		}


        /**
         * 启动一个task, 这个task从关联的队列中获取数据并使用处理器链进行处理。
         */
		public void startTask(){
			this.thread.start();
		}

        public void shutdown() {

               // do no op ;
        }
    }







}
