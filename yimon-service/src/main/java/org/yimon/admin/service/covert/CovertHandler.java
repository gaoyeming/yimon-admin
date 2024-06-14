package org.yimon.admin.service.covert;

import org.yimon.admin.core.constant.DatePattern;
import org.yimon.admin.core.util.DateFormatUtils;
import org.yimon.admin.core.util.StringUtils;
import org.yimon.admin.dal.entity.SysMenu;
import org.yimon.admin.dal.entity.SysUser;
import org.yimon.admin.dal.entity.SysUserRole;
import org.yimon.admin.service.model.UserDetailModel;
import org.yimon.admin.service.model.UserMenuModel;
import org.yimon.admin.service.model.UserRoleModel;
import org.yimon.admin.util.AesUtils;
import org.yimon.admin.util.constant.GlobalConstants;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author: ym.gao
 * @description: 属性转换
 * @date: 2024/6/11 16:02
 */
public final class CovertHandler {

    private CovertHandler() {
    }

    public static UserDetailModel covert(SysUser sysUser) {
        UserDetailModel model = new UserDetailModel();
        model.setId(sysUser.getId());
        model.setLoginName(sysUser.getLoginName());
        model.setLoginPassword(CovertHandler.encoder(sysUser.getLoginPassword()));
        model.setRealName(sysUser.getRealName());
        model.setContactMobile(CovertHandler.encoder(sysUser.getContactMobile()));
        model.setContactEmail(sysUser.getContactEmail());
        model.setContactAddress(sysUser.getContactAddress());
        model.setHeadPortraits(sysUser.getHeadPortraits());
        model.setLastLoginDate(sysUser.getLastLoginDate() != null ? DateFormatUtils.format(sysUser.getLastLoginDate(), DatePattern.NORM_DATETIME_PATTERN) : null);
        model.setLastLoginDevice(sysUser.getLastLoginDevice());
        return model;
    }

    public static UserRoleModel covert(SysUserRole sysUserRole) {
        UserRoleModel model = new UserRoleModel();
        model.setId(sysUserRole.getId());
        model.setUserId(sysUserRole.getUserId());
        model.setRoleId(sysUserRole.getRoleId());
        model.setRemark(sysUserRole.getRemark());
        return model;
    }

    public static UserMenuModel covert(SysMenu sysMenu) {
        UserMenuModel model = new UserMenuModel();
        model.setId(sysMenu.getId());
        model.setParentId(sysMenu.getParentId());
        model.setMenuCode(sysMenu.getMenuCode());
        model.setMenuName(sysMenu.getMenuName());
        model.setMenuIcon(sysMenu.getMenuIcon());
        model.setMenuUrl(sysMenu.getMenuUrl());
        model.setMenuPath(sysMenu.getMenuPath());
        model.setMenuType(sysMenu.getMenuType());
        return model;
    }

    private static String encoder(String encryptText) {
        if (StringUtils.isBlank(encryptText)) {
            return null;
        }

        return Base64.getEncoder().encodeToString(AesUtils.decrypt(encryptText, GlobalConstants.SECRET).getBytes(StandardCharsets.UTF_8));
    }
}
