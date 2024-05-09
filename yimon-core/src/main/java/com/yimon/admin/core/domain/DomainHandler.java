package com.yimon.admin.core.domain;

import com.yimon.admin.core.context.BaseContext;
import com.yimon.admin.core.context.ContextKey;
import com.yimon.admin.core.entrance.AEntrance;
import com.yimon.admin.core.exception.BusinessException;
import com.yimon.admin.core.log.BaseLogger;
import com.yimon.admin.core.result.ReturnCode;

/**
 * @author: ym.gao
 * @description: 域处理
 * @date: 2024/2/1 11:43
 */
public class DomainHandler {

    private final DomainConfig<? extends AEntrance> domainConfig;


    public DomainHandler(DomainConfig<? extends AEntrance> domainConfig) {
        this.domainConfig = domainConfig;
    }

    public BaseContext fire(ContextKey requestContextKey, BaseContext requestContext) throws Exception {
        AEntrance entrance = domainConfig.getEntrance(requestContextKey);
        if (entrance == null) {
            BaseLogger.warn("domain entrance not exist");
            throw new BusinessException(ReturnCode.FAILURE.code(), "domain entrance not exist");
        }
        BaseContext responseContext = entrance.uniformIn(requestContext); //执行事件
        //获取下一笔ContextKey
        ContextKey nextContextKey = domainConfig.getNextContextKey(requestContextKey);
        if (nextContextKey == null) {
            BaseLogger.info("domain have not next, is end");
            return responseContext;
        }
        //转换参数
        DomainSwitcher domainSwitcher = domainConfig.getSwitcher(requestContextKey);
        BaseContext nextContext = domainSwitcher.covertNext(requestContext, responseContext);
        BaseLogger.info("currentContextKey:[{}] --> nextContextKey:[{}]", requestContextKey, nextContextKey);
        return fire(nextContextKey, nextContext);
    }
}
