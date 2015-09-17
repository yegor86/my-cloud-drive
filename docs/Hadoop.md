## Running Hadoop cluster(this is current state of task)
1. Run 2 servers
   <code>vagrant up slave1 master </code>
2. Login to master
    <code> vagrant ssh master </code>
3. Change user to hadoopuser --password=password
   <code>$ su hadoopuser </code>
4. Format namenode
   <code>$ hdfs namenode -format <code>
5. Start DFS
   <code>$ bash /home/hadoopuser/hadoop/sbin/start-dfs.sh </code>
   by running command <code>$ jps </code> you shoud see
   DataNode
   NameNode
   SecondaryNameNode
6. Start Yarn
   <code>$ bash /home/hadoopuser/hadoop/sbin/start-yarn.sh </code>
   if everything is ok you can visit http://192.168.205.11:8088/cluster/nodes and see 2 active nodes