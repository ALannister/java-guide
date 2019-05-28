# windows下写的脚本拷贝到linux执行报错

### 现象

```
[hinoc@hadoop201 module]$ chmod +x start-all.sh 
[hinoc@hadoop201 module]$ ./start-all.sh 
-bash: ./start-all.sh: /bin/bash^M: bad interpreter: No such file or directory
```

### 解决

```
[hinoc@hadoop201 module]$ vi start-all.sh
在命令模式下执行
:set ff=unix
:wq
[hinoc@hadoop201 module]$ ./start-all.sh 
starting zookeeper......
zookeeper already exists!

starting kafka......
kafka already exists!

starting flink......
flink already exists!
[hinoc@hadoop201 module]$ 
```

脚本成功执行！