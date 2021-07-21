INSERT INTO `sys_page`(`page_name`,`page_title`,`memo`)VALUES('user','用户','');
INSERT INTO `sys_page`(`page_name`,`page_title`,`memo`)VALUES('file','上传文件','');
INSERT INTO `sys_page`(`page_name`,`page_title`,`memo`)VALUES('syspage','模块','');
INSERT INTO `sys_page`(`page_name`,`page_title`,`memo`)VALUES('sysfun','功能','');
INSERT INTO `sys_page`(`page_name`,`page_title`,`memo`)VALUES('sysrole','角色','');
INSERT INTO `sys_page`(`page_name`,`page_title`,`memo`)VALUES('sysrolefun','角色与功能','');


INSERT INTO `sys_role`(`role_no`,`role_name`)VALUES('Z','系统管理员');


INSERT INTO `sys_fun`(`fun_no`,`page_name`,`action_type`,`action_no`,`action_name`)VALUES('user/l','user','l','l','用户列表');
INSERT INTO `sys_fun`(`fun_no`,`page_name`,`action_type`,`action_no`,`action_name`)VALUES('user/c','user','c','c','创建用户');
INSERT INTO `sys_fun`(`fun_no`,`page_name`,`action_type`,`action_no`,`action_name`)VALUES('user/r','user','r','r','用户详情');
INSERT INTO `sys_fun`(`fun_no`,`page_name`,`action_type`,`action_no`,`action_name`)VALUES('user/u','user','u','u','更新用户');
INSERT INTO `sys_fun`(`fun_no`,`page_name`,`action_type`,`action_no`,`action_name`)VALUES('user/d','user','d','d','删除用户');
INSERT INTO `sys_fun`(`fun_no`,`page_name`,`action_type`,`action_no`,`action_name`)VALUES('file/upload','file','e','upload','上传文件');
INSERT INTO `sys_fun`(`fun_no`,`page_name`,`action_type`,`action_no`,`action_name`)VALUES('syspage/l','syspage','l','l','模块列表');
INSERT INTO `sys_fun`(`fun_no`,`page_name`,`action_type`,`action_no`,`action_name`)VALUES('syspage/c','syspage','c','c','创建模块');
INSERT INTO `sys_fun`(`fun_no`,`page_name`,`action_type`,`action_no`,`action_name`)VALUES('syspage/r','syspage','r','r','模块详情');
INSERT INTO `sys_fun`(`fun_no`,`page_name`,`action_type`,`action_no`,`action_name`)VALUES('syspage/u','syspage','u','u','更新模块');
INSERT INTO `sys_fun`(`fun_no`,`page_name`,`action_type`,`action_no`,`action_name`)VALUES('syspage/d','syspage','d','d','删除模块');
INSERT INTO `sys_fun`(`fun_no`,`page_name`,`action_type`,`action_no`,`action_name`)VALUES('sysfun/l','sysfun','l','l','功能列表');
INSERT INTO `sys_fun`(`fun_no`,`page_name`,`action_type`,`action_no`,`action_name`)VALUES('sysfun/c','sysfun','c','c','创建功能');
INSERT INTO `sys_fun`(`fun_no`,`page_name`,`action_type`,`action_no`,`action_name`)VALUES('sysfun/r','sysfun','r','r','功能详情');
INSERT INTO `sys_fun`(`fun_no`,`page_name`,`action_type`,`action_no`,`action_name`)VALUES('sysfun/u','sysfun','u','u','更新功能');
INSERT INTO `sys_fun`(`fun_no`,`page_name`,`action_type`,`action_no`,`action_name`)VALUES('sysfun/d','sysfun','d','d','删除功能');
INSERT INTO `sys_fun`(`fun_no`,`page_name`,`action_type`,`action_no`,`action_name`)VALUES('sysrole/l','sysrole','l','l','角色列表');
INSERT INTO `sys_fun`(`fun_no`,`page_name`,`action_type`,`action_no`,`action_name`)VALUES('sysrole/c','sysrole','c','c','创建角色');
INSERT INTO `sys_fun`(`fun_no`,`page_name`,`action_type`,`action_no`,`action_name`)VALUES('sysrole/r','sysrole','r','r','角色详情');
INSERT INTO `sys_fun`(`fun_no`,`page_name`,`action_type`,`action_no`,`action_name`)VALUES('sysrole/u','sysrole','u','u','更新角色');
INSERT INTO `sys_fun`(`fun_no`,`page_name`,`action_type`,`action_no`,`action_name`)VALUES('sysrole/d','sysrole','d','d','删除角色');
INSERT INTO `sys_fun`(`fun_no`,`page_name`,`action_type`,`action_no`,`action_name`)VALUES('sysrole/gbuk','sysrole','e','gbuk','角色详情-gbuk');
INSERT INTO `sys_fun`(`fun_no`,`page_name`,`action_type`,`action_no`,`action_name`,`memo`)VALUES('sysrolefun/l','sysrole','l','l','角色权限查询','查询某个角色可以访问的功能');
INSERT INTO `sys_fun`(`fun_no`,`page_name`,`action_type`,`action_no`,`action_name`,`memo`)VALUES('sysrolefun/c','sysrole','c','d','赋予权限','赋予某个角色访问某个功能的权限');
INSERT INTO `sys_fun`(`fun_no`,`page_name`,`action_type`,`action_no`,`action_name`,`memo`)VALUES('sysrolefun/d','sysrole','c','d','删除权限','取消某个角色访问某个功能的权限');


