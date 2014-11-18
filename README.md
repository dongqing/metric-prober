metric-prober
=============


使用方法： 
因为还没有用公司的maven仓库，所以先暂时用下面的临时解决办法。

1、
下载git 源码。
git clone git@github.com:dongqing/metric-prober.git


2、
打包到本地maven仓库， 先忽略单元测试： 有些测试用例还没有写完： 
mvn clean install -Dmaven.test.skip=true 

3、添加maven依赖： 
com.vip.metricprobe:metric-prober-extend:1.0-SNAPSHOT
 
  
4、把metric-prober-extend/src/main/resources下的配置文件拷贝到应用的resources下面
metric-prober-core/src/main/resources/下的配置文件拷贝到应用的resources下面
两个logback.xml的文件要合并到一起。


5、在宿主应用中启动spring容器，获取id="metricProberClient"的bean .
metricProberClient 这个bean的使用可以参考MetricProberClientTest类。
这个类主要用来处理宿主app的技术指标统计，比如:线程池的大小、队列的长度，统计的频率由用户设定,统计指标的获取由回调函数处理,统计结果的处理由监听器处理。 



6、获取id="apiCallListener"的bean. 
这个bean 主要处理api 调用到telescope系统的接入，实现了缓冲、aggregate、到telescope系统的定时推送。
使用方法可以参考ApiCallListenerTest这个类。
api指标的配置文件在telescope-sdk.xml中。



      
