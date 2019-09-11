1.统计连接某服务器端口（TCP）最多的IP地址列表

``` 
netstat -nat | grep "192.168.1.101:9092" | awk '{print $5}' | awk -F : '{print $4}' | sort | uniq -c | sort -nr | head -20
```
2.统计TCP状态列表

```
netstat -nat | awk '{print $6}' | sort | uniq -c | sort -nr | head -20
```
3.统计指定进程使用的端口列表
```
netstat -anp | grep 9811
```

4.统计指定进程使用的监听端口列表

```
netstat -lnp | grep 9811
```
5.查找使用指定端口的进程

```
netstat -nap | grep 9092 | awk '{print $7}' | sort | uniq 
```