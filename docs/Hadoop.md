## Running Hadoop cluster(this is current state of task)
1. Run 2 servers

    <code>$ vagrant up slave1 master </code>
2. Auto start is not working at first time(don't know reason) need to halt system and then run it again

    <code>$ vagrant halt </code>
    <code>$ vagrant up slave1 master </code>

3. Up time is about 2 minutes

4. if everything is ok you can visit http://192.168.205.11:8088/cluster/nodes and see 2 active nodes