package org.yimon.admin.service.model;

import lombok.Getter;
import lombok.Setter;
import org.yimon.admin.core.pojo.ABasePojo;

import java.util.List;

/**
 * @author: ym.gao
 * @description: 菜单信息
 * @date: 2024/6/11 14:39
 */
@Setter
@Getter
public class UserMenuModel extends ABasePojo {

    private Long id;//主键
    private Long parentId;//所属父级菜单（0不属于任何父级菜单）
    private String menuCode;//菜单编码
    private String menuName;//菜单名称
    private String menuIcon;//菜单图标
    private String menuUrl;//菜单url
    private String menuPath;//菜单存放路径
    private Integer menuType;//菜单类型：0:目录,1:菜单,2:子功能

    private List<UserMenuModel> children;//子类菜单
}
