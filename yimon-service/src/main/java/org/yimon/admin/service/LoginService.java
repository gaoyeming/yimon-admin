package org.yimon.admin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yimon.admin.core.check.Validate;
import org.yimon.admin.core.exception.BusinessException;
import org.yimon.admin.service.model.UserDetailModel;
import org.yimon.admin.util.constant.ResultCode;

import javax.annotation.Resource;

/**
 * @author: ym.gao
 * @description: 登录登出
 * @date: 2024/6/7 17:05
 */
@Slf4j
@Service
public class LoginService {

    @Resource
    private CaptchaService captchaService;

    @Resource
    private UserService userService;


    public UserDetailModel login(String captcha, String captchaType, String captchaKey, String loginName, String loginPassword){
        Validate.isNotBank(captcha, "captcha not be empty");
        Validate.isNotBank(captchaType, "captchaType not be empty");
        Validate.isNotBank(captchaKey, "captchaKey not be empty");
        Validate.isNotBank(loginName, "loginName not be empty");
        Validate.isNotBank(loginPassword, "loginPassword not be empty");
        //校验验证码
        if(!captchaService.compareCaptcha(captcha, captchaType, captchaKey)){
            throw new BusinessException(ResultCode.PARAMS_ERROR.code(), "verify captcha fail");
        }
        //获取用户
        UserDetailModel model = userService.getUserByLoginName(loginName);
        if(!loginPassword.equals(model.getLoginPassword())) {
            throw new BusinessException(ResultCode.PARAMS_ERROR.code(), "password is error");
        }
        return model;
    }


}
