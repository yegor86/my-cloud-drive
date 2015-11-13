#!/usr/bin/env bash
#This package provides the add-apt-repository binary, which you will need to
#install a new ppa easily

sudo sed -i 's/net.ipv6.bindv6only\ =\ 1/net.ipv6.bindv6only\ =\ 0/' \
/etc/sysctl.d/bindv6only.conf && sudo invoke-rc.d procps restart

sudo apt-get -y install python-software-properties

# openjdk
sudo apt-get -y update
sudo apt-get -y install openjdk-7-jdk
sudo ln -s /usr/lib/jvm/java-1.7.0-openjdk-amd64 /usr/lib/jvm/java

sudo apt-get -y install dos2unix

#copy sripst to run at startup and shutdown
cp /mnt/bootstrap/hadoop_files/rc.local /etc/rc.local
dos2unix /mnt/bootstrap/hadoop_files/rc.local /etc/rc.local
chmod +x /etc/rc.local

cp /mnt/bootstrap/hadoop_files/K99stophadoop.sh /etc/rc6.d/K99stophadoop.sh
dos2unix /mnt/bootstrap/hadoop_files/K99stophadoop.sh /etc/rc6.d/K99stophadoop.sh
chmod +x /etc/rc6.d/K99stophadoop.sh

# Create hadoopgroup
sudo addgroup hadoopgroup
# Create hadoopuser user
sudo adduser hadoopuser --gecos "First Last,RoomNumber,WorkPhone,HomePhone" --ingroup hadoopgroup --disabled-password
echo "hadoopuser:password" | sudo chpasswd

sudo -u hadoopuser bash << EOF

mkdir -p /home/hadoopuser/.ssh
#Generate a ssh key for the user
#ssh-keygen -t rsa -P "" -f /home/hadoopuser/.ssh/id_rsa
cp /mnt/bootstrap/hadoop_files/id_rsa.pub  /home/hadoopuser/.ssh/
cp /mnt/bootstrap/hadoop_files/id_rsa  /home/hadoopuser/.ssh/
cat /home/hadoopuser/.ssh/id_rsa.pub >> /home/hadoopuser/.ssh/authorized_keys

chmod 640 /home/hadoopuser/.ssh/authorized_keys
chmod 600 /home/hadoopuser/.ssh/id_rsa

export HOME=/home/hadoopuser

ssh-keyscan 0.0.0.0 >> /home/hadoopuser/.ssh/known_hosts
ssh-keyscan localhost >> /home/hadoopuser/.ssh/known_hosts
ssh-keyscan 192.168.205.11 >> /home/hadoopuser/.ssh/known_hosts
ssh-keyscan 192.168.205.12 >> /home/hadoopuser/.ssh/known_hosts

#unpack hadoop
cd /home/hadoopuser
wget http://www.eu.apache.org/dist/hadoop/core/hadoop-2.7.1/hadoop-2.7.1.tar.gz

#cp /mnt/bootstrap/hadoop-2.7.1.tar.gz /home/hadoopuser/
tar xvf hadoop-2.7.1.tar.gz
mv hadoop-2.7.1 hadoop

export HADOOP_HOME=/home/hadoopuser/hadoop
# Add Hadoop bin and sbin directory to PATH
export PATH=$PATH:$HADOOP_HOME/bin
export PATH=$PATH:$HADOOP_HOME/sbin

#set enviroment variables
cp /mnt/bootstrap/hadoop_files/.bashrc /home/hadoopuser/.bashrc
dos2unix /mnt/bootstrap/hadoop_files/.bashrc /home/hadoopuser/.bashrc

cp /mnt/bootstrap/hadoop_files/hadoop-env.sh /home/hadoopuser/hadoop/etc/hadoop/hadoop-env.sh
dos2unix /mnt/bootstrap/hadoop_files/hadoop-env.sh /home/hadoopuser/hadoop/etc/hadoop/hadoop-env.sh
cp /mnt/bootstrap/hadoop_files/core-site.xml /home/hadoopuser/hadoop/etc/hadoop/core-site.xml
dos2unix /mnt/bootstrap/hadoop_files/core-site.xml /home/hadoopuser/hadoop/etc/hadoop/core-site.xml
#master only
cp /mnt/bootstrap/hadoop_files/mapred-site.xml /home/hadoopuser/hadoop/etc/hadoop/mapred-site.xml
dos2unix /mnt/bootstrap/hadoop_files/mapred-site.xml /home/hadoopuser/hadoop/etc/hadoop/mapred-site.xml
#create directories for master and slave
mkdir -p /home/hadoopuser/hadoop-data/hadoopuser/hdfs/namenode
mkdir -p /home/hadoopuser/hadoop-data/hadoopuser/hdfs/datanode

cp /mnt/bootstrap/hadoop_files/hdfs-site.xml /home/hadoopuser/hadoop/etc/hadoop/hdfs-site.xml
dos2unix /mnt/bootstrap/hadoop_files/hdfs-site.xml /home/hadoopuser/hadoop/etc/hadoop/hdfs-site.xml
cp /mnt/bootstrap/hadoop_files/yarn-site.xml /home/hadoopuser/hadoop/etc/hadoop/yarn-site.xml
dos2unix /mnt/bootstrap/hadoop_files/yarn-site.xml /home/hadoopuser/hadoop/etc/hadoop/yarn-site.xml
cp /mnt/bootstrap/hadoop_files/slaves /home/hadoopuser/hadoop/etc/hadoop/slaves
dos2unix /mnt/bootstrap/hadoop_files/slaves /home/hadoopuser/hadoop/etc/hadoop/slaves

bash /home/hadoopuser/hadoop/bin/hdfs namenode -format
EOF

# Start Datanode and Yarn
sudo -u hadoopuser bash /home/hadoopuser/hadoop/sbin/start-dfs.sh
sudo -u hadoopuser bash /home/hadoopuser/hadoop/sbin/start-yarn.sh