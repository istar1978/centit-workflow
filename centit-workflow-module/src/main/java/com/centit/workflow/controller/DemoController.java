package com.centit.workflow.controller;


import com.centit.framework.common.JsonResultUtils;
import com.centit.framework.common.ResponseMapData;
import com.centit.framework.core.controller.BaseController;
import com.centit.workflow.po.FlowInstance;
import com.centit.workflow.service.FlowEngine;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by chen_rj on 2017/7/10.
 */
@Controller
@RequestMapping("/flow/demo")
public class DemoController extends BaseController {
    @Resource
    private FlowEngine flowEng;

    private ResponseMapData resData = new ResponseMapData();

    //新建流程
    @RequestMapping(value="/createFlowInstance")
    public void createFlowInstance(FlowInstance param,HttpServletRequest request, HttpServletResponse response){
        FlowInstance flowInstance = flowEng.createInstance(param.getFlowCode(),param.getFlowOptName(),param.getFlowOptTag(),param.getUserCode(),param.getUnitCode());
        resData.addResponseData(OBJLIST,flowInstance);
        JsonResultUtils.writeResponseDataAsJson(resData,response);
    }
    //提交节点
    @RequestMapping(value="/submitOpt/{nodeInstId}")
    public void submitOpt(@PathVariable String nodeInstId, HttpServletRequest request, HttpServletResponse response){
        flowEng.submitOpt(Long.parseLong(nodeInstId),"u0000000",null,null,null);
        JsonResultUtils.writeResponseDataAsJson(resData,response);
    }
}

