package com.yimon.admin.web.controller.vo;

import com.google.gson.annotations.SerializedName;
import com.yimon.admin.core.exception.BusinessException;
import com.yimon.admin.core.exception.InvokeException;
import com.yimon.admin.core.exception.RejectedException;
import com.yimon.admin.core.exception.ValidateException;
import com.yimon.admin.core.pojo.ABasePojo;
import com.yimon.admin.core.result.ReturnCode;
import com.yimon.admin.util.GsonHolder;

import java.util.Map;

/**
 * @author: ym.gao
 * @description: 通用结果响应
 * @date: 2024/4/29 16:27
 */
public class ResultVO extends ABasePojo {

    /**
     * 全局流水号，由后台生成
     */
    @SerializedName("trace_id")
    private String traceId;
    /**
     * 响应时间 yyyy-MM-dd HH:mm:ss
     */
    @SerializedName("response_time")
    private String responseTime;
    /**
     * 响应代码
     */
    @SerializedName("resp_code")
    private Integer respCode;
    /**
     * 响应代码描述
     */
    @SerializedName("resp_desc")
    private String respDesc;
    /**
     * 响应数据信息 json格式
     */
    @SerializedName("data")
    private String data;

    public static ResultVO isSucceed(Map<String, Object> data) {
        ResultVO resultVO = new ResultVO();
        resultVO.setRespCode(ReturnCode.SUCCEED.code());
        resultVO.setRespDesc(ReturnCode.SUCCEED.msg());
        resultVO.setData(GsonHolder.toJsonNormDate(data));
        return resultVO;
    }

    public static ResultVO fillRespCode(ReturnCode returnCode) {
        ResultVO resultVO = new ResultVO();
        resultVO.setRespCode(returnCode.code());
        resultVO.setRespDesc(returnCode.msg());
        return resultVO;
    }

    public static ResultVO fillRespCode(BusinessException e) {
        ResultVO resultVO = new ResultVO();
        resultVO.setRespCode(e.getCode());
        resultVO.setRespDesc(e.getMessage());
        return resultVO;
    }

    public static ResultVO fillRespCode(InvokeException e) {
        ResultVO resultVO = new ResultVO();
        resultVO.setRespCode(e.getCode());
        resultVO.setRespDesc(e.getMessage());
        return resultVO;
    }

    public static ResultVO fillRespCode(RejectedException e) {
        ResultVO resultVO = new ResultVO();
        resultVO.setRespCode(e.getCode());
        resultVO.setRespDesc(e.getMessage());
        return resultVO;
    }

    public static ResultVO fillRespCode(ValidateException e) {
        ResultVO resultVO = new ResultVO();
        resultVO.setRespCode(e.getCode());
        resultVO.setRespDesc(e.getMessage());
        return resultVO;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public Integer getRespCode() {
        return respCode;
    }

    public void setRespCode(Integer respCode) {
        this.respCode = respCode;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
