## Docker basic commands
1. Check that docker container is up and running. Copy [container-id]  from the ps command output  

    <code>$ docker ps</code>

2. Connect to the running container
    
    <code>$ docker exec -it [container-id] bash</code>

## To rebuild the container
1. Stop the container 

    <code>$ docker stop [container-id]</code>
2. Remove the container
    
    <code>$ docker rm [container-id]</code>
3. Build the container

    <code>$ docker build -t my-cloud-drive /mnt/bootstrap/web"</code>
4. Run docker container again from shell
    
    <code>$ docker run -d -p 3030 --net="host" my-cloud-drive</code>