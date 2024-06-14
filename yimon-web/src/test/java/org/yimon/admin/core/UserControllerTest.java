package org.yimon.admin.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.yimon.admin.core.util.StringUtils;
import org.yimon.admin.util.GsonHolder;
import org.yimon.admin.util.LocalCache;
import org.yimon.admin.web.WebApplication;
import org.yimon.admin.web.controller.UserController;
import org.yimon.admin.web.controller.vo.RequestVO;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author: ym.gao
 * @description: crud单元测试
 * @date: 2024/5/8 14:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
public class UserControllerTest {

    @Resource
    private UserController userController;

    @Test
    public void loginTest() {

        RequestVO request = new RequestVO();

        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("loginName", "admin");
        valueMap.put("loginPassword", "123456");
        valueMap.put("captchaType", "login");
        valueMap.put("captchaKey", UUID.randomUUID().toString());
        valueMap.put("captcha", "QWERT");
        request.setData(GsonHolder.toJson(valueMap));

        //提现设置好验证码
        LocalCache.put(StringUtils.joinWith("::", valueMap.get("captchaType"), valueMap.get("captchaKey")), "QWERT");
        System.out.println(userController.login(request));
    }

}
