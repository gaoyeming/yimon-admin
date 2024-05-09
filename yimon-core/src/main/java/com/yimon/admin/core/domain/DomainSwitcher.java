package com.yimon.admin.core.domain;

import com.yimon.admin.core.context.BaseContext;

/**
 * @author: ym.gao
 * @description: 事件域
 * @date: 2024/1/31 14:47
 */
public interface DomainSwitcher {

    BaseContext covertNext(BaseContext requestContext, BaseContext responseContext);
}
