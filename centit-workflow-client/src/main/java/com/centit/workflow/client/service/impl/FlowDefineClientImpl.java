package com.centit.workflow.client.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.centit.framework.appclient.AppSession;
import com.centit.support.network.HttpExecutor;
import com.centit.support.network.HttpExecutorContext;
import com.centit.workflow.client.service.FlowDefineClient;
import com.centit.workflow.po.FlowInfo;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chen_rj on 2018-5-2.
 */
@Service
public class FlowDefineClientImpl implements FlowDefineClient {
    @Value("${workflow.server}")
    private String workFlowServerUrl;

    public FlowDefineClientImpl() {

    }
    private AppSession appSession;

    @Override
    public CloseableHttpClient allocHttpClient() throws Exception {
        return appSession.allocHttpClient();
    }

    @Override
    public void releaseHttpClient(CloseableHttpClient httpClient) {
        appSession.releaseHttpClient(httpClient);
    }

    @Override
    public void setWorkFlowServerUrl(String workFlowServerUrl) {
        this.workFlowServerUrl = workFlowServerUrl;
    }

    public void makeAppSession() {
        appSession = new AppSession(workFlowServerUrl,false,null,null);
    }


    @PostConstruct
    public void init(){
        //this.setWorkFlowServerUrl(workFlowServerUrl);
        makeAppSession();
    }

    @Override
    public List<FlowInfo> list() {
        Map<String,Object> paramMap = new HashMap<>();
        String result = null;
        CloseableHttpClient httpClient = null;
        List<FlowInfo> flowInfos = null;
        try {
            httpClient = appSession.allocHttpClient();
            appSession.checkAccessToken(httpClient);
            result =  HttpExecutor.simpleGet(HttpExecutorContext.create(httpClient),
                appSession.completeQueryUrl("/flow/define"),paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            appSession.releaseHttpClient(httpClient);
        }
        try {
            JSONObject jsonObject = JSONObject.parseObject(result);
            String dataStr = jsonObject.getJSONObject("data").get("objList").toString();
            flowInfos= JSONObject.parseArray(dataStr,FlowInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flowInfos;
    }
}
