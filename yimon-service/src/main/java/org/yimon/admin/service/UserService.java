package org.yimon.admin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yimon.admin.core.check.Validate;
import org.yimon.admin.core.constant.DatePattern;
import org.yimon.admin.core.exception.BusinessException;
import org.yimon.admin.core.util.DateFormatUtils;
import org.yimon.admin.dal.entity.SysUser;
import org.yimon.admin.service.impl.GET_Repository;
import org.yimon.admin.service.model.UserDetailModel;
import org.yimon.admin.util.constant.ResultCode;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: ym.gao
 * @description: 用户
 * @date: 2024/6/7 18:54
 */
@Slf4j
@Service
public class UserService {

    @Resource
    private GET_Repository get;

    public UserDetailModel getUserByLoginName(String loginName) {
        Validate.isNotBank(loginName, "loginName not be empty");
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("loginName", loginName);

        Map<String, Object> user = get.execute("sys_user", paramsMap);
        if(user == null || user.get("@RESULT@") == null) {
            throw new BusinessException(ResultCode.PARAMS_ERROR.code(), "user not exist");
        }
        SysUser sysUser = (SysUser) user.get("@RESULT@");
        if(!sysUser.getEnabled().equals(1)) {
            throw new BusinessException(ResultCode.PARAMS_ERROR.code(), "user unavailable");
        }
        return covert(sysUser);
    }

    private UserDetailModel covert(SysUser sysUser) {
        UserDetailModel model = new UserDetailModel();
        model.setLoginName(sysUser.getLoginName());
        model.setLoginPassword(sysUser.getLoginPassword());
        model.setRealName(sysUser.getRealName());
        model.setContactMobile(sysUser.getContactMobile());
        model.setContactEmail(sysUser.getContactEmail());
        model.setContactAddress(sysUser.getContactAddress());
        model.setHeadPortraits(sysUser.getHeadPortraits());
        model.setLastLoginDate(DateFormatUtils.format(sysUser.getLastLoginDate(), DatePattern.NORM_DATETIME_PATTERN));
        model.setLastLoginDevice(sysUser.getLastLoginDevice());
        return model;
    }
}
