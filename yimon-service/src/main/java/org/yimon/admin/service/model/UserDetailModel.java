package org.yimon.admin.service.model;

import lombok.Getter;
import lombok.Setter;
import org.yimon.admin.core.pojo.ABasePojo;

/**
 * @author: ym.gao
 * @description: 登录用户信息
 *
 * @date: 2024/6/7 17:13
 */
@Setter
@Getter
public class UserDetailModel extends ABasePojo {

    private Long id;//主键
    private String loginName;//登录名
    private String loginPassword;//登录密码
    private String realName;//真实姓名
    private String contactEmail;//联系邮箱
    private String contactMobile;//联系电话
    private String contactAddress;//联系地址
    private String headPortraits;//用户头像
    private String lastLoginDate;//上次登录时间
    private String lastLoginDevice;//上次登录设备
}
