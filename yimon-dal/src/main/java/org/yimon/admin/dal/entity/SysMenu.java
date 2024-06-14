package org.yimon.admin.dal.entity;

import com.ctrip.platform.dal.dao.DalPojo;
import com.ctrip.platform.dal.dao.annotation.Database;
import com.ctrip.platform.dal.dao.annotation.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.sql.Types;

@Entity
@Database(name="MySql_yimon")
@Table(name="sys_menu")
public class SysMenu implements DalPojo {
	
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
	
	@Column(name="parent_id")
	@Type(value=Types.BIGINT)
	private Long parentId;
	
	@Column(name="menu_code")
	@Type(value=Types.VARCHAR)
	private String menuCode;
	
	@Column(name="menu_name")
	@Type(value=Types.VARCHAR)
	private String menuName;
	
	@Column(name="menu_icon")
	@Type(value=Types.VARCHAR)
	private String menuIcon;
	
	@Column(name="menu_url")
	@Type(value=Types.VARCHAR)
	private String menuUrl;
	
	@Column(name="menu_path")
	@Type(value=Types.VARCHAR)
	private String menuPath;
	
	@Column(name="menu_type")
	@Type(value=Types.INTEGER)
	private Integer menuType;
	
	@Column(name="sort")
	@Type(value=Types.INTEGER)
	private Integer sort;

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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuPath() {
		return menuPath;
	}

	public void setMenuPath(String menuPath) {
		this.menuPath = menuPath;
	}

	public Integer getMenuType() {
		return menuType;
	}

	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}