# hadoop2.4.1伪分布式集群搭建

### 0. 目录

[TOC]

> 首先配置一台。在安装好所有的步骤之后，在可以备份扩展机dataNode的时候，克隆一台母机备份。以便扩展（不能直接克隆dataNode机器稍加改造使用，否则会出现：你用该dataNode扩展的所有机器都只能被成功使用一台，其他的莫名其妙的就掉线）

### 1. 准备linux环境

#### 1.1 OS说明

使用的是centOS 7，虚拟机配好IP后操作都在SecureCRT上进行

```
uname -a 

Linux had01 2.6.32-358.el6.i686 #1 SMP Thu Feb 21 21:50:49 UTC 2013 i686 i686 i386 GNU/Linux 
```

#### 1.2 配置vm虚拟机网络连接方式

```
- VMware软件 -> My Computer -> 选中虚拟机 -> 右键 -> settings -> network adapter  -> 在"网络连接"中选择"自定义" -> VMnet8(NAT) -> OK

- 回到windows –> 打开网络和共享中心 -> 更改适配器设置 -> 右键VMnet8网络适配器 -> 属性 -> 双击IPv4 -> 设置虚拟网卡VMnet8的IP: 192.168.1.100 子网掩码: 255.255.255.0 默认网关: 192.168.1.1 首选DNS服务器: 192.168.1.1 -> 点击确定 

- VMware软件 -> Edit -> Virtual Network Editor -> 选择 VMnet8 -> 点击NAT Settings（设置VMnet8 虚拟交换机） -> Gateway IP: 192.168.1.1 
```

#### 1.3 配置用户名、主机名

- 新建了一个用户组和一个用户名：用户组：hadoop 用户名：hadoop
？

- 修改主机名称（每台机器都要修改该名称，方便记忆标识和hosts地址映射）

```
sudo vim /etc/sysconfig/network

    NETWORKING=yes
    HOSTNAME=hadoop101    ###
```

- 修改主机名称后不会立即生效，需要执行sudo hostname hadoop101,然后再exit重新登录

#### 1.4 把当前用户添加到sudoers

运行hadoop可以有权限运行root的权限命令(本人菜鸟，不知道这个具体有啥作用，配置之后的确好用得多，不用重复输入密码获取root权限)

```
通过su切换到root
vi /etc/sudoers 
## Allow root to run any commands anywhere 
root ALL=(ALL) ALL 
hadoop ALL=(ALL) ALL 
(该项配置大概在第27行)
```

#### 1.5 配置IP地址

两种方式：

- 第一种：通过Linux图形界面进行修改

```
进入Linux图形界面 -> 右键点击右上方的两个小电脑 -> 点击Edit connections -> 选中当前网络（当前系统使用的那一个） -> 点击edit按钮 -> 选择IPv4 -> method选择为manual -> 点击add按钮 -> 添加IP：192.168.1.101 子网掩码：255.255.255.0 网关：192.168.1.1 -> 域名服务器：192.168.1.1 -> apply
```
> 注：VMware的相关服务要启动

- 第二种：修改配置文件方式

```
        vim /etc/sysconfig/network-scripts/ifcfg-eth0
    
        DEVICE="eth0"
        BOOTPROTO="static"               ###
        HWADDR="00:0C:29:3C:BF:E7"
        IPV6INIT="yes"
        NM_CONTROLLED="yes"
        ONBOOT="yes"
        TYPE="Ethernet"
        UUID="ce22eeca-ecde-4536-8cc2-ef0dc36d4a8c"
        IPADDR="192.168.1.101"           ###
        NETMASK="255.255.255.0"          ###
        GATEWAY="192.168.1.1"            ###
```

改完配置文件后不会自动生效，必须重启Linux服务器(reboot),
或者重启network服务(sudo service network restart)

#### 1.6 修改主机名和IP的映射关系

```
sudo vim /etc/hosts

    192.168.1.101   hadoop101
    192.168.1.102   hadoop102
```

这里可以多配置几台，如果你打算配置5台来组成分布式集群，这里就映射5台
	
#### 1.7 关闭防火墙

```
    查看防火墙状态
    service iptables status
    #关闭防火墙
    service iptables stop
    #查看防火墙开机启动状态
    chkconfig iptables --list
    #关闭防火墙开机启动
    chkconfig iptables off
```
#### 1.8 修改linux启动模式

把图形界面修改为字符界面，节省内存 

