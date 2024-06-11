package org.yimon.admin.dal.entity;

import com.ctrip.platform.dal.dao.DalPojo;
import com.ctrip.platform.dal.dao.annotation.Database;
import com.ctrip.platform.dal.dao.annotation.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.sql.Types;

@Entity
@Database(name="MySql_yimon")
@Table(name="sys_role_menu")
public class SysRoleMenu implements DalPojo {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value=Types.BIGINT)
	private Long id;
	
	@Column(name="create_time")
	@Type(value=Types.TIMESTAMP)
	private Timestamp createTime;
	
	@Column(name="dataChange_lastTime")
	@Type(value=Types.TIMESTAMP)
	private Timestamp dataChangeLastTime;
	
	@Column(name="role_id")
	@Type(value=Types.BIGINT)
	private Long roleId;
	
	@Column(name="menu_id")
	@Type(value=Types.BIGINT)
	private Long menuId;
	
	@Column(name="remark")
	@Type(value=Types.VARCHAR)
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getDataChangeLastTime() {
		return dataChangeLastTime;
	}

	public void setDataChangeLastTime(Timestamp dataChangeLastTime) {
		this.dataChangeLastTime = dataChangeLastTime;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}