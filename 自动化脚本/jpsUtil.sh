#!/bin/bash
hosts=("hadoop101" "hadoop102" "hadoop103")

for i in ${hosts[@]};
do
echo "=============== jps $i==============="
ssh $i "source /etc/profile;jps"
echo ""
done