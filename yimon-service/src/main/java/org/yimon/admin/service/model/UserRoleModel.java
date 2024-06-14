package org.yimon.admin.service.model;

import lombok.Getter;
import lombok.Setter;
import org.yimon.admin.core.pojo.ABasePojo;

/**
 * @author: ym.gao
 * @description: 菜单信息
 * @date: 2024/6/11 14:39
 */
@Setter
@Getter
public class UserRoleModel extends ABasePojo {

    private Long id;//主键
    private Long userId;//所属用户
    private Long roleId;//所属角色
    private String remark;//备注
}
