package com.yimon.admin.web.controller.vo;

import com.google.gson.annotations.SerializedName;
import com.yimon.admin.core.pojo.ABasePojo;

/**
 * @author: ym.gao
 * @description: 通用请求
 * @date: 2024/4/29 16:14
 */
public class RequestVO extends ABasePojo {

    /**
     * 全局流水号，由后台生成
     */
    @SerializedName("trace_id")
    private String traceId;
    /**
     * 请求时间 yyyy-MM-dd HH:mm:ss
     */
    @SerializedName("request_time")
    private String requestTime;
    /**
     * 请求设备信息
     * {
     * "deviceType": "设备类型"
     * "deviceTerminalNo": "设备编号",
     * "deviceAddress": "{\"IP\":\"127.0.0.1\",\"MAC\":\"${MAC}\",\"IMEI\":\"${IMEI}\",\"IMSI\":\"${IMSI}\",\"ICCID\":\"${ICCID}\",\"WIFIMAC\":\"${WIFIMAC}\",\"GPS\":\"${GPS}\"}"
     * }
     */
    @SerializedName("request_device")
    private String requestDevice;
    /**
     * token信息
     */
    @SerializedName("token")
    private String token;
    /**
     * 请求数据信息 json格式
     */
    @SerializedName("data")
    private String data;

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getRequestDevice() {
        return requestDevice;
    }

    public void setRequestDevice(String requestDevice) {
        this.requestDevice = requestDevice;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
