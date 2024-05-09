package com.yimon.admin.biz;

import com.yimon.admin.core.exception.RejectedException;
import com.yimon.admin.service.RepositoryService;
import com.yimon.admin.util.SpringContextHandler;
import com.yimon.admin.util.constant.ResultCode;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ym.gao
 * @description: 通用的业务处理层
 * @date: 2024/4/29 16:41
 */
@Component
public class CrudBiz {

    /**
     * 对应增、删、改、查单笔、查多笔
     **/
    public static final List<String> METHODS = Arrays.asList("put", "delete", "post", "get", "gets");


    public Map<String, Object> execute(String method, Map<String, Map<String, Object>> requestMap) {
        if (!METHODS.contains(method)) {
            throw new RejectedException(ResultCode.REJECT.code(), "当前请求方法暂不支持，请确认");
        }
        //获取具体实现
        RepositoryService service = SpringContextHandler.getBeanByName(method, RepositoryService.class);
        Map<String, Object> result = new HashMap<>();
        requestMap.forEach((k, v) -> {
            //k为表名， v为map的参数
            result.put(k, service.execute(k, v));
        });

        return result;
    }
}
