## Running Hadoop cluster(this is current state of task)
1. Run 2 servers (Up time is about 2 minutes)
    
    <code>$ vagrant up slave1 master </code>
   
2. if everything is ok you can visit [http://192.168.205.11:8088/cluster/nodes](http://192.168.205.11:8088/cluster/nodes) and see 2 active nodes

3. Test HDFS
* Generate test files in HDFS

    <code>$ gradle test --tests org.odesamama.mcd.HDFSTests</code>
* Check that file exists in hadoop fs

    <code>$ vagrant ssh master</code>
    
    <code>$ sudo su hadoopuser</code>
    
    <code>$ hadoop fs -ls /testfileslocation</code>
    
    
     