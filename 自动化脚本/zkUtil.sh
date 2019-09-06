#!/bin/bash
hosts=("hadoop101" "hadoop102" "hadoop103")
echo "=============== zookeeper $1==============="
for i in ${hosts[@]};
do
ssh $i "source /etc/profile;/opt/module/zookeeper-3.4.10/bin/zkServer.sh $1"
done