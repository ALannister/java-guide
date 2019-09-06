#!/bin/bash

startSuccessOrNot(){
	if [ $2 -eq 0 ]; then
		echo "$1 start success!"
	else
		echo "$1 start fail!"
		exit 1
	fi
}

#start zookeeper
echo "starting zookeeper......"
count=`ps -fe | grep "zookeeper" | grep -v "grep"| wc -l`
if [ $count -eq 0 ];then
	nohup /opt/module/zookeeper-3.4.10/bin/zkServer.sh start 1>/dev/null 2>&1 &
	startSuccessOrNot "zookeeper" $?
	sleep 10
else
	echo "zookeeper already exists!"
fi



#start kafka
echo ""
echo "starting kafka......"
count=`ps -fe | grep "kafka" | grep -v "grep"| wc -l`
if [ $count -eq 0 ];then
	nohup /opt/module/kafka_2.12-2.2.0/bin/kafka-server-start.sh /opt/module/kafka_2.12-2.2.0/config/server.properties 1>/dev/null 2>&1 & 
	startSuccessOrNot "kafka" $?
	sleep 15
else
	echo "kafka already exists!"
fi


#start DataProcessPython
echo ""
echo "starting DataProcessPython......"
count=`ps -fe | grep "data_streaming.py" | grep -v "grep"| wc -l`
if [ $count -eq 0 ];then
	nohup python27 /opt/PycharmProjects/DataProcessPython/data_streaming.py 1>/dev/null 2>&1 & 
	echo "DataProcessPython start success!"
	sleep 10
else
	echo "DataProcessPython already exists!"
fi



#start WebServer
echo ""
echo "starting WebServer......"
count=`ps -fe | grep "app_namespace.py" | grep -v "grep"| wc -l`
if [ $count -eq 0 ];then
	nohup python27 /opt/PycharmProjects/WebServer/app_namespace.py 1>/dev/null 2>&1 & 
	startSuccessOrNot "WebServer" $?
	sleep 10
else
	echo "WebServer already exists!"
fi

