#!/usr/bin/env bash

#This package provides the add-apt-repository binary, which you will need to
#install a new ppa easily
sudo apt-get -y install python-software-properties

#add provider for oracle java
sudo add-apt-repository -y ppa:webupd8team/java

sudo apt-get -y update

#accept copyrights for oracle-java8-installer
echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
sudo apt-get -y install oracle-java8-installer
sudo apt-get -y install dos2unix

sudo update-java-alternatives -s java-8-oracle

# Create hadoopgroup
sudo addgroup hadoopgroup
# Create hadoopuser user
sudo adduser hadoopuser --gecos "First Last,RoomNumber,WorkPhone,HomePhone" --ingroup hadoopgroup --disabled-password
echo "hadoopuser:password" | sudo chpasswd

#change to hadoopuser
sudo -u hadoopuser bash << EOF

mkdir -p /home/hadoopuser/.ssh

chmod 700 /home/hadoopuser/.ssh/

touch /home/hadoopuser/.ssh/authorized_keys

chmod 640 /home/hadoopuser/.ssh/authorized_keys

cat /mnt/bootstrap/hadoop_files/id_rsa.pub >> /home/hadoopuser/.ssh/authorized_keys

#unpack hadoop
cd /home/hadoopuser
wget http://www.webhostingjams.com/mirror/apache/hadoop/core/stable/hadoop-2.7.1.tar.gz
#cp /mnt/bootstrap/hadoop-2.7.1.tar.gz /home/hadoopuser/
tar xvf hadoop-2.7.1.tar.gz
mv hadoop-2.7.1 hadoop

# Set HADOOP_HOME
export HADOOP_HOME=/home/hadoopuser/hadoop
# Set JAVA_HOME
export JAVA_HOME=/usr/lib/jvm/java-8-oracle
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
#create directories for master and slave
mkdir -p /home/hadoopuser/hadoop-data/hadoopuser/hdfs/namenode
mkdir -p /home/hadoopuser/hadoop-data/hadoopuser/hdfs/datanode

cp /mnt/bootstrap/hadoop_files/hdfs-site.xml /home/hadoopuser/hadoop/etc/hadoop/hdfs-site.xml
dos2unix /mnt/bootstrap/hadoop_files/hdfs-site.xml /home/hadoopuser/hadoop/etc/hadoop/hdfs-site.xml
cp /mnt/bootstrap/hadoop_files/yarn-site.xml /home/hadoopuser/hadoop/etc/hadoop/yarn-site.xml
dos2unix /mnt/bootstrap/hadoop_files/yarn-site.xml /home/hadoopuser/hadoop/etc/hadoop/yarn-site.xml

EOF