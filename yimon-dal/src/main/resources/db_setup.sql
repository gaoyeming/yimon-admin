-- 系统用户操作日志表
CREATE TABLE `sys_user_operation_log`
(
    `id`            bigint(20)    NOT NULL AUTO_INCREMENT COMMENT '自增物理主键',
    `create_time`   datetime(6)   NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间，应用不用维护',
    `update_time`   datetime(6)   NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间，应用不用维护',
    `opera_user`    varchar(128)  NOT NULL DEFAULT '' COMMENT '操作用户',
    `opera_title`   varchar(100)  NOT NULL DEFAULT '' COMMENT '操作标题',
    `opera_class`   varchar(200)  NOT NULL DEFAULT '' COMMENT '操作类',
    `opera_method`  varchar(200)  NOT NULL DEFAULT '' COMMENT '操作方法',
    `request_ip`    varchar(25)   NOT NULL DEFAULT '' COMMENT '请求ip',
    `request_api`   varchar(200)  NOT NULL DEFAULT '' COMMENT '请求API',
    `request_body`  varchar(1024) NOT NULL DEFAULT '' COMMENT '请求参数',
    `response_body` varchar(1024) NOT NULL DEFAULT '' COMMENT '响应参数',
    PRIMARY KEY (`id`),
    KEY `sys_user_operation_log_idx_01` (`opera_user`),
    KEY `sys_user_operation_log_idx_02` (`request_api`),
    KEY `sys_user_operation_log_create_time` (`create_time`),
    KEY `sys_user_operation_log_update_time` (`update_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统用户操作日志表';

-- 系统缓存模拟表
CREATE TABLE `sys_cache_mock`
(
    `id`          bigint(20)    NOT NULL AUTO_INCREMENT COMMENT '自增物理主键',
    `create_time` datetime(6)   NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间，应用不用维护',
    `update_time` datetime(6)   NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间，应用不用维护',
    `cache_key`   varchar(512)  NOT NULL DEFAULT '' COMMENT '缓存key',
    `cache_value` varchar(2048) NOT NULL DEFAULT '' COMMENT '缓存value',
    `expire_time` bigint(20)    NOT NULL DEFAULT -1 COMMENT '失效时长，单位毫秒；-1则为永不失效',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_cache_mock_uidx_01` (`cache_key`),
    KEY `sys_cache_mock_create_time` (`create_time`),
    KEY `sys_cache_mock_update_time` (`update_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统模拟缓存表';

-- 系统文件表
CREATE TABLE `sys_file`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '自增物理主键',
    `create_time` datetime(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间，应用不用维护',
    `update_time` datetime(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间，应用不用维护',
    `file_id`     varchar(128) NOT NULL DEFAULT '' COMMENT '文件唯一id',
    `file_name`   varchar(128) NOT NULL DEFAULT '' COMMENT '文件名称',
    `file_store`  varchar(32)  NOT NULL DEFAULT '' COMMENT '文件存储方式',
    `file_path`   varchar(512) NOT NULL DEFAULT '' COMMENT '文件存放路径',
    `file_link`   varchar(512) NOT NULL DEFAULT '' COMMENT '文件访问链接',
    `file_status` char(1)      NOT NULL DEFAULT 'I' COMMENT '文件状态：I-初始化；W-待上传；U-已上传；F-上传失败',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_file_uidx_01` (`file_id`),
    KEY `sys_file_create_time` (`create_time`),
    KEY `sys_file_update_time` (`update_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统文件表';

-- 系统用户信息表
CREATE TABLE `sys_user`
(
    `id`              bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '自增物理主键',
    `create_time`     datetime(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间，应用不用维护',
    `update_time`     datetime(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间，应用不用维护',
    `create_user`     varchar(128) NOT NULL DEFAULT '' COMMENT '创建用户',
    `update_user`     varchar(128) NOT NULL DEFAULT '' COMMENT '更新用户',
    `login_name`      varchar(32)  NOT NULL DEFAULT '' COMMENT '登录名',
    `login_password`  varchar(64)  NOT NULL DEFAULT '' COMMENT '登录密码',
    `real_name`       varchar(32)  NOT NULL DEFAULT '' COMMENT '真实姓名',
    `head_file_id`    varchar(128) NOT NULL DEFAULT '' COMMENT '用户头像文件',
    `contact_email`   varchar(128) NOT NULL DEFAULT '' COMMENT '联系邮箱',
    `contact_mobile`  varchar(32)  NOT NULL DEFAULT '' COMMENT '联系电话',
    `last_login_date` datetime(6)           DEFAULT NULL COMMENT '最后登录时间',
    `last_login_ip`   varchar(32)  NOT NULL DEFAULT '' COMMENT '最后登录ip',
    `enabled`         tinyint(4)   NOT NULL DEFAULT 1 COMMENT '可用（1表示可用，0表示停用）',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_user_uidx_01` (`login_name`),
    KEY `sys_user_create_time` (`create_time`),
    KEY `sys_user_update_time` (`update_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统用户信息表';

-- 系统角色表
CREATE TABLE `sys_role`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '自增物理主键',
    `create_time` datetime(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间，应用不用维护',
    `update_time` datetime(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间，应用不用维护',
    `create_user` varchar(128) NOT NULL DEFAULT '' COMMENT '创建用户',
    `update_user` varchar(128) NOT NULL DEFAULT '' COMMENT '更新用户',
    `role_code`   varchar(32)  NOT NULL DEFAULT '' COMMENT '角色code',
    `role_name`   varchar(64)  NOT NULL DEFAULT '' COMMENT '角色名称',
    `remark`      varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_role_uidx_01` (`role_code`),
    KEY `sys_role_idx_01` (`role_name`),
    KEY `sys_role_create_time` (`create_time`),
    KEY `sys_role_update_time` (`update_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统角色表';

-- 系统菜单表
CREATE TABLE `sys_menu`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '自增物理主键',
    `create_time` datetime(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间，应用不用维护',
    `update_time` datetime(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间，应用不用维护',
    `create_user` varchar(128) NOT NULL DEFAULT '' COMMENT '创建用户',
    `update_user` varchar(128) NOT NULL DEFAULT '' COMMENT '更新用户',
    `parent_code` varchar(32)  NOT NULL DEFAULT '' COMMENT '所属父级菜单',
    `menu_code`   varchar(32)  NOT NULL DEFAULT '' COMMENT '菜单code',
    `menu_name`   varchar(32)  NOT NULL DEFAULT '' COMMENT '菜单名称',
    `menu_icon`   varchar(32)  NOT NULL DEFAULT '' COMMENT '菜单图标',
    `menu_url`    varchar(64)  NOT NULL DEFAULT '' COMMENT '菜单url',
    `menu_path`   varchar(64)  NOT NULL DEFAULT '' COMMENT '菜单存放路径',
    `menu_type`   int(5)       NOT NULL DEFAULT 0 COMMENT '菜单类型：0:目录,1:菜单,2:子功能',
    `order`       int(5)       NOT NULL DEFAULT 0 COMMENT '菜单排序',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_menu_uidx_01` (`menu_code`),
    KEY `sys_menu_idx_01` (`menu_name`),
    KEY `sys_menu_create_time` (`create_time`),
    KEY `sys_menu_update_time` (`update_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统菜单表';

-- 系统用户角色映射表
CREATE TABLE `sys_user_role`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '自增物理主键',
    `create_time` datetime(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间，应用不用维护',
    `update_time` datetime(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间，应用不用维护',
    `create_user` varchar(128) NOT NULL DEFAULT '' COMMENT '创建用户',
    `update_user` varchar(128) NOT NULL DEFAULT '' COMMENT '更新用户',
    `login_name`  varchar(32)  NOT NULL DEFAULT '' COMMENT '用户登录名',
    `role_code`   varchar(32)  NOT NULL DEFAULT '' COMMENT '角色code',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_user_role_uidx_01` (`login_name`, `role_code`),
    KEY `sys_user_role_idx_01` (`login_name`),
    KEY `sys_user_role_idx_02` (`role_code`),
    KEY `sys_user_role_create_time` (`create_time`),
    KEY `sys_user_role_update_time` (`update_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统用户角色映射表';

-- 系统角色菜单映射表
CREATE TABLE `sys_role_menu`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '自增物理主键',
    `create_time` datetime(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间，应用不用维护',
    `update_time` datetime(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间，应用不用维护',
    `create_user` varchar(128) NOT NULL DEFAULT '' COMMENT '创建用户',
    `update_user` varchar(128) NOT NULL DEFAULT '' COMMENT '更新用户',
    `role_code`   varchar(32)  NOT NULL DEFAULT '' COMMENT '角色id',
    `menu_code`   varchar(32)  NOT NULL DEFAULT '' COMMENT '菜单id',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_role_menu_uidx_01` (`role_code`, `menu_code`),
    KEY `sys_role_menu_idx_01` (`role_code`),
    KEY `sys_role_menu_idx_02` (`menu_code`),
    KEY `sys_role_menu_create_time` (`create_time`),
    KEY `sys_role_menu_update_time` (`update_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统角色菜单映射表';


--通道配置
CREATE TABLE `channel_dict_data`
(
    `id`                 bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '自增物理主键',
    `create_time`        datetime(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间，应用不用维护',
    `update_time`        datetime(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间，应用不用维护',
    `channel_code`       varchar(64)  NOT NULL DEFAULT '' COMMENT '通道编码',
    `dict_key`           varchar(64)  NOT NULL DEFAULT '' COMMENT '字典key',
    `dict_bank_code`     varchar(64)  NOT NULL DEFAULT '' COMMENT '银行字典code',
    `dict_bank_code_msg` varchar(128) NOT NULL DEFAULT '' COMMENT '银行字典描述',
    `dict_self_code`     varchar(64)  NOT NULL DEFAULT '' COMMENT '内部字典code',
    `dict_self_code_msg` varchar(128) NOT NULL DEFAULT '' COMMENT '内部字典描述',
    `status`             varchar(32)  NOT NULL DEFAULT 'USED' COMMENT '数据状态USED、STOP',
    `remark`             varchar(1024)         DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `channel_dict_data_uidx_01` (`channel_code`, `dict_key`),
    KEY `channel_dict_data_idx_01` (`dict_bank_code`),
    KEY `channel_dict_data_idx_02` (`dict_self_code`),
    KEY `channel_dict_data_create_time` (`create_time`),
    KEY `channel_dict_data_update_time` (`update_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='通道字典映射表';

CREATE TABLE `channel_rsp_info`
(
    `id`              bigint(20)    NOT NULL AUTO_INCREMENT COMMENT '自增物理主键',
    `create_time`     datetime(6)   NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间，应用不用维护',
    `update_time`     datetime(6)   NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间，应用不用维护',
    `channel_code`    varchar(64)   NOT NULL DEFAULT '' COMMENT '通道编码',
    `channel_id`      varchar(64)   NOT NULL DEFAULT '' COMMENT '渠道ID',
    `rsp_code`        varchar(64)   NOT NULL DEFAULT '' COMMENT '调单返回码',
    `rsp_msg`         varchar(2048) NOT NULL DEFAULT '' COMMENT '渠道返回描述',
    `self_rsp_code`   varchar(64)   NOT NULL DEFAULT '' COMMENT '关联self返回码',
    `self_rsp_msg`    varchar(64)   NOT NULL DEFAULT '' COMMENT '关联self返回描述',
    `status`          varchar(32)   NOT NULL DEFAULT 'USED' COMMENT '数据状态USED、STOP',
    `business_status` varchar(32)   NOT NULL DEFAULT '' COMMENT '业务状态S成功、F失败、P处理中',
    `remark`          varchar(1024)          DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `channel_rsp_info_uidx_01` (`channel_code`, `channel_id`),
    KEY `channel_rsp_info_idx_01` (`rsp_code`),
    KEY `channel_rsp_info_idx_02` (`self_rsp_code`),
    KEY `channel_rsp_info_create_time` (`create_time`),
    KEY `channel_rsp_info_update_time` (`update_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='通道响应码配置表';

CREATE TABLE `channel_route_data`
(
    `id`               bigint(20)    NOT NULL AUTO_INCREMENT COMMENT '自增物理主键',
    `create_time`      datetime(6)   NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间，应用不用维护',
    `update_time`      datetime(6)   NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间，应用不用维护',
    `channel_code`     varchar(64)   NOT NULL DEFAULT '' COMMENT '通道编码',
    `channel_id`       varchar(64)   NOT NULL DEFAULT '' COMMENT '渠道ID',
    `query_channel_id` varchar(64)            DEFAULT NULL COMMENT '调单渠道',
    `route_info`       varchar(2048) NOT NULL DEFAULT '' COMMENT '路由信息',
    `template_id`      varchar(64)   NOT NULL DEFAULT '' COMMENT '模板Id',
    `status`           varchar(32)   NOT NULL DEFAULT 'USED' COMMENT '数据状态USED、STOP',
    `remark`           varchar(1024)          DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `channel_route_data_uidx_01` (`channel_code`, `channel_id`),
    KEY `channel_route_data_idx_01` (`query_channel_id`),
    KEY `channel_route_data_idx_02` (`route_info`),
    KEY `channel_route_data_idx_03` (`template_id`),
    KEY `channel_route_data_create_time` (`create_time`),
    KEY `channel_route_data_update_time` (`update_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='通道信息表';

CREATE TABLE `template_config`
(
    `id`                         bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '自增物理主键',
    `create_time`                datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间，应用不用维护',
    `update_time`                datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间，应用不用维护',
    `template_id`                varchar(64) NOT NULL DEFAULT '' COMMENT '模版本ID',
    `request_head_context`       varchar(1024)        DEFAULT NULL COMMENT 'head模板',
    `request_body_context`       varchar(4000)        DEFAULT NULL COMMENT 'body模板',
    `request_foot_context`       varchar(1024)        DEFAULT NULL COMMENT 'foot模板',
    `request_head_format_type`   varchar(32)          DEFAULT NULL COMMENT 'head渲染类型',
    `request_body_format_type`   varchar(32)          DEFAULT NULL COMMENT 'body渲染类型',
    `request_foot_format_type`   varchar(32)          DEFAULT NULL COMMENT 'foot渲染类型',
    `response_head_convert_bean` varchar(64)          DEFAULT NULL COMMENT 'head转换bean',
    `response_body_convert_bean` varchar(64)          DEFAULT NULL COMMENT 'body转换bean',
    `response_foot_convert_bean` varchar(64)          DEFAULT NULL COMMENT 'foot转换bean',
    `result_context`             varchar(2048)        DEFAULT NULL COMMENT 'result转换模板',
    `result_convert_type`        varchar(32)          DEFAULT NULL COMMENT 'result转换类型',
    `seq_bean`                   varchar(64)          DEFAULT NULL COMMENT '请求银行流生成bean',
    `length`                     int(11)              DEFAULT NULL COMMENT '通用长度请求银行流生成长度',
    `prefix`                     varchar(8)           DEFAULT NULL COMMENT '通用长度请求银行流生成前缀',
    `suffix`                     varchar(8)           DEFAULT NULL COMMENT '通用长度请求银行流生成后缀',
    `invoke_bean`                varchar(64)          DEFAULT NULL COMMENT '请求bean',
    `status`                     varchar(32) NOT NULL DEFAULT 'USED' COMMENT '数据状态USED、STOP',
    PRIMARY KEY (`id`),
    UNIQUE KEY `template_config_uidx_01` (`template_id`),
    KEY `template_config_idx_01` (`invoke_bean`),
    KEY `template_config_create_time` (`create_time`),
    KEY `template_config_update_time` (`update_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='通道模板配置表';



