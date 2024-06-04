CREATE DATABASE IF NOT EXISTS yimon_admin;
USE yimon_admin;

DROP TABLE IF EXISTS `yimon_admin`.`sys_operation_log`;
-- 系统用户操作日志表
CREATE TABLE IF NOT EXISTS `sys_operation_log`
(
    `id`                  bigint        NOT NULL AUTO_INCREMENT COMMENT '自增物理主键',
    `create_time`         timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间，应用不用维护',
    `dataChange_lastTime` timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间，应用不用维护',
    `trace_id`          varchar(64)   NOT NULL COMMENT '追踪流水号',
    `opera_type`       varchar(16)   NOT NULL COMMENT '操作类别，可以标记不同系统操作（yimon_admin）',
    `opera_user`          varchar(64)   NOT NULL COMMENT '操作用户',
    `opera_title`         varchar(128)  NOT NULL COMMENT '操作标题',
    `opera_device`        varchar(512)  NOT NULL DEFAULT '' COMMENT '请求设备信息',
    `opera_url`           varchar(256)  NOT NULL COMMENT '操作入口',
    `opera_param`         varchar(1024) NOT NULL DEFAULT '' COMMENT '操作参数',
    `opera_result`        varchar(1024) NOT NULL DEFAULT '' COMMENT '操作结果',
    `status`              int                    DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_operation_log_uidx_trace_id` (`trace_id`),
    KEY `sys_operation_log_idx_01` (`opera_user`),
    KEY `sys_operation_log_idx_02` (`opera_title`),
    KEY `sys_operation_log_idx_dataChange_lastTime` (`dataChange_lastTime`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统操作日志表';
