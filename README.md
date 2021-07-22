# Metoo
做了几个个人项目，在Metoo中总结一下。主要由3个工程组成：常用代码工程（scaffold）、空工程（ts）、代码生成工程（generator）。


## 一、scaffold
一个Spring Boot工程，为前端提供api接口。  
使用到了：
-  mybatis
-  pagehelper
-  Spring Cache + Redis，以及jedis用于指定TTL的缓存
-  jjwt
-  druid
-  fastjson、easyexcel、commons-beanutils、commons-lang3、lombok

### 工程内容
-  工具类
-  api调用参数、结果封装
-  filter
-  异常处理
-  后台权限管理，包括：模块、功能、角色、角色与功能、用户

### 打包、引用
使用maven-compiler-plugin打包install本地mvn库，在其他项目中通过pom依赖引入。


## 二、ts
用于验证scaffold工程，创建新项目时可以直接拷贝ts
- 后端：Spring Boot工程，提供api接口
- 前端：使用了[PanJiaChen/vue-element-admin](https://github.com/PanJiaChen/vue-element-admin)，代码在html目录下
- 数据库使用mysql8

### 拷贝后的html需要修改
1. 系统名称：src/setting.js
2. 图标：dist/中的favicon.ico和logo.png
3. 后端api访问地址：.env.development

### 拷贝后的Java工程需要修改
1. 修改pom文件的说明
2. 修改包定义
3. 修改SpringBootApplication启动文件
4. 修改properties中的端口、数据库链接等配置

## 三、generator
用于生成crud代码的工程。由2部分组成：
- 后端api接口
- 前端静态页面，在html目录，直接浏览器打开即可

### 自动生成方式
从information_schema的TABLES、COLUMNS、TABLE_CONSTRAINTS、KEY_COLUMN_USAGE表中，读取数据表定义信息，使用freemarker模板，生成mybatis mapper xml、mapper、model、service、service impl、controller，以及前端的view、api，用于满足scaffold权限需要的初始化sql。

### 生成代码使用
1. 拷贝文件到ts工程相应路径
2. 前端手动添加路由
3. 表太多的时候，前端需要考虑按模块组织api、view

### 代码运行
1. 后端使用run java application方式运行SpringBootApplication类，方便debug
2. 前端在html目录下执行
```
npm install
npm run dev
```

### 注意事项
1. 数据表结构定义时，需要增加注释，生成代码时会使用表、字段的注释作为名称。
2. unique key，目前只取了1个，做数据唯一性检查
