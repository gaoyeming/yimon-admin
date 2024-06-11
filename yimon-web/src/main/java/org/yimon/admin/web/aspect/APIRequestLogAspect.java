package org.yimon.admin.web.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.yimon.admin.biz.CrudBiz;
import org.yimon.admin.core.util.StringUtils;
import org.yimon.admin.util.GsonHolder;
import org.yimon.admin.util.constant.GlobalConstants;
import org.yimon.admin.web.annotation.APIRequestLog;
import org.yimon.admin.web.handler.HttpServletHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yeming.gao
 * @Description: @SysOperationLog注解的AOP实现
 * @date 2019/11/26 9:48
 */
@Slf4j
@Aspect
@Component
public class APIRequestLogAspect {
    /**
     * 操作类型
     */
    private static final String OPERA_TYPE = "YIMON_ADMIN";
    /**
     * 最大返回值长度
     */
    private static final int MAX_LENGTH = 1024;

    @Resource
    private HttpServletRequest request;

    @Resource
    private CrudBiz crudBiz;


    /**
     * 环绕通知
     *
     * @param proceedingJoinPoint 切点
     * @return Object
     * @throws Throwable 异常
     */
    @Around("@annotation(org.yimon.admin.web.annotation.APIRequestLog)")
    public Object apiLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        APIRequestLog apiRequestRecord = method.getAnnotation(APIRequestLog.class);

        Map<String, Object> operaLog = new HashMap<>();
        operaLog.put("traceId", ThreadContext.get(GlobalConstants.TRACE_ID));//追踪流水号
        operaLog.put("operaType", OPERA_TYPE);//操作类型
        operaLog.put("operaUser", HttpServletHandler.getUser(request));//操作用户
        operaLog.put("operaTitle", apiRequestRecord.value());//操作标题
        operaLog.put("operaDevice", HttpServletHandler.getDevice(request));//请求设备信息
        operaLog.put("operaUrl", request.getServletPath());//操作入口
        //操作参数
        String operaParam = proceedingJoinPoint.getArgs() == null ? StringUtils.EMPTY : GsonHolder.toJsonNormDate(proceedingJoinPoint.getArgs());
        //操作结果
        String operaResult = StringUtils.EMPTY;
        //操作状态（1正常 0异常）
        int status = 1;
        try {
            //执行目标方法获取返回对象
            Object response = proceedingJoinPoint.proceed();
            //操作结果
            operaResult = response == null ? StringUtils.EMPTY : GsonHolder.toJsonNormDate(response);
            return response;
        } catch (Throwable e) {
            status = 0;//操作发生异常
            operaResult = e.getMessage();
            throw e;
        } finally {
            //长度判断
            if (operaParam.length() > MAX_LENGTH) {
                operaParam = operaParam.substring(0, MAX_LENGTH);
            }
            if (operaResult.length() > MAX_LENGTH) {
                operaResult = operaResult.substring(0, MAX_LENGTH);
            }

            operaLog.put("operaParam", operaParam);//操作参数
            operaLog.put("operaResult", operaResult);//操作结果
            operaLog.put("status", status);//操作状态（0正常 1异常）
            crudBiz.execute("put", "sys_operation_log", operaLog);

            //打印日志
            StringBuilder logStr = new StringBuilder("Operation log").append(System.lineSeparator()).append("【").append(System.lineSeparator());
            operaLog.forEach((k, v) -> {
                logStr.append(k).append(":").append(v).append(System.lineSeparator());
            });
            logStr.append("】");
            log.info(logStr.toString());
        }
    }
}
