### Install VM with DB
1. Run VM with Database

	<code>$ vagrant up db</code>
2. Apply migrations

	<code>$ gradle flywayMigrate -i</code>
3. Create a new user

    <code>$ gradle -Pfirstname=Yegor -Plastname=Fadeev -Pemail=admin@mail.com register</code>
4. Check that linux is up and running

	<code>$ vagrant ssh db</code>
5. Check that tables were created (Credentials: mydrive/mydrive):

	<code>$ psql -h localhost -p 5432 -U mydrive</code>
	
	<code>mydrive=> \dt</code>
