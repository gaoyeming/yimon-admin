package org.yimon.admin.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yimon.admin.core.check.Validate;
import org.yimon.admin.core.exception.BusinessException;
import org.yimon.admin.dal.entity.SysRoleMenu;
import org.yimon.admin.dal.entity.SysUser;
import org.yimon.admin.dal.entity.SysUserRole;
import org.yimon.admin.service.covert.CovertHandler;
import org.yimon.admin.service.impl.GETS_Repository;
import org.yimon.admin.service.impl.GET_Repository;
import org.yimon.admin.service.impl.POST_Repository;
import org.yimon.admin.service.model.UserDetailModel;
import org.yimon.admin.service.model.UserMenuModel;
import org.yimon.admin.service.model.UserRoleModel;
import org.yimon.admin.util.constant.ResultCode;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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

    @Resource
    private GETS_Repository gets;

    @Resource
    private POST_Repository post;

    @Resource
    private MenuService menuService;

    /**
     * 获取用户信息
     *
     * @param loginName 登录名
     * @return UserDetailModel
     */
    public UserDetailModel getUserByLoginName(String loginName) {
        Validate.isNotBank(loginName, "loginName not be empty");
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("loginName", loginName);

        Map<String, Object> user = get.execute("sys_user", paramsMap);
        if (user == null || user.get("@RESULT@") == null) {
            throw new BusinessException(ResultCode.PARAMS_ERROR.code(), "user not exist");
        }
        SysUser sysUser = (SysUser) user.get("@RESULT@");
        if (!sysUser.getEnabled().equals(1)) {
            throw new BusinessException(ResultCode.PARAMS_ERROR.code(), "user unavailable");
        }
        return CovertHandler.covert(sysUser);
    }

    /**
     * 获取用户角色
     * @param loginName 登录名
     * @return List<UserRoleModel>
     */
    public List<UserRoleModel> getRoleByLoginName(String loginName) {
        Validate.isNotBank(loginName, "loginName not be empty");
        //首先获取用户信息
        UserDetailModel userDetailModel = this.getUserByLoginName(loginName);

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("userId", userDetailModel.getId());

        Map<String, Object> userRole = gets.execute("sys_user_role", paramsMap);
        if (userRole == null || userRole.get("@RESULT@") == null) {
            log.warn("loginName:{} the user has not assigned a role", loginName);
            return Collections.emptyList();
        }
        List<SysUserRole> sysUserRoleList = (List<SysUserRole>) userRole.get("@RESULT@");
        return sysUserRoleList.stream().map(CovertHandler::covert).collect(Collectors.toList());
    }

    /**
     * 获取用户菜单
     * @param loginName 登录名
     * @return List<UserMenuModel>
     */
    public List<UserMenuModel> getMenuByLoginName(String loginName) {
        Validate.isNotBank(loginName, "loginName not be empty");
        //首先获取用户对应的角色
        List<UserRoleModel> userRoleModelList = this.getRoleByLoginName(loginName);
        if(userRoleModelList.isEmpty()) {
            return Collections.emptyList();
        }
        //获取角色对应的菜单
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("roleId", userRoleModelList.stream().map(UserRoleModel::getRoleId).collect(Collectors.toList()));
        Map<String, Object> roleMenu = gets.execute("sys_role_menu", paramsMap);
        if (roleMenu == null || roleMenu.get("@RESULT@") == null) {
            log.warn("loginName:{} no menu is assigned to the role of the user", loginName);
            return Collections.emptyList();
        }
        List<SysRoleMenu> sysRoleMenuList = (List<SysRoleMenu>) roleMenu.get("@RESULT@");
        return menuService.getMenuList(0L, sysRoleMenuList.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList()));
    }

    public void updateLastLogin(String loginName, Date loginDate, String deviceInfo){
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("loginName", loginName);
        paramsMap.put("lastLoginDate", loginDate);
        paramsMap.put("lastLoginDevice", deviceInfo);
        paramsMap.put("@WHERE@", "loginName");

        Map<String, Object> result =  post.execute("sys_user", paramsMap);
        if(result == null || result.get("@ROWS@") == null) {
            log.warn("loginName:{} update last login info fail", loginName);
        } else {
            log.info("loginName:{} update last login info result:{}", loginName, result.get("@ROWS@"));
        }
    }
}
