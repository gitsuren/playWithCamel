# IBM MQ / Spring Boot  / Apache Camel

Example project using IBM MQ, Spring Boot and Apache camel.  It demonstrates both reading and writing to an 
MX queue by emitting a message every second and reading the message back.  The receiving camel route outputs
the body of the received message to the log.

To run the application it needs an instance of IBM MQ running locally. 
I used the IBM provided docker image - https://hub.docker.com/r/ibmcom/mq/.

## Running the Docker image

To download the docker image...

```
docker pull ibmcom/mq
```

Full details for using the docker image can be found here->https://github.com/ibm-messaging/mq-docker
* https://hub.docker.com/r/ibmcom/mq/

**TL;DR**
Use Docker to create a volume:
```
docker volume create qm1data
```

Run a default queue with all the defaults

```
docker run \
  --env LICENSE=accept \
  --env MQ_QMGR_NAME=QM1 \
  --publish 1414:1414 \
  --publish 9443:9443 \
  --detach \
  ibmcom/mq
  
OR
   
docker run --env LICENSE=accept --env MQ_QMGR_NAME=QM1 --volume qm1data:/mnt/mqm --publish 1414:1414 --publish 9443:9443 --detach --env MQ_APP_PASSWORD=passw0rd ibmcom/mq:latest

$ docker ps
$ docker exec -ti <your container id> /bin/bash

$ dspmqver
$ dspmq

What you’ve done so far
========================
Inside the container, the MQ installation on RHEL has the following objects:

Queue manager QM1
Queue DEV.QUEUE.1
Channel: DEV.APP.SVRCONN
Listener: DEV.LISTENER.TCP on port 1414  
```
For mqweb console to check if it is running or not from inside docker container:
```
bash-4.4$ dspmqweb
MQWB1124I: Server 'mqweb' is running.
URLS:
https://562c2d18e6eb:9443/ibmmq/console/
https://562c2d18e6eb:9443/ibmmq/rest/
bash-4.4$ strmqweb
Server mqweb is already running.
bash-4.4$
```

Once started MQ Dev edition will be running with the following configuration
* https://localhost:9443/ibmmq/console - MQ Management console
*https://localhost:9443/ibmmq/console/#/manage/qmgr/QM1/queues - for queues
 
  Username : admin password: passw0rd
  
* Port 1414 - MQ listener

All of the default settings are used for the demonstration application

## Monitoring

Both Spring Actuator and Hawtio are configured on this example app using port 8095.


**Actuator Endpoints**

```$xslt
Some endpoints require authentication

Username : admin
Password : admin

http://localhost:8095/health
http://localhost:8095/info
http://localhost:8095/metrics
```

**HAWTIO**

Hawtio is available on port 8095: /hawtio as well.  It provides realtime visualisation of
the process, including the camel messages exchanges.

```$xslt
http://localhost:8095/hawtio
```

# Also you can refer to following link
* https://developer.ibm.com/components/ibm-mq/tutorials/mq-connect-app-queue-manager-containers/#step-3-run-the-container-from-the-image
* https://developer.ibm.com/tutorials/mq-setting-up-using-ibm-mq-console/