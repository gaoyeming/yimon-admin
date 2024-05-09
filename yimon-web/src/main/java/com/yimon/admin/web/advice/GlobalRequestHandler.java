package com.yimon.admin.web.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author yeming.gao
 * @Description: @RequestBody请求参数-全局请求体处理
 * @date 2020/7/29 10:02
 */
@Slf4j
@RestControllerAdvice(basePackages = {"com.yimon.admin.web.controller"})
public class GlobalRequestHandler implements RequestBodyAdvice {


    @Resource
    private HttpServletRequest httpServletRequest;

    /**
     * 此处如果返回false , 则不执行当前Advice的业务
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * 无请求时的处理
     */
    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    /**
     * 读取参数前执行 在此做些编码/解密/封装参数为对象的操作
     */
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
//        String ipAddress = HttpServletHandler.getIpAddress(httpServletRequest);
        return new MyHttpInputMessage(inputMessage, null);
    }

    /**
     * 读取参数后执行
     */
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.info("execute api:{}, request:{}", httpServletRequest.getServletPath(), body);
        return body;
    }
}