INSERT INTO `sys_role_fun`(`role_no`,`fun_no`)VALUES('Z','user/l');
INSERT INTO `sys_role_fun`(`role_no`,`fun_no`)VALUES('Z','user/c');
INSERT INTO `sys_role_fun`(`role_no`,`fun_no`)VALUES('Z','user/r');
INSERT INTO `sys_role_fun`(`role_no`,`fun_no`)VALUES('Z','user/u');
INSERT INTO `sys_role_fun`(`role_no`,`fun_no`)VALUES('Z','user/d');
INSERT INTO `sys_role_fun`(`role_no`,`fun_no`)VALUES('Z','syspage/l');
INSERT INTO `sys_role_fun`(`role_no`,`fun_no`)VALUES('Z','syspage/c');
INSERT INTO `sys_role_fun`(`role_no`,`fun_no`)VALUES('Z','syspage/r');
INSERT INTO `sys_role_fun`(`role_no`,`fun_no`)VALUES('Z','syspage/u');
INSERT INTO `sys_role_fun`(`role_no`,`fun_no`)VALUES('Z','syspage/d');
INSERT INTO `sys_role_fun`(`role_no`,`fun_no`)VALUES('Z','sysfun/l');
INSERT INTO `sys_role_fun`(`role_no`,`fun_no`)VALUES('Z','sysfun/c');
INSERT INTO `sys_role_fun`(`role_no`,`fun_no`)VALUES('Z','sysfun/r');
INSERT INTO `sys_role_fun`(`role_no`,`fun_no`)VALUES('Z','sysfun/u');
INSERT INTO `sys_role_fun`(`role_no`,`fun_no`)VALUES('Z','sysfun/d');
INSERT INTO `sys_role_fun`(`role_no`,`fun_no`)VALUES('Z','sysrole/l');
INSERT INTO `sys_role_fun`(`role_no`,`fun_no`)VALUES('Z','sysrole/c');
INSERT INTO `sys_role_fun`(`role_no`,`fun_no`)VALUES('Z','sysrole/r');
INSERT INTO `sys_role_fun`(`role_no`,`fun_no`)VALUES('Z','sysrole/u');
INSERT INTO `sys_role_fun`(`role_no`,`fun_no`)VALUES('Z','sysrole/d');
INSERT INTO `sys_role_fun`(`role_no`,`fun_no`)VALUES('Z','sysrole/gbuk');
INSERT INTO `sys_role_fun`(`role_no`,`fun_no`)VALUES('Z','sysrolefun/l');
INSERT INTO `sys_role_fun`(`role_no`,`fun_no`)VALUES('Z','sysrolefun/c');
INSERT INTO `sys_role_fun`(`role_no`,`fun_no`)VALUES('Z','sysrolefun/d');
INSERT INTO `sys_role_fun`(`role_no`,`fun_no`)VALUES('Z','file/upload');

INSERT INTO `user`(`user_name`,`phone`,`password`,`roles`,`status`)VALUES('系统初始化管理员','13999999999','123456','Z','1');