package com.vip.metricprobe.telescope.sdk.client;

import com.vip.metricprobe.telescope.sdk.BaseTestCase;
import com.vip.metricprobe.telescope.sdk.domain.PushData;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongqingswt on 14-11-17.
 */
public class TelescopeClientTest extends BaseTestCase{

    @Resource
    TelescopeClient telescopeClient;

    /**
     * <property name="id" value="aWTmr43lqbRhcHDmtYvor5XmjqXlhaXmjIfmoIfmtojmga/lubPlj7BfTU1Q"/>
     <property name="key" value="消息平台_MMP_a2V55q+N5am0YXBw5rWL6K+V5o6l5YWl5oyH5qCH5raI5oGv5bmz5Y+wX01NUA=="/>
     <property name="api"  value="className.methodName(argtype...)"/>

     */

    @Test
    public void testPush(){
        List<PushData> pushDataList = new ArrayList<PushData>(1);
        PushData pushData = new PushData();
        pushData.setId("aWTmr43lqbRhcHDmtYvor5XmjqXlhaXmjIfmoIfmtojmga/lubPlj7BfTU1Q");
        pushData.setKey("消息平台_MMP_a2V55q+N5am0YXBw5rWL6K+V5o6l5YWl5oyH5qCH5raI5oGv5bmz5Y+wX01NUA==");
        pushData.setValue(1d);
        pushData.setTimestamp(System.currentTimeMillis());

        pushDataList.add(pushData);

        telescopeClient.push(pushDataList);
    }


}
