# -*- mode: ruby -*-
# vi: set ft=ruby :

$script = <<SCRIPT
echo I am provisioning...
date > /etc/vagrant_provisioned_at
SCRIPT


Vagrant.configure("2") do |config|
  config.vm.provision "shell", inline: $script

  config.vm.define "db", primary: true do |postgress|
    postgress.vm.box = "ubuntu/trusty64"
    postgress.vm.box_url = "https://atlas.hashicorp.com/ubuntu/boxes/trusty64"
    postgress.vm.host_name = "postgresql"

    postgress.vm.synced_folder "Vagrant-setup", "/mnt/bootstrap/db", :create => true
    postgress.vm.provision :shell, :path => "Vagrant-setup/bootstrap.sh"

    # PostgreSQL Server port forwarding
    postgress.vm.network "forwarded_port", guest: 5432, host: 5432
    postgress.vm.network "private_network", ip: "192.168.205.10"
  end
  
  config.vm.define "web" do |container| 
    container.vm.box = "ubuntu/trusty64"
    container.vm.box_url = "https://atlas.hashicorp.com/ubuntu/boxes/trusty64"
    container.vm.host_name = "webapp"
    
    container.vm.synced_folder ".", "/mnt/bootstrap/web", :create => true
    container.vm.provision "docker" do |d|
        d.build_image "/mnt/bootstrap/web",
            args: "-t my-cloud-drive"
        d.run "my-cloud-drive", 
            args: "-d -p 3030:3030 --net=host my-cloud-drive"
    end
    
    container.vm.network "forwarded_port", guest: 3030, host: 3030
    container.vm.network "private_network", ip: "192.168.205.13"
  end

  config.vm.define "slave1" do |slave1|
    slave1.vm.box = "ubuntu/trusty64"
    slave1.vm.box_url = "https://atlas.hashicorp.com/ubuntu/boxes/trusty64"
    slave1.vm.host_name = "slave1"

    slave1.vm.synced_folder "Vagrant-setup", "/mnt/bootstrap", :create => true
    slave1.vm.provision :shell, :path => "Vagrant-setup/hadoop_slave_new.sh"

    slave1.vm.network "private_network", ip: "192.168.205.12"
  end

  config.vm.define "master" do |master|
    master.vm.box = "ubuntu/trusty64"
    master.vm.box_url = "https://atlas.hashicorp.com/ubuntu/boxes/trusty64"
    master.vm.host_name = "master"

    master.vm.synced_folder "Vagrant-setup", "/mnt/bootstrap", :create => true
    master.vm.provision :shell, :path => "Vagrant-setup/hadoop_master_new.sh"

    master.vm.network "forwarded_port", guest: 8088, host: 8088
    master.vm.network  "private_network", ip: "192.168.205.11"
  end

  config.vm.provider "virtualbox" do |vb|
    vb.customize ["modifyvm", :id, "--memory", "2048"]
  end



end
