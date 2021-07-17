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
- 用于运行scaffold工程，只包含一个SpringBootApplication和相关配置。
- 创建新项目时可以直接拷贝ts


## 三、generator
用于生成crud代码的工程。实现中。
