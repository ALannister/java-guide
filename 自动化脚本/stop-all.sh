#!/bin/bash

killProcessTerm(){
	echo ""
	echo "stopping $1......"
	count=`ps -fe | grep $1 | grep -v "grep"| wc -l`
	if [ $count -eq 0 ];then
		echo "$1 is not running!"
	else
		ps -ef | grep $1 | grep -v grep | awk '{print $2}' | xargs kill -15
		echo "$1 stop success!"
		sleep 5
	fi
}

killProcess(){
	count=`ps -fe | grep $1 | grep -v "grep"| wc -l`
	if [ $count -ne 0 ];then
		ps -ef | grep $1 | grep -v grep | awk '{print $2}' | xargs kill -9
		echo "kill $1 whith kill -9"
	fi
}

#stop WebServer
killProcessTerm "app_namespace.py"
killProcess "app_namespace.py"

#stop DataProcessPython
killProcessTerm "data_streaming.py"
killProcess "data_streaming.py"

#stop DataProcessFlink
killProcessTerm "flink"
killProcess "flink"

#stop kafka
killProcessTerm "kafka"
killProcess "kafka"

#stop zookeeper
killProcessTerm "zookeeper"
killProcess "zookeeper"





