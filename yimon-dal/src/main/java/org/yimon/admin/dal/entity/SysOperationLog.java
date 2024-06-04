package org.yimon.admin.dal.entity;

import com.ctrip.platform.dal.dao.DalPojo;
import com.ctrip.platform.dal.dao.annotation.Database;
import com.ctrip.platform.dal.dao.annotation.Type;
import org.yimon.admin.core.pojo.ABasePojo;

import javax.persistence.*;
import java.sql.Timestamp;
import java.sql.Types;

@Entity
@Database(name = "MySql_yimon")
@Table(name = "sys_operation_log")
public class SysOperationLog extends ABasePojo implements DalPojo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(value = Types.INTEGER)
    private Long id;

    @Column(name = "create_time")
    @Type(value = Types.TIMESTAMP)
    private Timestamp createTime;

    @Column(name = "dataChange_lastTime")
    @Type(value = Types.INTEGER)
    private Timestamp dataChangeLastTime;

    /**
     * 追踪流水号
     */
    @Column(name = "trace_id")
    @Type(value = Types.VARCHAR)
    private String traceId;

    /**
     * 操作类别，可以标记不同系统操作（yimon_admin）
     */
    @Column(name = "opera_type")
    @Type(value = Types.VARCHAR)
    private String operaType;

    /**
     * 操作用户
     */
    @Column(name = "opera_user")
    @Type(value = Types.VARCHAR)
    private String operaUser;

    /**
     * 操作标题
     */
    @Column(name = "opera_title")
    @Type(value = Types.VARCHAR)
    private String operaTitle;

    /**
     * 请求设备信息
     */
    @Column(name = "opera_device")
    @Type(value = Types.VARCHAR)
    private String operaDevice;

    /**
     * 操作入口
     */
    @Column(name = "opera_url")
    @Type(value = Types.VARCHAR)
    private String operaUrl;

    /**
     * 操作参数
     */
    @Column(name = "opera_param")
    @Type(value = Types.VARCHAR)
    private String operaParam;

    /**
     * 操作结果
     */
    @Column(name = "opera_result")
    @Type(value = Types.VARCHAR)
    private String operaResult;

    /**
     * 操作状态（0正常 1异常）
     */
    @Column(name = "status")
    @Type(value = Types.INTEGER)
    private Integer status;

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

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
