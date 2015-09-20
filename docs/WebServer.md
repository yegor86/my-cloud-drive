## Install VM with Web Server
1. Build the application from the project directory
    
    <code>$ gradle build</code>
2. Copy jar file from _build\libs_ to _src\etc\vagrant\Vagrant-setup_
3. Go to _src/etc/vagrant_ and execute the following command on this folder
    
    <code>$ vagrant up web</code>
4. Check from the Web Browser that the application is up and running and has access to VM with database. 
    
    Follow the link <http://localhost:3030/users> and make sure you don't have Internal Server Error code (500) 
5. Check that linux is up and running
    
    <code>$ vagrant ssh web</code>

6. [Docker Guide](docs/Docker.md)