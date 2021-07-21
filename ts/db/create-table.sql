CREATE TABLE `sys_page` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `page_name` VARCHAR(50) NOT NULL DEFAULT '',
    `page_title` VARCHAR(50) NOT NULL DEFAULT '',
    `memo` VARCHAR(500) NULL,
    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `sys_fun` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `fun_no` VARCHAR(80) NOT NULL DEFAULT '' COMMENT '非输入值，等于page_name/action_no',
    `page_name` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '关联sys_page表page_name',
    `action_type` CHAR(1) NOT NULL DEFAULT 'l' COMMENT 'l-列表；c-创建；r-详情；u-更新；d-删除；e-其他',
    `action_no` VARCHAR(30) NOT NULL DEFAULT '',
    `action_name` VARCHAR(50) NOT NULL DEFAULT '',
    `memo` VARCHAR(500) NULL,
    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `sys_role` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `role_no` CHAR(1) NOT NULL DEFAULT '',
    `role_name` VARCHAR(20) NOT NULL DEFAULT '',
    `memo` VARCHAR(500) NULL,
    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE `sys_role_fun` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `role_no` CHAR(1) NOT NULL DEFAULT '',
    `fun_no` VARCHAR(80) NOT NULL DEFAULT '',
    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE `user` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `user_name` VARCHAR(50) NOT NULL DEFAULT '',
    `avatar` VARCHAR(200) NULL,
    `gender` CHAR(1) NULL COMMENT '0-未提供；1-男；2-女',
    `birthday` VARCHAR(10) NULL COMMENT '格式：yyyy-MM-dd',
    `phone` VARCHAR(20) NOT NULL DEFAULT '',
    `password` VARCHAR(100) NOT NULL DEFAULT '',
    `roles` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '多个角色使用分号连接',
    `status` CHAR(1) NOT NULL DEFAULT '1' COMMENT '0-禁用；1-启用',
    PRIMARY KEY (`id`),
    INDEX `i_staff_0` (`phone`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE `file_upload` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `table_name` VARCHAR(50) NOT NULL DEFAULT '',
    `row_id` BIGINT(20) NOT NULL DEFAULT -1,
    `full_path` VARCHAR(500) NOT NULL DEFAULT '',
    `file_name` VARCHAR(200) NOT NULL DEFAULT '',
    `delete_status` CHAR(1) NOT NULL DEFAULT '0' COMMENT '0-未删除；1-逻辑删除；2-物理删除',
    PRIMARY KEY (`id`),
    INDEX `i_file_upload_0` (`table_name` , `row_id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

