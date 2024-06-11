package org.yimon.admin.dal.entity;

import com.ctrip.platform.dal.dao.DalPojo;
import com.ctrip.platform.dal.dao.annotation.Database;
import com.ctrip.platform.dal.dao.annotation.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.sql.Types;

@Entity
@Database(name="MySql_yimon")
@Table(name="sys_operation_log")
public class SysOperationLog implements DalPojo {
	
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
	
	@Column(name="trace_id")
	@Type(value=Types.VARCHAR)
	private String traceId;

	@Column(name="opera_type")
	@Type(value=Types.VARCHAR)
	private String operaType;
	
	@Column(name="opera_user")
	@Type(value=Types.VARCHAR)
	private String operaUser;
	
	@Column(name="opera_title")
	@Type(value=Types.VARCHAR)
	private String operaTitle;
	
	@Column(name="opera_device")
	@Type(value=Types.VARCHAR)
	private String operaDevice;
	
	@Column(name="opera_url")
	@Type(value=Types.VARCHAR)
	private String operaUrl;
	
	@Column(name="opera_param")
	@Type(value=Types.VARCHAR)
	private String operaParam;
	
	@Column(name="opera_result")
	@Type(value=Types.VARCHAR)
	private String operaResult;
	
	@Column(name="status")
	@Type(value=Types.INTEGER)
	private Integer status;

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

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public String getOperaType() {
		return operaType;
	}

	public void setOperaType(String operaType) {
		this.operaType = operaType;
	}

	public String getOperaUser() {
		return operaUser;
	}

	public void setOperaUser(String operaUser) {
		this.operaUser = operaUser;
	}

	public String getOperaTitle() {
		return operaTitle;
	}

	public void setOperaTitle(String operaTitle) {
		this.operaTitle = operaTitle;
	}

	public String getOperaDevice() {
		return operaDevice;
	}

	public void setOperaDevice(String operaDevice) {
		this.operaDevice = operaDevice;
	}

	public String getOperaUrl() {
		return operaUrl;
	}

	public void setOperaUrl(String operaUrl) {
		this.operaUrl = operaUrl;
	}

	public String getOperaParam() {
		return operaParam;
	}

	public void setOperaParam(String operaParam) {
		this.operaParam = operaParam;
	}

	public String getOperaResult() {
		return operaResult;
	}

	public void setOperaResult(String operaResult) {
		this.operaResult = operaResult;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}