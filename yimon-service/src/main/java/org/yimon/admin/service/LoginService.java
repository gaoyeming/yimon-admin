package org.yimon.admin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yimon.admin.core.async.ThreadPoolExecutor;
import org.yimon.admin.core.check.Validate;
import org.yimon.admin.core.exception.BusinessException;
import org.yimon.admin.service.model.UserDetailModel;
import org.yimon.admin.service.model.UserMenuModel;
import org.yimon.admin.util.AesUtils;
import org.yimon.admin.util.constant.GlobalConstants;
import org.yimon.admin.util.constant.ResultCode;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Resource
    private JwtTokenService jwtTokenService;

    @Resource
    private ThreadPoolExecutor yimonDiscardThreadPool;


    public Map<String, Object> login(String loginName, String loginPassword, String captchaType, String captchaKey, String captcha, String deviceInfo) {
        Validate.isNotBank(captcha, "captcha not be empty");
        Validate.isNotBank(captchaType, "captchaType not be empty");
        Validate.isNotBank(captchaKey, "captchaKey not be empty");
        Validate.isNotBank(loginName, "loginName not be empty");
        Validate.isNotBank(loginPassword, "loginPassword not be empty");
        //校验验证码
        if (!captchaService.compareCaptcha(captcha, captchaType, captchaKey)) {
            throw new BusinessException(ResultCode.PARAMS_ERROR.code(), "verify captcha fail");
        }
        //获取用户
        UserDetailModel userDetailModel = userService.getUserByLoginName(loginName);
        if (!loginPassword.equals(userDetailModel.getLoginPassword())) {
            throw new BusinessException(ResultCode.PARAMS_ERROR.code(), "password is error");
        }
        //生成用户对应的token
        String token = jwtTokenService.generateToken(loginName);
        //获取菜单权限
        List<UserMenuModel> userMenuModelList = userService.getMenuByLoginName(loginName);
        //返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("user", userDetailModel);
        result.put("token", token);
        result.put("menuList", userMenuModelList);
        //登录成功异步线程记录最新登录时间信息
        yimonDiscardThreadPool.execute(() -> lastLogin(token, deviceInfo));
        return result;
    }

    /**
     * 更新最后登录的时间以及设备信息
     *
     * @param token      当前token
     * @param deviceInfo 登录的设备信息
     */
    public void lastLogin(String token, String deviceInfo) {
        //记录最新的登录时间以及设备信息
        Date lastLoginDate = jwtTokenService.getIssuedAt(token);
        String loginName = jwtTokenService.getLoginNameFromToken(token);
        userService.updateLastLogin(loginName, lastLoginDate, deviceInfo);
    }

}