```
sudo vi /etc/inittab 
id:5:initdefault   ###改为id:3:initdefault
```

#### 1.9 重启Linux

```
    reboot
```

### 2. 安装JDK

> 使用SecureCRT在windows中连接服务器操作

#### 2.1 上传jdk

- SecureCRT上传,按 alt+p 后出现sftp窗口，然后put d:\xxx\yy\ll\jdk-7u_65-i585.tar.gz
或者使用 filezilla 工具上传
或者直接在图形界面拖拽，解压
或者通过windows和虚拟机的共享文件夹传递

#### 2.1 解压jdk

> /home/hadoop/app 我们把所有的软件都安装在app下 

- 创建文件夹 

```
mkdir /home/hadoop/app 
```

- 解压

```
tar -zxvf jdk-7u55-linux-i586.tar.gz -C /home/hadoop/app
```

- 将java添加到环境变量中

```
    vim /etc/profile
    #在文件最后添加
    export JAVA_HOME=/home/hadoop/app/jdk1.7.0_65
    export PATH=$PATH:$JAVA_HOME/bin
    （这里也可以把hadoop的环境变量也配置了，以后安装hadoop的时候，就不需要再配置这里了）
    -------------------
    export JAVA_HOME=/home/hadoop/app/jdk1.7.0_65
    export HADOOP_HOME=/home/hadoop/app/hadoop-2.4.1
    export PATH=$PATH:$JAVA_HOME/bin:$HADOOP_HOME/bin:$HADOOP_HOME/sbin
    -------------------
    #刷新配置
    source /etc/profile
```

- 以上是环境搭建好了，可以备份一台母机，万一玩废了，还可以重新来过 
  克隆一台服务器，备份！

### 3. 安装hadoop2.4.1

- 先上传hadoop的安装包到服务器上去，解压到/home/hadoop/app目录下

> 注意：hadoop2.x的配置文件$HADOOP_HOME/etc/hadoop

- 伪分布式需要修改5个配置文件 
	配置文件中有默认的的配置参数，可以在官网查看详细信息，这里指修改，必须设置的参数，简单化让程序先运行起来 

> （rm -rf xxx/sss 删除文件或则文件夹） 

需要先进入到hadoop安装目录下的 etc/hadoop/ 中

#### hadoop-env.sh

```
vim hadoop-env.sh
    #第27行，修改成vim /etc/profile 中配置的java——home路径
    export JAVA_HOME=/home/hadoop/app/jdk1.7.0_65
```

##### core-site.xml

```
<!-- 指定HADOOP所使用的文件系统schema（URI），HDFS的老大（NameNode）的地址 -->
    <property>
        <name>fs.defaultFS</name>
        <value>hdfs://had01:9000</value>
    </property>
    <!-- 指定hadoop运行时产生文件的存储目录:要定义在当前用于的权限目录中 -->
    <property>
        <name>hadoop.tmp.dir</name>
        <value>/home/hadoop/hadoop-2.4.1/tmp</value>
    </property>
```

#### hdfs-site.xml
hdfs-default.xml

```
<!-- 指定HDFS副本的数量:上传文件备份的副本个数 -->
    <property>
        <name>dfs.replication</name>
        <value>2</value>
    </property>
```

#### mapred-site.xml

没有该文件的话，该目录下提供了一个模版文件，我们把该文件直接修改名称 

```
mv mapred-site.xml.template mapred-site.xml
```

```
<!-- 指定mr运行在yarn上 -->
    <property>
        <name>mapreduce.framework.name</name>
        <value>yarn</value>
    </property>
```


#### yarn-site.xml

```
<!-- 指定YARN的老大（ResourceManager）的地址 -->
    <property>
        <name>yarn.resourcemanager.hostname</name>
        <value>had01</value>
    </property>
<!-- reducer获取数据的方式 -->
    <property>
        <name>yarn.nodemanager.aux-services</name>
        <value>mapreduce_shuffle</value>
     </property>
```

#### 将hadoop添加到环境变量

```
vim /etc/proflie
    export JAVA_HOME=/home/hadoop/app/jdk1.7.0_65
    export HADOOP_HOME=/home/hadoop/app/hadoop-2.4.1
    export PATH=$PATH:$JAVA_HOME/bin:$HADOOP_HOME/bin:$HADOOP_HOME/sbin

source /etc/profile #刷新资源
```

