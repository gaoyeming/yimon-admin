package org.yimon.admin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yimon.admin.core.check.Validate;
import org.yimon.admin.dal.entity.SysMenu;
import org.yimon.admin.service.covert.CovertHandler;
import org.yimon.admin.service.impl.GETS_Repository;
import org.yimon.admin.service.impl.GET_Repository;
import org.yimon.admin.service.model.UserMenuModel;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: ym.gao
 * @description: 菜单
 * @date: 2024/6/11 14:38
 */
@Slf4j
@Service
public class MenuService {

    @Resource
    private GET_Repository get;

    @Resource
    private GETS_Repository gets;

    /**
     * 获取菜单信息并进行分级管理(只会查询目录跟菜单，子功能不在查询范围内--子功能后续只有后端用到)
     * @param menuIdList 菜单主键列表
     * @return List<UserMenuModel>
     */
    public List<UserMenuModel> getMenuList(Long parentMenuId, List<Long> menuIdList){
        //从列表中首先筛选无父级的目录或者菜单
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("parentId", parentMenuId);//无父级的目录
        paramsMap.put("menuType", Arrays.asList(0, 1));//目录或者菜单
        paramsMap.put("@ORDER@", "sort");//排序，升序
        if(menuIdList != null && !menuIdList.isEmpty()) {
            paramsMap.put("id", menuIdList);//指定菜单范围
        }
        Map<String, Object> menu = gets.execute("sys_menu", paramsMap);
        if(menu == null || menu.get("@RESULT@") == null) {
            return Collections.emptyList();
        }
        List<SysMenu> menuList = (List<SysMenu>) menu.get("@RESULT@");
        List<UserMenuModel> userMenuModelList = menuList.stream().map(CovertHandler::covert).collect(Collectors.toList());
        //计算子菜单
        userMenuModelList.forEach(userMenuModel -> userMenuModel.setChildren(this.getMenuList(userMenuModel.getId(), menuIdList)));
        return userMenuModelList;
    }
}
