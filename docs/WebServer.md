## Install VM with Web Server
1. Build the application from the project directory
    
    <code>$ gradle build</code>
2. Copy jar file from _build\libs_ to _src\etc\vagrant\Vagrant-setup_
3. Go to _src/etc/vagrant_ and execute the following command on this folder
    
    <code>$ vagrant up web</code>
4. Check from the Web Browser that the application is up and running and has access to VM with database. 
    Follow the link <http://localhost:3030/users> and make sure you don't have Internal Server Error code (500) 
4. Check that linux is up and running
    
    <code>$ vagrant ssh web</code>
5. Check that docker container is up and running. Copy [container-id]  from the ps command output  

    <code>$ docker ps</code>

6. Connect to the running container
    
    <code>$ docker exec -it [container-id] bash</code>

#### To rebuild the container
1. Stop the container 

    <code>$ docker stop [container-id]</code>
2. Remove the container
    
    <code>$ docker rm [container-id]</code>
3. Build the container

    <code>$ docker build -t my-cloud-drive /mnt/bootstrap/web"</code>
4. Run docker container again from shell
    
    <code>$ docker run -d -p 3030 --net="host" my-cloud-drive</code>