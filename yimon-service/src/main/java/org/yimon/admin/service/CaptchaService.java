package org.yimon.admin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yimon.admin.core.check.Validate;
import org.yimon.admin.core.exception.UserDefinedException;
import org.yimon.admin.core.util.StringUtils;
import org.yimon.admin.util.CaptchaGenerator;
import org.yimon.admin.util.LocalCache;
import org.yimon.admin.util.constant.ResultCode;

import java.util.Map;

/**
 * @author: ym.gao
 * @description: 验证码
 * @date: 2024/6/7 15:45
 */
@Slf4j
@Service
public class CaptchaService {

    /**
     * 创建验证码
     * @param captchaType 验证码类型
     * @param captchaKey 验证码key
     * @return 验证码base64图片结果
     */
    public String createCaptcha(String captchaType, String captchaKey) {
        Validate.isNotBank(captchaType, "captchaType not be empty");
        Validate.isNotBank(captchaKey, "captchaKey not be empty");
        Map<String, String> captchaMap = CaptchaGenerator.generateCaptcha();
        if (captchaMap.isEmpty()) {
            log.warn("generate captcha fail, type:{} key:{}", captchaType, captchaKey);
            throw new UserDefinedException(ResultCode.EXCEPTION.code(), "generate captcha fail");
        }
        String imageBase64Data = captchaMap.get("ImageBase64Data");
        String captchaData = captchaMap.get("CaptchaData");
        //将验证码存入缓存
        LocalCache.put(StringUtils.joinWith("::", captchaType, captchaKey), captchaData);
        return imageBase64Data;
    }

    /**
     * 校验验证码
     * @param captcha 输入的验证码
     * @param captchaType 验证码类型
     * @param captchaKey 验证码key
     * @return 与缓存比对的校验结果
     */
    public boolean compareCaptcha(String captcha, String captchaType, String captchaKey) {
        Validate.isNotBank(captcha, "captcha not be empty");
        Validate.isNotBank(captchaType, "captchaType not be empty");
        Validate.isNotBank(captchaKey, "captchaKey not be empty");
        //获取缓存中的验证码
        String cacheCaptcha = LocalCache.get(StringUtils.joinWith("::", captchaType, captchaKey));
        if(captcha.equalsIgnoreCase(cacheCaptcha)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
