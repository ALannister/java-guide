# MySQL的架构介绍

### 目录

[TOC]

### 1 MySQL简介

#### 1.1 概述

- MySQL是一个关系型数据库管理系统，由瑞典MySQL AB公司开发，目前属于Oracle公司。
- MySQL是一种关联数据库管理系统，将数据保存在不同的表中，而不是将所有数据放在一个大仓库内，这样就增加了速度并提高了灵活性。
- MySQL是开源的，所以你不需要支付额外的费用。
- MySQL是可以定制的，采用了GPL协议，你可以修改源码来开发自己的Mysql系统。
- MySQL支持大型的数据库。可以处理拥有上千万条记录的大型数据库。
- MySQL使用标准的SQL数据语言形式。
- MySQL可以允许于多个系统上，并且支持多种语言。这些编程语言包括C、C++、Python、Java、Perl、PHP、Eiffel、Ruby和Tcl等。
- MySQL支持大型数据库，支持5000万条记录的数据仓库，32位系统表文件最大可支持4GB，64位系统支持最大的表文件为8TB。

#### 1.2 高级MySQL

- MySQL内核

- SQL优化工程师

- MySQL服务器的优化

- 各种参数常量设定

- 查询语句优化

- 主从复制

- 软硬件升级

- 容灾备份

- SQL编程

注：完整的MySQL优化需要很深的功底，大公司甚至有专门的DBA写上述

### 2 MySQL Linux版的安装（MySQL 5.5）

#### 2.1 下载地址

官网下载地址：http://dev.mysql.com/downloads/mysql/

#### 2.2 检查工作 

- 检查当前系统是否安装过MySQL

   执行安装命令前，先执行查询命令
  `rpm -qa|grep mysql`
  如果存在mysql-libs的旧版本包如下：
  `￼￼￼myql-libs-5.1.73.el6.x86_64`
  请先执行卸载命令：`rpm -e --nodeps  mysql-libs`

- 检查/tmp文件夹权限

  由于mysql安装过程中，会通过mysql用户在/tmp目录下新建tmp_db文件，所以请给/tmp较大的权限
  执行 ：`chmod -R 777 /tmp`

#### 2.4 安装

```
在mysql的安装文件目录下执行：
rpm -ivh MySQL-server-5.5.54-1.linux2.6.x86_64.rpm
rpm -ivh MySQL-client-5.5.54-1.linux2.6.x86_64.rpm
```

#### 2.5 查看MySQL安装版本



