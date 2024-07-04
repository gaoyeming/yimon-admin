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
    `sort`               int         NOT NULL DEFAULT 0 COMMENT '菜单排序（只对同类型菜单生效）',
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

-- 数据初始化
INSERT INTO yimon_admin.sys_user (login_name, login_password, real_name, contact_email, contact_mobile, contact_address,
                                  head_portraits, last_login_date, last_login_device, enabled)
VALUES ('admin', 'xOXkH5VxISnQDond3AENPw==', '管理员', 'yimon@foxmail.com', '18121213232', '上海浦东',
        '/assets/img/user/header_default.jpeg', NULL, '', 1);
INSERT INTO yimon_admin.sys_role (role_code, role_name, remark)
VALUES ('SYS_ADMIN', '系统管理员', '管理员');
INSERT INTO yimon_admin.sys_menu (parent_id, menu_code, menu_name, menu_icon, menu_url, menu_path, menu_type, `sort`)
VALUES (0, 'Sys-Manage', '系统管理', 'Operation', '', '', 0, 1),
       (1, 'Sys-Menu', '菜单管理', 'Menu', '/menu', '/sys/Menu', 1, 1),
       (1, 'Sys-Role', '角色管理', 'Rank', '/role', '/sys/Role', 1, 2),
       (1, 'Sys-User', '用户管理', 'Avatar', '/user', '/sys/User', 1, 3),
       (2, 'Sys-Menu-Gets', '获取菜单列表', '', 'http://127.0.0.1:8080', '/crud/sys_menu/gets', 2, 1),
       (2, 'Sys-Menu-Get', '获取指定菜单', '', 'http://127.0.0.1:8080', '/crud/sys_menu/get', 2, 2),
       (2, 'Sys-Menu-Put', '新增菜单', '', 'http://127.0.0.1:8080', '/crud/sys_menu/put', 2, 3),
       (2, 'Sys-Menu-Delete', '删除菜单', '', 'http://127.0.0.1:8080', '/crud/sys_menu/delete', 2, 4),
       (2, 'Sys-Menu-Post', '更新菜单', '', 'http://127.0.0.1:8080', '/crud/sys_menu/post', 2, 5),
       (3, 'Sys-Role-Gets', '获取角色列表', '', 'http://127.0.0.1:8080', '/crud/sys_role/gets', 2, 1),
       (3, 'Sys-Role-Get', '获取指定角色', '', 'http://127.0.0.1:8080', '/crud/sys_role/get', 2, 2),
       (3, 'Sys-Role-Put', '新增角色', '', 'http://127.0.0.1:8080', '/crud/sys_role/put', 2, 3),
       (3, 'Sys-Role-Delete', '删除角色', '', 'http://127.0.0.1:8080', '/crud/sys_role/delete', 2, 4),
       (3, 'Sys-Role-Post', '更新角色', '', 'http://127.0.0.1:8080', '/crud/sys_role/post', 2, 5),
       (4, 'Sys-User-Gets', '获取用户列表', '', 'http://127.0.0.1:8080', '/crud/sys_user/gets', 2, 1),
       (4, 'Sys-User-Get', '获取指定用户', '', 'http://127.0.0.1:8080', '/crud/sys_user/get', 2, 2),
       (4, 'Sys-User-Put', '新增用户', '', 'http://127.0.0.1:8080', '/crud/sys_user/put', 2, 3),
       (4, 'Sys-User-Delete', '删除用户', '', 'http://127.0.0.1:8080', '/crud/sys_user/delete', 2, 4),
       (4, 'Sys-User-Post', '更新用户', '', 'http://127.0.0.1:8080', '/crud/sys_user/post', 2, 5);
INSERT INTO yimon_admin.sys_user_role (user_id, role_id, remark)
VALUES (1, 1, '管理员-系统管理员');
INSERT INTO yimon_admin.sys_role_menu (role_id, menu_id, remark)
VALUES (1, 1, '系统管理员-系统管理'),
       (1, 2, '系统管理员-菜单管理'),
       (1, 3, '系统管理员-角色管理'),
       (1, 4, '系统管理员-用户管理'),
       (1, 5, '系统管理员-获取菜单列表'),
       (1, 6, '系统管理员-获取指定菜单'),
       (1, 7, '系统管理员-新增菜单'),
       (1, 8, '系统管理员-删除菜单'),
       (1, 9, '系统管理员-更新菜单'),
       (1, 10, '系统管理员-获取角色列表'),
       (1, 11, '系统管理员-获取指定角色'),
       (1, 12, '系统管理员-新增角色'),
       (1, 13, '系统管理员-删除角色'),
       (1, 14, '系统管理员-更新角色'),
       (1, 15, '系统管理员-获取用户列表'),
       (1, 16, '系统管理员-获取指定用户'),
       (1, 17, '系统管理员-新增用户'),
       (1, 18, '系统管理员-删除用户'),
       (1, 19, '系统管理员-更新用户');



