CREATE DATABASE IF NOT EXISTS yimon_admin;

USE yimon_admin;

-- 系统操作日志表
DROP TABLE IF EXISTS `yimon_admin`.`sys_operation_log`;
CREATE TABLE IF NOT EXISTS `sys_operation_log`
(
    `id`                  bigint        NOT NULL AUTO_INCREMENT COMMENT '自增物理主键',
    `create_time`         timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间，应用不用维护',
    `dataChange_lastTime` timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间，应用不用维护',
    `trace_id`            varchar(64)   NOT NULL COMMENT '追踪流水号',
    `opera_type`          varchar(16)   NOT NULL COMMENT '操作类别，可以标记不同系统操作（yimon_admin）',
    `opera_user`          varchar(64)   NOT NULL COMMENT '操作用户',
    `opera_title`         varchar(128)  NOT NULL COMMENT '操作标题',
    `opera_device`        varchar(512)  NOT NULL DEFAULT '' COMMENT '请求设备信息',
    `opera_url`           varchar(256)  NOT NULL COMMENT '操作入口',
    `opera_param`         varchar(1024) NOT NULL DEFAULT '' COMMENT '操作参数',
    `opera_result`        varchar(1024) NOT NULL DEFAULT '' COMMENT '操作结果',
    `status`              int                    DEFAULT 1 COMMENT '操作状态（1正常 0异常）',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_operation_log_unique_trace_id` (`trace_id`),
    KEY `sys_operation_log_idx_01` (`opera_user`),
    KEY `sys_operation_log_idx_02` (`opera_title`),
    KEY `sys_operation_log_idx_dataChange_lastTime` (`dataChange_lastTime`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统操作日志表';
-- 系统用户信息表
DROP TABLE IF EXISTS `yimon_admin`.`sys_user`;
CREATE TABLE `sys_user`
(
    `id`                  bigint       NOT NULL AUTO_INCREMENT COMMENT '自增物理主键',
    `create_time`         timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间，应用不用维护',
    `dataChange_lastTime` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间，应用不用维护',
    `login_name`          varchar(32)  NOT NULL COMMENT '登录名',
    `login_password`      varchar(64)  NOT NULL COMMENT '登录密码',
    `real_name`           varchar(32)  NOT NULL COMMENT '真实姓名',
    `contact_email`       varchar(64)  NOT NULL DEFAULT '' COMMENT '联系邮箱',
    `contact_mobile`      varchar(32)  NOT NULL DEFAULT '' COMMENT '联系电话',
    `contact_address`     varchar(512) NOT NULL DEFAULT '' COMMENT '联系地址',
    `head_portraits`      varchar(128) NOT NULL DEFAULT '' COMMENT '用户头像',
    `last_login_date`     timestamp             DEFAULT NULL COMMENT '最后登录时间',
    `last_login_device`   varchar(512) NOT NULL DEFAULT '' COMMENT '最后登录设备信息',
    `enabled`             tinyint      NOT NULL DEFAULT 1 COMMENT '可用（1表示可用，0表示停用）',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_user_unique_login_name` (`login_name`),
    KEY `sys_user_idx_01` (`real_name`),
    KEY `sys_operation_log_idx_dataChange_lastTime` (`dataChange_lastTime`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统用户信息表';
-- 系统角色表
DROP TABLE IF EXISTS `yimon_admin`.`sys_role`;
CREATE TABLE `sys_role`
(
    `id`                  bigint       NOT NULL AUTO_INCREMENT COMMENT '自增物理主键',
    `create_time`         timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间，应用不用维护',
    `dataChange_lastTime` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间，应用不用维护',
    `role_code`           varchar(32)  NOT NULL COMMENT '角色编码',
    `role_name`           varchar(64)  NOT NULL COMMENT '角色名称',
    `remark`              varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_role_unique_role_code` (`role_code`),
    KEY `sys_role_idx_01` (`role_name`),
    KEY `sys_operation_log_idx_dataChange_lastTime` (`dataChange_lastTime`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统角色信息表';
-- 系统菜单表
DROP TABLE IF EXISTS `yimon_admin`.`sys_menu`;
CREATE TABLE `sys_menu`
(
    `id`                  bigint      NOT NULL AUTO_INCREMENT COMMENT '自增物理主键',
    `create_time`         timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间，应用不用维护',
    `dataChange_lastTime` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间，应用不用维护',
    `parent_id`           bigint      NOT NULL DEFAULT 0 COMMENT '所属父级菜单（0不属于任何父级菜单）',
    `menu_code`           varchar(32) NOT NULL COMMENT '菜单编码',
    `menu_name`           varchar(32) NOT NULL COMMENT '菜单名称',
    `menu_icon`           varchar(32) NOT NULL DEFAULT '' COMMENT '菜单图标',
    `menu_url`            varchar(64) NOT NULL DEFAULT '' COMMENT '菜单url',
    `menu_path`           varchar(64) NOT NULL DEFAULT '' COMMENT '菜单存放路径',
    `menu_type`           int         NOT NULL DEFAULT 0 COMMENT '菜单类型：0:目录,1:菜单,2:子功能',
    `order`               int         NOT NULL DEFAULT 0 COMMENT '菜单排序（只对同类型菜单生效）',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_menu_unique_menu_code` (`menu_code`),
    KEY `sys_menu_idx_01` (`menu_name`),
    KEY `sys_menu_idx_02` (`parent_id`),
    KEY `sys_operation_log_idx_dataChange_lastTime` (`dataChange_lastTime`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统菜单信息表';
-- 系统用户角色映射表
DROP TABLE IF EXISTS `yimon_admin`.`sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `id`                  bigint       NOT NULL AUTO_INCREMENT COMMENT '自增物理主键',
    `create_time`         timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间，应用不用维护',
    `dataChange_lastTime` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间，应用不用维护',
    `user_id`             bigint       NOT NULL COMMENT '所属用户',
    `role_id`             bigint       NOT NULL COMMENT '所属角色',
    `remark`              varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_user_role_unique_user_id_role_id` (`user_id`, `role_id`),
    KEY `sys_user_role_idx_01` (`user_id`),
    KEY `sys_user_role_idx_02` (`role_id`),
    KEY `sys_operation_log_idx_dataChange_lastTime` (`dataChange_lastTime`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统用户角色映射表';
-- 系统角色菜单映射表
DROP TABLE IF EXISTS `yimon_admin`.`sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
    `id`                  bigint       NOT NULL AUTO_INCREMENT COMMENT '自增物理主键',
    `create_time`         timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间，应用不用维护',
    `dataChange_lastTime` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间，应用不用维护',
    `role_id`             bigint       NOT NULL COMMENT '所属角色',
    `menu_id`             bigint       NOT NULL COMMENT '所属菜单',
    `remark`              varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_role_menu_unique_role_id_menu_id` (`role_id`, `menu_id`),
    KEY `sys_role_menu_idx_01` (`role_id`),
    KEY `sys_role_menu_idx_02` (`menu_id`),
    KEY `sys_operation_log_idx_dataChange_lastTime` (`dataChange_lastTime`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统角色菜单映射表';