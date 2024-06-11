package org.yimon.admin.web.advice;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.yimon.admin.core.constant.DatePattern;
import org.yimon.admin.core.util.DateFormatUtils;
import org.yimon.admin.util.constant.GlobalConstants;
import org.yimon.admin.util.constant.ResultCode;
import org.yimon.admin.web.controller.vo.ResultVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * @author yeming.gao
 * @Description: @ResponseBody请求参数-全局响应体处理
 * @date 2020/7/29 10:02
 */
@Slf4j
@RestControllerAdvice(basePackages = {"org.yimon.admin.web.controller"})
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }


    /**
     * 通用返回内容处理，只能在程序正常响应时进行处理，异常请在GlobalExceptionHandler进行处理
     *
     * @return Object
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        ResultVO resultVO = ResultVO.fillRespCode(ResultCode.EXCEPTION_NOT_MATCH);
        if (body == null) {
            resultVO = ResultVO.fillRespCode(ResultCode.SUCCEED_NON_DATA);
        }
        if (body instanceof ResultVO) {
            resultVO = (ResultVO) body;
        }
        resultVO.setTraceId(ThreadContext.get(GlobalConstants.TRACE_ID));
        resultVO.setResponseTime(DateFormatUtils.format(new Date(), DatePattern.NORM_DATETIME_PATTERN));
        log.info("execute api:{}, response:{}", httpServletRequest.getServletPath(), resultVO);
        ThreadContext.remove(GlobalConstants.TRACE_ID);
        return resultVO;
    }

}
