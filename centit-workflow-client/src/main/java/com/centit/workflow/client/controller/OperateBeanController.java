package com.centit.workflow.client.controller;

import com.centit.framework.common.ResponseData;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by chen_rj on 2017/8/3.
 */

public abstract class OperateBeanController {

    abstract ResponseData doCallBack(Map<String,Object> paramMap );

    abstract ResponseData getFlowVariable();


    @RequestMapping(value = "/callBackFunc", method= RequestMethod.POST)
    @ResponseBody
    public ResponseData callBackFunc(@RequestBody Map<String,Object> paramMap ){
        return  doCallBack(paramMap);
    }

    /**
     * 获取流程变量
     * @return
     */
    @RequestMapping(value = "/getFlowVariable", method= RequestMethod.GET)
    @ResponseBody
    public ResponseData callFlowVariable(){
        return  getFlowVariable();
    }
}
