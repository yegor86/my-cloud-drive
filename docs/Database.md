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
