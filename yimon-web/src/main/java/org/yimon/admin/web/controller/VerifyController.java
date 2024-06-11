package org.yimon.admin.web.controller;

import org.springframework.web.bind.annotation.*;
import org.yimon.admin.service.CaptchaService;
import org.yimon.admin.util.GsonHolder;
import org.yimon.admin.web.annotation.APIRequestLog;
import org.yimon.admin.web.controller.vo.RequestVO;
import org.yimon.admin.web.controller.vo.ResultVO;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * @author: ym.gao
 * @description: 验证
 * @date: 2024/6/7 15:53
 */
@RestController
@RequestMapping("verify")
public class VerifyController {

    @Resource
    private CaptchaService captchaService;

    @PostMapping(value = "captcha/create")
    @APIRequestLog(value = "生成图片验证码")
    public ResultVO crud(@RequestBody RequestVO request) {
        String captchaType = GsonHolder.getMapStr(request.getData()).get("captchaType");
        String captchaKey = GsonHolder.getMapStr(request.getData()).get("captchaKey");
        return ResultVO.isSucceed(Collections.singletonMap("captchaImg", captchaService.createCaptcha(captchaType, captchaKey)));
    }
}
