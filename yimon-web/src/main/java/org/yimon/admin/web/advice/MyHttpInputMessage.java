package org.yimon.admin.web.advice;

import org.yimon.admin.core.constant.DatePattern;
import org.yimon.admin.core.util.DateFormatUtils;
import org.yimon.admin.core.util.SnowFlakeUtils;
import org.yimon.admin.util.GsonHolder;
import org.yimon.admin.util.constant.GlobalConstants;
import org.yimon.admin.web.handler.HttpServletHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yeming.gao
 * @Description: 请求消息序列化处理
 * @date 2020/7/29 10:02
 */
@Slf4j
public class MyHttpInputMessage implements HttpInputMessage {

    private final HttpHeaders headers;
    private final InputStream body;

    MyHttpInputMessage(HttpInputMessage inputMessage, HttpServletRequest httpServletRequest) throws IOException {
        Map<String, String> requestMap = new HashMap<>(6);
        String traceId = SnowFlakeUtils.generateTraceId("YIMON_ADMIN_");
        ThreadContext.put(GlobalConstants.TRACE_ID, traceId);
        log.debug("current request generate traceId:{}", traceId);

        requestMap.put(GlobalConstants.TRACE_ID, traceId);
        requestMap.put(GlobalConstants.REQUEST_TIME, DateFormatUtils.format(new Date(), DatePattern.NORM_DATETIME_PATTERN));
        requestMap.put(GlobalConstants.REQUEST_DEVICE, HttpServletHandler.getDevice(httpServletRequest));
        requestMap.put(GlobalConstants.TOKEN, HttpServletHandler.getToken(httpServletRequest));
        requestMap.put(GlobalConstants.DATA, convertToString(inputMessage.getBody()));


        this.headers = inputMessage.getHeaders();
        this.body = new ByteArrayInputStream(GsonHolder.toJson(requestMap).getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public InputStream getBody() {
        return body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }

    private String convertToString(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        reader.close();
        return stringBuilder.toString();
    }

}
