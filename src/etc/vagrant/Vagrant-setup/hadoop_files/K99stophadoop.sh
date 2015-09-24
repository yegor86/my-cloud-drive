#!/bin/sh -e

su -c 'bash /home/hadoopuser/hadoop/sbin/stop-dfs.sh' hadoopuser
su -c 'bash /home/hadoopuser/hadoop/sbin/stop-yarn.sh' hadoopuser

exit 0
