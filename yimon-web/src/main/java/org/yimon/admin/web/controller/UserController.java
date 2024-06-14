package org.yimon.admin.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yimon.admin.service.LoginService;
import org.yimon.admin.util.GsonHolder;
import org.yimon.admin.web.annotation.APIRequestLog;
import org.yimon.admin.web.controller.vo.RequestVO;
import org.yimon.admin.web.controller.vo.ResultVO;

import javax.annotation.Resource;
import java.util.Base64;

/**
 * @author: ym.gao
 * @description: 验证
 * @date: 2024/6/7 15:53
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private LoginService loginService;

    @PostMapping(value = "login")
    @APIRequestLog(value = "用户登录")
    public ResultVO login(@RequestBody RequestVO request) {
        String loginName = GsonHolder.getMapStr(request.getData()).get("loginName");
        String loginPassword = GsonHolder.getMapStr(request.getData()).get("loginPassword");
        String captchaType = GsonHolder.getMapStr(request.getData()).get("captchaType");
        String captchaKey = GsonHolder.getMapStr(request.getData()).get("captchaKey");
        String captcha = GsonHolder.getMapStr(request.getData()).get("captcha");
        return ResultVO.isSucceed(loginService.login(loginName, loginPassword, captchaType, captchaKey, captcha, request.getRequestDevice()));
    }
}
