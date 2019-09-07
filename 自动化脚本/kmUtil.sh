#!/bin/bash
host=hadoop103

if [ "$1" == "start" ];then
	ssh $host "source /etc/profile;/opt/module/kafka-manager-2.0.0.2/bin/kafka-manager -Dconfig.file=/opt/module/kafka-manager-2.0.0.2/conf/application.conf -Dhttp.port=8888 1>/dev/null 2>&1 &"
	echo "start kafka-manager at $host"
else
	ssh $host "source /etc/profile;ps -ef | grep "kafka-manager" | grep -v grep | awk '{print $2}' | xargs kill -15 1>/dev/null 2>&1"
	echo "stop kafka-manager at $host"
fi