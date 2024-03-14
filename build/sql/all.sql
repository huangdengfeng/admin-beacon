CREATE DATABASE IF NOT EXISTS `admin-beacon` DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
use `admin-beacon`;

DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(45) NOT NULL COMMENT '权限编码',
  `name` varchar(45) NOT NULL COMMENT '名称',
  `parent_id` int DEFAULT NULL COMMENT '父ID',
  `parent_ids` varchar(200) DEFAULT NULL COMMENT '所有父节点',
  `status` tinyint NOT NULL COMMENT '状态',
  `create_user` int NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` int NOT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'role id',
  `code` varchar(45) NOT NULL COMMENT '角色编码',
  `name` varchar(45) NOT NULL COMMENT '角色名称',
  `status` tinyint NOT NULL COMMENT '状态',
  `create_user` int NOT NULL,
  `create_time` datetime NOT NULL,
  `update_user` int NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_code_UNIQUE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `role_id` int NOT NULL COMMENT '角色ID',
  `permission_id` int NOT NULL COMMENT '权限ID',
  `create_user` int NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_user` (
  `uid` int NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(50) NOT NULL COMMENT 'username ',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `secret_key` varchar(32) NOT NULL COMMENT '安全密钥',
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `photo` varchar(100) DEFAULT NULL COMMENT '照片',
  `status` tinyint NOT NULL COMMENT '状态：1.正常;2.停用;3.锁定',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user` int NOT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_user` int NOT NULL COMMENT '更新用户',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `uid` int NOT NULL,
  `role_id` int NOT NULL,
  `create_user` int NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`uid`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `sys_user` VALUES (1,'admin','$2a$10$hTbIfacFP2Vl0EX.qmQBB.WI/zESwGc2WiMOnykfBzOoiiKIGSGCa','NYivApKMepQQLYul5xGegsUQA440ox4h','管理员',NULL,NULL,NULL,1,'2023-08-27 09:16:23',1,'2023-10-20 22:55:18',1,NULL);


# 初始权限脚本
INSERT INTO `sys_permission` (`code`,`name`,`parent_id`,`parent_ids`,`status`,`create_user`,`create_time`,`update_user`,`update_time`)
VALUES ('sys:user','用户管理',NULL,NULL,1,1,now(),1,now());

set @parentId = (SELECT MAX(id) FROM sys_permission);

INSERT INTO `sys_permission` (`code`,`name`,`parent_id`,`parent_ids`,`status`,`create_user`,`create_time`,`update_user`,`update_time`)
VALUES ('sys:user:qry','用户查询',@parentId,@parentId,1,1,now(),1,now());
INSERT INTO `sys_permission` (`code`,`name`,`parent_id`,`parent_ids`,`status`,`create_user`,`create_time`,`update_user`,`update_time`)
VALUES ('sys:user:add','添加',@parentId,@parentId,1,1,now(),1,now());
INSERT INTO `sys_permission` (`code`,`name`,`parent_id`,`parent_ids`,`status`,`create_user`,`create_time`,`update_user`,`update_time`)
VALUES ('sys:user:modify','修改用户',@parentId,@parentId,1,1,now(),1,now());
INSERT INTO `sys_permission` (`code`,`name`,`parent_id`,`parent_ids`,`status`,`create_user`,`create_time`,`update_user`,`update_time`)
VALUES ('sys:user:modify_pwd','修改用户密码',@parentId,@parentId,1,1,now(),1,now());

INSERT INTO `sys_permission` (`code`,`name`,`parent_id`,`parent_ids`,`status`,`create_user`,`create_time`,`update_user`,`update_time`)
VALUES ('sys:role','角色管理',NULL,NULL,1,1,now(),1,now());

set @parentId = (SELECT MAX(id) FROM sys_permission);

INSERT INTO `sys_permission` (`code`,`name`,`parent_id`,`parent_ids`,`status`,`create_user`,`create_time`,`update_user`,`update_time`)
VALUES ('sys:role:add','添加',@parentId,@parentId,1,1,now(),1,now());
INSERT INTO `sys_permission` (`code`,`name`,`parent_id`,`parent_ids`,`status`,`create_user`,`create_time`,`update_user`,`update_time`)
VALUES ('sys:role:modify','修改',@parentId,@parentId,1,1,now(),1,now());
INSERT INTO `sys_permission` (`code`,`name`,`parent_id`,`parent_ids`,`status`,`create_user`,`create_time`,`update_user`,`update_time`)
VALUES ('sys:role:delete','删除',@parentId,@parentId,1,1,now(),1,now());


INSERT INTO `sys_permission` (`code`,`name`,`parent_id`,`parent_ids`,`status`,`create_user`,`create_time`,`update_user`,`update_time`)
VALUES ('sys:permission','权限管理',NULL,NULL,1,1,now(),1,now());

set @parentId = (SELECT MAX(id) FROM sys_permission);

INSERT INTO `sys_permission` (`code`,`name`,`parent_id`,`parent_ids`,`status`,`create_user`,`create_time`,`update_user`,`update_time`)
VALUES ('sys:permission:add','添加',@parentId,@parentId,1,1,now(),1,now());
INSERT INTO `sys_permission` (`code`,`name`,`parent_id`,`parent_ids`,`status`,`create_user`,`create_time`,`update_user`,`update_time`)
VALUES ('sys:permission:modify','修改',@parentId,@parentId,1,1,now(),1,now());
INSERT INTO `sys_permission` (`code`,`name`,`parent_id`,`parent_ids`,`status`,`create_user`,`create_time`,`update_user`,`update_time`)
VALUES ('sys:permission:delete','删除',@parentId,@parentId,1,1,now(),1,now());

