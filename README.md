# Simple NetworkDisk
多平台网盘工具，使用SpringBoot后端框架，网页前端使用vue框架，app端使用微信小程序框架，桌面端使用electron+vue+bootstrap技术栈完成（接口与网页端相同）

## 更新计划：
1、后端代码修改（1）：整合SpringSecurity、整合swaggerUI接口文档、整合ElasticSearch搜索

2、后端代码修改（2）：使用redis缓存（替代原有的全局变量）

3、暂停桌面端开发，优先开发网页端和后端接口

4、微信小程序端暂停开发
# 功能模块

 1、文件上传下载
 
 2、文件夹管理，新建文件夹、上传文件到文件夹、移动文件到文件夹、移动文件夹、重命名文件夹等子功能
 
 3、文件分享：链接密码分享、公开全网分享
 
 4、好友功能：好友文件传输、私信
 
 5、管理员平台
 
 # 说明
 ## 源码结构说明
 1、backend_new（稳定）: 新的后端源代码，采用springboot编写
 
 2、desktop（开发中）：桌面端前端代码，采用html+js+css，使用了npm、vue.js、axios、bootstrap等前端技术栈，通过electron打包成桌面应用
 
 3、src（旧版）: 旧后端代码（功能不齐全），未来将不再使用
 
 4、webcontent（过期，等待重构）: 网页端前端代码，采用html+js+css，使用了vue.js、axios、bootstrap搭建
 
 5、wechatapp（过期，等待重构）：微信小程序端，原生wxml和javascript开发，使用了weui界面库
 
 
