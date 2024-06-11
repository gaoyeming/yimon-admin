package org.yimon.admin.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yimon.admin.core.util.SnowFlakeUtils;
import org.yimon.admin.util.constant.GlobalConstants;
import org.yimon.admin.web.controller.vo.ResultVO;

import java.util.Collections;

/**
 * @author ym.gao
 * @date 2021/11/10 18:20
 * @desc 项目运行监控
 */
@Slf4j
@Controller
public class WelcomeController {

    /**
     * 页面路由配置--后续新增页面路由都需要再这里进行配置
     *
     * @return String-指定页面
     */
    @GetMapping(value = "/")
    public String page() {
        return "index.html";
    }

    @GetMapping(value = "/monitor")
    @ResponseBody
    public ResultVO monitorAPI() {
        String traceId = SnowFlakeUtils.generateTraceId("YIMON_ADMIN_");
        ThreadContext.put(GlobalConstants.TRACE_ID, traceId);
        return ResultVO.isSucceed(Collections.singletonMap("monitor", "WEB_OK"));
    }
}