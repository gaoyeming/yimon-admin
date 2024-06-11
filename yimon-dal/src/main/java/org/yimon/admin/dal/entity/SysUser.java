package org.yimon.admin.dal.entity;

import com.ctrip.platform.dal.dao.DalPojo;
import com.ctrip.platform.dal.dao.annotation.Database;
import com.ctrip.platform.dal.dao.annotation.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.sql.Types;

@Entity
@Database(name="MySql_yimon")
@Table(name="sys_user")
public class SysUser implements DalPojo {
	
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
	
	@Column(name="login_name")
	@Type(value=Types.VARCHAR)
	private String loginName;
	
	@Column(name="login_password")
	@Type(value=Types.VARCHAR)
	private String loginPassword;
	
	@Column(name="real_name")
	@Type(value=Types.VARCHAR)
	private String realName;
	
	@Column(name="contact_email")
	@Type(value=Types.VARCHAR)
	private String contactEmail;
	
	@Column(name="contact_mobile")
	@Type(value=Types.VARCHAR)
	private String contactMobile;
	
	@Column(name="contact_address")
	@Type(value=Types.VARCHAR)
	private String contactAddress;
	
	@Column(name="head_portraits")
	@Type(value=Types.VARCHAR)
	private String headPortraits;
	
	@Column(name="last_login_date")
	@Type(value=Types.TIMESTAMP)
	private Timestamp lastLoginDate;
	
	@Column(name="last_login_device")
	@Type(value=Types.VARCHAR)
	private String lastLoginDevice;
	
	@Column(name="enabled")
	@Type(value=Types.TINYINT)
	private Integer enabled;

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

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getHeadPortraits() {
		return headPortraits;
	}

	public void setHeadPortraits(String headPortraits) {
		this.headPortraits = headPortraits;
	}

	public Timestamp getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Timestamp lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getLastLoginDevice() {
		return lastLoginDevice;
	}

	public void setLastLoginDevice(String lastLoginDevice) {
		this.lastLoginDevice = lastLoginDevice;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
}