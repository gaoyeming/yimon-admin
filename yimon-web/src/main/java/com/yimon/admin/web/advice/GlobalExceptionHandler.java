package com.yimon.admin.web.advice;

import com.yimon.admin.core.constant.DatePattern;
import com.yimon.admin.core.exception.BusinessException;
import com.yimon.admin.core.exception.InvokeException;
import com.yimon.admin.core.exception.RejectedException;
import com.yimon.admin.core.exception.ValidateException;
import com.yimon.admin.core.result.ReturnCode;
import com.yimon.admin.core.util.DateFormatUtils;
import com.yimon.admin.util.constant.GlobalConstants;
import com.yimon.admin.web.controller.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * @author yeming.gao
 * @Description: 全局异常处理类。
 * @date 2020/7/29 10:02
 */
@Slf4j
@RestControllerAdvice(basePackages = {"com.yimon.admin.web.controller"})
public class GlobalExceptionHandler {

    /**
     * 业务异常
     *
     * @param e 异常
     * @return 处理结果
     */
    @ExceptionHandler(BusinessException.class)
    public ResultVO handlerBusinessException(HttpServletRequest httpServletRequest, BusinessException e) {
        log.warn("happen BusinessException:", e);
        ResultVO resultVO = ResultVO.fillRespCode(e);
        resultVO.setTraceId(ThreadContext.get(GlobalConstants.TRACE_ID));
        resultVO.setResponseTime(DateFormatUtils.format(new Date(), DatePattern.NORM_DATETIME_PATTERN));
        log.info("execute api:{}, response:{}", httpServletRequest.getServletPath(), resultVO);
        return resultVO;
    }

    /**
     * 调用异常
     *
     * @param e 异常
     * @return 处理结果
     */
    @ExceptionHandler(InvokeException.class)
    public ResultVO handlerInvokeException(HttpServletRequest httpServletRequest, InvokeException e) {
        log.warn("happen InvokeException:", e);
        ResultVO resultVO = ResultVO.fillRespCode(e);
        resultVO.setTraceId(ThreadContext.get(GlobalConstants.TRACE_ID));
        resultVO.setResponseTime(DateFormatUtils.format(new Date(), DatePattern.NORM_DATETIME_PATTERN));
        log.info("execute api:{}, response:{}", httpServletRequest.getServletPath(), resultVO);
        return resultVO;
    }

    /**
     * 拒绝异常
     *
     * @param e 异常
     * @return 处理结果
     */
    @ExceptionHandler(RejectedException.class)
    public ResultVO handlerRejectedException(HttpServletRequest httpServletRequest, RejectedException e) {
        log.warn("happen RejectedException:", e);
        ResultVO resultVO = ResultVO.fillRespCode(e);
        resultVO.setTraceId(ThreadContext.get(GlobalConstants.TRACE_ID));
        resultVO.setResponseTime(DateFormatUtils.format(new Date(), DatePattern.NORM_DATETIME_PATTERN));
        log.info("execute api:{}, response:{}", httpServletRequest.getServletPath(), resultVO);
        return resultVO;
    }

    /**
     * 参数异常
     *
     * @param e 异常
     * @return 处理结果
     */
    @ExceptionHandler(ValidateException.class)
    public ResultVO handlerValidateException(HttpServletRequest httpServletRequest, ValidateException e) {
        log.warn("happen ValidateException:", e);
        ResultVO resultVO = ResultVO.fillRespCode(e);
        resultVO.setTraceId(ThreadContext.get(GlobalConstants.TRACE_ID));
        resultVO.setResponseTime(DateFormatUtils.format(new Date(), DatePattern.NORM_DATETIME_PATTERN));
        log.info("execute api:{}, response:{}", httpServletRequest.getServletPath(), resultVO);
        return resultVO;
    }

    /**
     * 其他异常
     *
     * @param e 异常
     * @return 处理结果
     */
    @ExceptionHandler(Exception.class)
    public ResultVO handlerSysException(HttpServletRequest httpServletRequest, Exception e) {
        log.warn("happen Exception:", e);
        ResultVO resultVO = ResultVO.fillRespCode(ReturnCode.EXCEPTION);
        resultVO.setTraceId(ThreadContext.get(GlobalConstants.TRACE_ID));
        resultVO.setResponseTime(DateFormatUtils.format(new Date(), DatePattern.NORM_DATETIME_PATTERN));
        log.info("execute api:{}, response:{}", httpServletRequest.getServletPath(), resultVO);
        return resultVO;
    }
}