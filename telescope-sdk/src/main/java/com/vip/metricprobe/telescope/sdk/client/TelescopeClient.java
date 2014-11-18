package com.vip.metricprobe.telescope.sdk.client;

import com.alibaba.fastjson.JSONObject;
import com.vip.metricprobe.telescope.sdk.config.TelescopeClientConfig;
import com.vip.metricprobe.telescope.sdk.domain.PushData;
import com.vip.metricprobe.telescope.sdk.exeception.TelescopeException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 *  TODO 在数据推送的时候 ，超时怎么办。 另外推送错误是否需要重试 。
 * Created by dongqingswt on 14-11-6.
 */
public class TelescopeClient {

    private static final  Logger logger = LoggerFactory.getLogger("telescopeclientlogger");

    private CloseableHttpClient httpClient  = HttpClients.createDefault();

    private TelescopeClientConfig telescopeClientConfig;


    public TelescopeClient(){

    }

    public void setTelescopeClientConfig(TelescopeClientConfig telescopeClientConfig) {
        this.telescopeClientConfig = telescopeClientConfig;
    }

    public void push(List<PushData> pushDatas){

        for(PushData pushData: pushDatas){
            //值为0 的计数不推送。
            if(pushData.getValue() == 0){
                continue;
            }

            try {
                this.push(pushData);
            } catch (TelescopeException e) {
                logger.error("TelescopeClient.push(List<PushData> pushDatas) catch  TelescopeException {}", "" , e);

            } catch (UnsupportedEncodingException e) {
                logger.error("TelescopeClient.push(List<PushData> pushDatas) catch  UnsupportedEncodingException {}", "" , e);
            }

        }
    }



    private boolean push(PushData pushData) throws TelescopeException, UnsupportedEncodingException {

        HttpPost httpPost =  new HttpPost(this.telescopeClientConfig.getPushUrl());

        String returnJSON =  JSONObject.toJSONString(pushData,true);



        StringEntity s = new StringEntity(returnJSON, "UTF-8");
        s.setContentEncoding("UTF-8");
        s.setContentType("application/json");
        httpPost.setEntity(s);

        boolean isPushCorrect = false;
        try {
            CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpPost);
            try{
                HttpEntity entity = closeableHttpResponse.getEntity();


                String responseEntity =  EntityUtils.toString(entity) ;
                JSONObject obj = (JSONObject) JSONObject.parse(responseEntity);

                Integer code = (Integer) obj.get("code");

                if(code != null && code.equals(1000)){
                    isPushCorrect = true;
                } else{
                    StringBuilder err = new StringBuilder() ;
                    err.append("The telescope pushurl [")
                            .append(this.telescopeClientConfig.getPushUrl())
                            .append("] push data failure , get response [ ")
                            .append(responseEntity)
                            .append("] ,push data is : [")
                            .append(pushData)
                            .append("] ");
                    throw new TelescopeException(err.toString());
                }


            } finally{
                closeableHttpResponse.close();
            }

        } catch (IOException e) {
            logger.error("TelescopeClient.push(PushData pushData)  catch  IOException {}", "" , e);
        }    finally{
            try {
                httpClient.close() ;
            } catch (IOException e) {
                logger.error("TelescopeClient.push(PushData pushData) ->  httpClient.close() catch  IOException {}", "" , e);
            }
        }

        if(isPushCorrect){
             logger.info("TelescopeClient.push(PushData pushData) {} success ", returnJSON);
        }
        return isPushCorrect;
    }








    /**
     * String url = "http://192.168.46.58:8090/DataAcceptor/data/pushDataTest.do";
     HttpPost httpPost =  new HttpPost(url);

     PushData pushData= new PushData();
     pushData.setId("aWTmr43lqbRhcHDmtYvor5XmjqXlhaXmjIfmoIfmtojmga/lubPlj7BfTU1Q");
     pushData.setKey("消息平台_MMP_a2V55q+N5am0YXBw5rWL6K+V5o6l5YWl5oyH5qCH5raI5oGv5bmz5Y+wX01NUA==");
     pushData.setTimestamp(Calendar.getInstance().getTimeInMillis());
     pushData.setValue(1d) ;

     IndexEntry indexEntry = new IndexEntry() ;
     indexEntry.setDimension("地域", "华南");
     indexEntry.setDimension("主机", "主机IP ");
     indexEntry.setValue(2d) ;

     List<IndexEntry> indexEntryList = new ArrayList<IndexEntry>(1);
     indexEntryList.add(indexEntry);
     pushData.setIndex(indexEntryList);

     String returnJSON =  JSONObject.toJSONString(pushData,true);
     System.out.println("pushdata json :" +returnJSON) ;


     StringEntity s = new StringEntity(returnJSON, "UTF-8");
     s.setContentEncoding("UTF-8");
     s.setContentType("application/json");
     httpPost.setEntity(s);
     System.out.println("entity:"+ s );

     try {
     CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpPost);
     try{
     HttpEntity entity = closeableHttpResponse.getEntity();
     System.out.println("repsonse content: " + EntityUtils.toString(entity)) ;

     } finally{
     closeableHttpResponse.close();
     }

     } catch (IOException e) {
     e.printStackTrace();
     }    finally{
     try {
     httpClient.close() ;
     } catch (IOException e) {
     e.printStackTrace();
     }
     }

     return false;
     */



}