> 最开始我们增加jdk环境变量的时候一次增加了hadoop环境变量，这一步就可以不增加了**

### 4. copy配置好的hadoop到dataNode节点 

> 或者把已经配置好的hadoop目录备份一份，如果操作了以下的，再复制到其他节点上就会出现各种错误

#### 复制文件到远程主机： 

```
scp -r hadoop-2.4.1/ hadoop@had002:/home/hadoop/app/
```

然后修改had002的主机名称，ip地址（之前备份的母机在这里就显示出作用了）其他的不用修改，重启等待

#### 扩展datenode

> 以下一大段话是出了问题的总结，可以先不看。跟着笔记走就行了

>>	以后要扩展datenode: 只需要备份一台，配置好的jdk环境变量、和hadoop环境变量(以后只要复制hadoop就不需要再修改环境变量了)、防火墙关闭、把当前用户的hadoop组加入root 
（sudo命令那个vi /etc/sudoers配置文件中）即可，直接在虚拟机克隆，然后把ip固定、hosts配置列表从master中复制一份或则手动输入关联，主机名称修改下 
再在master上启动hadoop hdfs服务即可 
如果出现了：http://master:50070/中看不到你所配置的datenode其他节点。则就是这里扩展（复制）的时候有问题。 
重新弄一个os系统。修改主机名称、关闭防火墙、当前组加入sudo配置文件、固定id、hosts列表映射、jdk，hadoop环境变量、再从其他已运行的datenode中把hadoop文件夹复制到统一的目录下即可。

#### 格式化namenode

> 是对namenode进行初始化，也就是在had01上面执行该命令

```
hdfs namenode -format (hadoop namenode -format)
```

> 格式化命令，只是在hadoop的tem目录下生成对应的节点目录，如果遇到启动有问题的，可以尝试删除tem目录下的 nameNode 和 dateNode目录，重新格式化

#### 启动hadoop

- 启动之前最好先把免验证登录设置了 

> 自己在配置的过程中，就出现了手动输入密码，但是还卡着不动，配置了免验证密码登录之后，再启动就正常了 

```
	配置需要自动启动的data节点 
（cd etc/hadoop/）：vi slaves 
	把需要启动dataNode进程机器的主机名或则ip地址加入该文件列表，每行一个。 
sbin/start-dfs.sh

	YARN（资源调度集群框架,服务于mapreduce等运算框架）
sbin/start-yarn.sh
```

- 验证是否启动成功

```
使用jps命令验证 
27408 NameNode 
28218 Jps 
27643 SecondaryNameNode 
28066 NodeManager 
27803 ResourceManager 
27512 DataNode
```

> [HDFS管理界面，启动hdfs](http://192.168.1.101:50070)

> [MR管理界面,启动YARN才能打开该页面](http://192.168.1.101:8088 )

- 配置ssh免登陆

> 要全部配置了免密码登录，连master（本机）也要配置后，才会正常的被启动（实测是这样不会出现什么异常），本机配置之后，不需要输入密码

1. 生成ssh免登陆密钥
	
	进入到我的home目录 
	cd ~/.ssh 
	ssh-keygen -t rsa 
	（四个回车）执行完这个命令后，会生成两个文件id_rsa（私钥）、id_rsa.pub（公钥） 
	
	2. 将公钥拷贝到要免密登陆的目标机器上
	
	> 比如：要在100，上免密码登录101，那么就在100上面操作，然后复制到101上面
	> 也就是说：如果要在master上面免密码启动其他date节点：就要在master上面操作，然后复制到其他节点上面 
	> ssh-copy-id localhost #localhost 是主机名称或则ip地址
	
	3. ssh密钥验证原理图 




### 使用HDFS shell测试效果
#### 查看帮助 
hadoop fs -help //cmd
#### 上传 
hadoop fs -put (linux上文件) (hdfs上的路径) 
hadoop fs -put /home/hadoop/insall/Ghost_XP_IE8_CJ_V2015.02.iso hdfs://had01:9000/Ghost_XP_IE8_CJ_V2015.02.iso
#### 查看文件内容 
hadoop fs -cat (hdfs上的路径)
查看文件列表 
hadoop fs -ls /
下载文件 
hadoop fs -get (hdfs上的路径) (linux上文件)
删除文件 
hadoop dfs -rm xxx

删除目录与目录下所有文件 
hadoop dfs -rmr /user/cl/temp


