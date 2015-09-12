# Installation guide

## Install environment
1. Download and Install JDK 1.8 - http://www.oracle.com/technetwork/java/javase/downloads/index.html.
2. Download and Install Gradle - https://gradle.org/gradle-download/

	* Download and unzip gradle-x.x foder
	* Add gradle-x.x/bin in executable %PATH%
3. Download and Install Oracle Virtual Box - https://www.virtualbox.org/wiki/Downloads
4. Install vagrant http://docs.vagrantup.com/v2/installation/
5. Restart your computer

### Install VM with DB
1. Go to _src/etc/vagrant_ and execute the following command on this folder

	<code>$ vagrant up db</code>
2. Apply migrations: Go to the project directory and execute

	<code>$ gradle flywayMigrate -i</code>
3. Check that linux is up and running

	<code>$ vagrant ssh db</code>
4. Check that tables were created (Credentials: mydrive/mydrive):

	<code>$ psql -h localhost -p 5432 -U mydrive</code>
	
	<code>mydrive=> \dt</code>

### Install VM with Web Server
1. Build the application from the project directory
    
    <code>$ gradle build</code>
2. Copy jar file from _build\libs_ to _src\etc\vagrant\Vagrant-setup_
3. Go to _src/etc/vagrant_ and execute the following command on this folder
    
    <code>$ vagrant up web</code>
4. Check from the Web Browser that the application is up and running and has access to VM with database. 
    Follow the link <http://localhost:3030/users> and make sure you don't have Internal Server Error code (500) 
4. Check that linux is up and running
    
    <code>$ vagrant ssh web</code>
5. Check that docker container is up and running. Copy <container-id>  from the ps command output  

    <code>$ docker ps</code>

6. Connect to the running container
    
    <code>$ docker exec -it <container-id> bash</code>

#### To rebuild the container
1. Stop the container 

    <code>$ docker stop <container-id></code>
2. Remove the container
    
    <code>$ docker stop <container-id></code>
3. Build the container

    <code>$ docker build -t my-cloud-drive /mnt/bootstrap/web"</code>
1. Run docker container again from shell
    
    <code>$ docker run -d -p 3030 --net="host" my-cloud-drive</code>

## Run application locally
1. Go to the project directory and generate executable file

    <code>$ gradle wrapper</code>
2. Start server

    <code>$ gradlew bootRun</code>
2. Check it works on port 3030: Follow the address (Make sure that port 3030 is not bound)

	<code>localhost:3030</code>


