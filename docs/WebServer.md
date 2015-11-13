## Install VM with Web Server
1. Build the application from the project directory
    
    <code>$ gradle build</code>
2. Run VM with Web Server
    
    <code>$ vagrant up web</code>
3. Check from the Web Browser that the application is up and running and has access to VM with database. 
    
    Follow the link [http://localhost:3030/users](http://localhost:3030/users) and make sure you don't have Internal Server Error code (500) 
4. Check that linux is up and running
    
    <code>$ vagrant ssh web</code>
    

## Re-Install VM with Web Server
1. Rebuild the jar file

    <code>$ gradle build</code>

2. [Rebuild the container](docs/Docker.md)