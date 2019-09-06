#!/bin/bash
hosts=("hadoop101" "hadoop102" "hadoop103")
echo "=============== kafka $1==============="
for i in ${hosts[@]};
do
	if [ "$1" == "start" ];then
		ssh $i "source /etc/profile;/opt/module/kafka_2.12-2.2.0/bin/kafka-server-start.sh /opt/module/kafka_2.12-2.2.0/config/server.properties 1>/dev/null 2>&1 &"
		echo "start kafka at $i"
	else
		ssh $i "source /etc/profile;/opt/module/kafka_2.12-2.2.0/bin/kafka-server-stop.sh"
		echo "stop kafka at $i"
	fi
done