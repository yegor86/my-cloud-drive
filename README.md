# Installation guide

## Install environment
1. Download and Install JDK 1.8 - http://www.oracle.com/technetwork/java/javase/downloads/index.html.
2. Download and Install Oracle Virtual Box - https://www.virtualbox.org/wiki/Downloads
3. Download and Install Gradle - https://gradle.org/gradle-download/

	* Download and unzip gradle-x.x foder
	* Add gradle-x.x/bin in executable %PATH%
4. Install vagrant http://docs.vagrantup.com/v2/installation/
5. Restart your computer
6. Install Lunix: Go to src/etc/vagrant and execute the following command on this folder

	<code>$ vagrant up </code>
7. Apply migrations: Go to the project directory and execute

	<code>$ gradle flywayMigrate -i</code>
8. Check that linux is up and running

	<code>$ vagrant ssh</code>
9. Check that tables were created (Credentials: mydrive/mydrive):

	<code>$ psql -h localhost -p 5432 -U mydrive</code>
	<code>mydrive=> \dt</code>

## Run application
1. Go to the project directory and start the server

	<code>gradlew bootRun</code>
2. Check it works on port 3030: Follow the address

	<code>localhost:3030</code>
	