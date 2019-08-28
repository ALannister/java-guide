# MySQL 里设置或修改系统变量的几种方法

比如设置[MySQL](http://lib.csdn.net/base/mysql)实例参数wait_timeout为10秒.

 

### 1 设置全局变量方法1(不推荐): 修改参数文件, 然后重启mysqld
```
# vi /etc/my.cnf

[mysqld]

wait_timeout=10

# service mysqld restart
```
不过这个方法太生硬了, 线上服务重启无论如何都应该尽可能避免.

 **使用此方法对global全局变量的设置重启mysqld后才是有效的**

### 2 设置全局变量方法2(推荐): 在命令行里通过SET来设置, 然后再修改参数文件

如果要修改全局变量, 必须要显示指定"GLOBAL"或者"@@global.", 同时必须要有SUPER权限. 
```
mysql> set global wait_timeout=10;

or

mysql> set @@global.wait_timeout=10;
```


然后查看设置是否成功:
```
mysql> select @@global.wait_timeout=10;

or

mysql> show global variables like 'wait_timeout';

+---------------+-------+

| Variable_name | Value |

+---------------+-------+

| wait_timeout  | 10    | 

+---------------+-------+
```
**需要注意的是，使用此方法对global全局变量的设置仅对于新开启的会话才是有效的，对已经开启的会话不生效。**

如果查询时使用的是show variables的话, 会发现设置并没有生效, 除非重新登录再查看。这是因为使用show variables的话就等同于使用show session variables, 查询的是会话变量, 只有使用show global variables查询的才是全局变量. 如果仅仅想修改会话变量的话, 可以使用类似set wait_timeout=10;或者set session wait_timeout=10;这样的语法. 

当前只修改了正在运行的MySQL实例参数, 但下次重启mysqld又会回到默认值, 所以别忘了修改参数文件:
```
# vi /etc/my.cnf

[mysqld]

wait_timeout=10
```


### 3 设置会话变量方法: 在命令行里通过SET来设置

如果要修改会话变量值, 可以指定"SESSION"或者"@@session."或者"@@"或者"LOCAL"或者"@@local.", 或者什么都不使用. 
```
mysql> set wait_timeout=10;

or

mysql> set session wait_timeout=10;

or

mysql> set local wait_timeout=10;

or

mysql> set @@wait_timeout=10;

or

mysql> set @@session.wait_timeout=10;

or

mysql> set @@local.wait_timeout=10;
```


然后查看设置是否成功:
```
mysql> select @@wait_timeout;

or

mysql> select @@session.wait_timeout;

or

mysql> select @@local.wait_timeout;

or

mysql> show variables like 'wait_timeout';

or

mysql> show local variables like 'wait_timeout';

or

mysql> show session variables like 'wait_timeout';

+---------------+-------+

| Variable_name | Value |

+---------------+-------+

| wait_timeout  | 10    | 

+---------------+-------+
```
 **修改session变量配置，仅仅是对本session的变量配置有效，对于其他session无效。 **

###  4 会话变量和全局变量转换方法: 在命令行里通过SET来设置

将会话变量值设置为对应的全局变量值呢:
```
mysql> set @@session.wait_timeout=@@global.wait_timeout;
```
将会话变量值设置为MySQL编译时的默认值(wait_timeout=28800):
```
mysql> set wait_timeout=DEFAULT;
```
这里要注意的是, 并不是所有的系统变量都能被设置为DEFAULT, 如果设置这些变量为DEFAULT则会返回错误. 

### 出处：
http://blog.csdn.net/leesmn/article/details/10211253