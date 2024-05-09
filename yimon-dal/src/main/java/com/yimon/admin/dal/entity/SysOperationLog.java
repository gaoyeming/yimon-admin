package com.yimon.admin.dal.entity;

import com.ctrip.platform.dal.dao.DalPojo;
import com.ctrip.platform.dal.dao.annotation.Database;
import com.ctrip.platform.dal.dao.annotation.Type;
import com.yimon.admin.core.pojo.ABasePojo;

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

    @Column(name = "update_time")
    @Type(value = Types.INTEGER)
    private Timestamp updateTime;

    @Column(name = "opera_user")
    @Type(value = Types.VARCHAR)
    private String operaUser;

    /**
     * 操作标题
     */
    @Column(name = "opera_title")
    @Type(value = Types.VARCHAR)
    private String operaTitle;

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

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
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
}
