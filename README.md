# Installation guide

## Install environment
* Download and Install JDK 1.8 - http://www.oracle.com/technetwork/java/javase/downloads/index.html
* Download and Install Oracle Virtual Box - https://www.virtualbox.org/wiki/Downloads
* Download and Install Gradle - https://gradle.org/gradle-download/
..1. Download and unzip gradle-x.x foder
..2. Add gradle-x.x/bin as executable PATH
* Install vagrant http://docs.vagrantup.com/v2/installation/
* Restart your computer
* Install Lunix: Go to src/etc/vagrant and execute the following command on this folder
	<code>$ vagrant up </code>
* Apply migrations: Go to the project directory and execute
	<code>$ gradle flywayMigrate -i</code>
* Check that linux is up and running
	<code>$ vagrant ssh</code>
* Check that tables were created (Credentials: mydrive/mydrive):
	<code>$ psql -h localhost -p 5432 -U mydrive</code>
	<code>mydrive=> \dt</code>
	
## Run application
* Go to the project directory and start the server
	<code>gradlew bootRun</code>
* Check it works on port 3030: Follow the address 
	<code>localhost:3030</code>
	



