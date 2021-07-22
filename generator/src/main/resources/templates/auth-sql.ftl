INSERT INTO `sys_page`(`page_name`,`page_title`,`memo`)VALUES('[=lowerTableName]','[=tableTitle]','');

INSERT INTO `sys_fun`(`fun_no`,`page_name`,`action_type`,`action_no`,`action_name`)VALUES('[=lowerTableName]/l','[=lowerTableName]','l','l','[=tableTitle]列表');
INSERT INTO `sys_fun`(`fun_no`,`page_name`,`action_type`,`action_no`,`action_name`)VALUES('[=lowerTableName]/c','[=lowerTableName]','c','c','创建[=tableTitle]');
INSERT INTO `sys_fun`(`fun_no`,`page_name`,`action_type`,`action_no`,`action_name`)VALUES('[=lowerTableName]/r','[=lowerTableName]','r','r','[=tableTitle]详情');
INSERT INTO `sys_fun`(`fun_no`,`page_name`,`action_type`,`action_no`,`action_name`)VALUES('[=lowerTableName]/u','[=lowerTableName]','u','u','更新[=tableTitle]');
INSERT INTO `sys_fun`(`fun_no`,`page_name`,`action_type`,`action_no`,`action_name`)VALUES('[=lowerTableName]/d','[=lowerTableName]','d','d','删除[=tableTitle]');

INSERT INTO `sys_role_fun`(`role_no`,`fun_no`)VALUES('Z','[=lowerTableName]/l');
INSERT INTO `sys_role_fun`(`role_no`,`fun_no`)VALUES('Z','[=lowerTableName]/c');
INSERT INTO `sys_role_fun`(`role_no`,`fun_no`)VALUES('Z','[=lowerTableName]/r');
INSERT INTO `sys_role_fun`(`role_no`,`fun_no`)VALUES('Z','[=lowerTableName]/u');
INSERT INTO `sys_role_fun`(`role_no`,`fun_no`)VALUES('Z','[=lowerTableName]/d');