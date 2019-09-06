#!/bin/bash

startSuccessOrNot(){
	if [ $2 -eq 0 ]; then
		echo "$1 start success!"
	else
		echo "$1 start fail!"
		exit 1
	fi
}

submitFlinkTask(){
	TaskName=$1
	echo ""
	echo "starting $TaskName......"
	count=`ps -fe | grep $TaskName | grep -v "grep"| wc -l`
	if [ $count -eq 0 ];then
		nohup /opt/module/flink-1.7.2/bin/flink run -c com.hinoc.monitor.dataprocess.$TaskName /mnt/hgfs/share/DataProcessFlink/data_process_flink-1.0-jar-with-dependencies.jar -bootstrap-server hadoop201:9092 >/dev/null 2>&1 &
		startSuccessOrNot $TaskName $?
	else
		echo "$TaskName already exists!"
	fi
}

#start zookeeper
echo "starting zookeeper......"
count=`ps -fe | grep "zookeeper" | grep -v "grep"| wc -l`
if [ $count -eq 0 ];then
	nohup /opt/module/zookeeper-3.4.10/bin/zkServer.sh start 1>/dev/null 2>&1 &
	startSuccessOrNot "zookeeper" $?
else
	echo "zookeeper already exists!"
fi

sleep 5

#start kafka
echo ""
echo "starting kafka......"
count=`ps -fe | grep "kafka" | grep -v "grep"| wc -l`
if [ $count -eq 0 ];then
	nohup /opt/module/kafka_2.12-2.2.0/bin/kafka-server-start.sh /opt/module/kafka_2.12-2.2.0/config/server.properties 1>/dev/null 2>&1 & 
	startSuccessOrNot "kafka" $?
else
	echo "kafka already exists!"
fi

sleep 5

#start flink
echo ""
echo "starting flink......"
count=`ps -fe | grep "flink" | grep -v "grep" | grep -v "start-all-flink.sh" | wc -l`
if [ $count -eq 0 ];then
	nohup /opt/module/flink-1.7.2/bin/start-cluster.sh 1>/dev/null 2>&1 & 
	startSuccessOrNot flink $?
	echo "starting TaskManagerRunner......"
	slaves=`cat /opt/module/flink-1.7.2/conf/slaves | wc -l`
	tasks=`jps | grep -c TaskManagerRunner`
	while [ $slaves -gt $tasks ]
	do
		tasks=`jps | grep -c TaskManagerRunner`
	done
	echo "$tasks TaskManagerRunner start success!"
else
	echo "flink already exists!"
fi
sleep 10

#start WebServer
echo ""
echo "starting WebServer......"
count=`ps -fe | grep "app_namespace.py" | grep -v "grep"| wc -l`
if [ $count -eq 0 ];then
	nohup python27 /opt/PycharmProjects/WebServer/app_namespace.py 1>/dev/null 2>&1 & 
	startSuccessOrNot "WebServer" $?
else
	echo "WebServer already exists!"
fi

sleep 5

#submit data processs task
submitFlinkTask EocCltMacRate
sleep 10

submitFlinkTask EocCltMapBitRate
sleep 10

submitFlinkTask EocDevNeedRate
sleep 10

submitFlinkTask EocDevRxPower
sleep 10

submitFlinkTask HinocStatistics
sleep 10

submitFlinkTask EocDevRxSNR
sleep 10

submitFlinkTask EocDevChanInfo
sleep 5