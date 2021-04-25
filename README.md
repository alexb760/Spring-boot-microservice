# Srping Microservices

## Table of Contents
* About the project.
* Patters.
* Technologies.
* Setup.
* Check and Play.
* References

### About

We build a simple game that gives a challenge to solve ask the name and base
on your correct guessing save and give you rewards.

The following project we can find how to build a resilient and fault tolerant
system in order to achieve this goal we use:

BDD architecture splitting up in domains all aur logics business thats how we
start from a monolithic app and in the second iteration we were able to create
separate domains that later on will become in different services:

* Multiplication - services.
* Gamification - services.

The second stage in this project is apply common microservices patters to make
or system resilient, fault tolerant, loose coupled and scalable:
### Patters:
* Gateway: Will apply common routing between services given as ability to point to 
  a single point make our client agnostic about infra structure in the back end.
* Load Balancer: Comes handy when we set up many instances of our services
  Front end do not need to know how to balance request instead we use Load balancer
* Registry Service: An microservice architecture need to know somehow where and which services are available
  then we implement a service discovery where any single services in our system will register in.
* Centralized logs: once all aur service are go and up ich service will own its logs track potential bugs or errors will become in a nightmare
  then we apply centralized logs to channel all logs to a specific place. also
  we apply a traceable logs technics to track down logs "Sluth".
* Centralized configuration: in this steep we want to have all our configuration in a single place that will
  allow as more easy life in terms to change things in a single place and apply ether all projects or single service
  and also manage different profiles e.g: Dev, Prod, Test.
  
A part of mentioned above, we also implement a event-drive-architecture by applying these we can gain loose couple
since now our microservices can share message between in a non-blocking fashion just publishing events and subscribing to tehm
to achieve this project go for RabbitMQ for tracing logs and messages sharing.

Last but not least Containerization we go for Docker as a container platform where we can ready an up
all our system in a single script using docker-composer. All needed script are available in
script folder.

Our system is cloud friendly now, scalable and resilient, etc.
 
## Technologies:
1. Java 14 - Compiled in Java 15.
2. Spring Boot 2.4.2.
3. Spring Cloud 2020.0.2 (Gateway, LoadBalancer, logs)
4. Consul server: 1.9.5 (Service discovery, Centralized config - KV)
5. RabbitMQ 3.8 (Message broker)
6. Docker
7. Docker-compose v3
8. NodeJS V 12.16.3
9. npm V 6.14.4

## Setup:
1. Clone project:
2. Creates a importer consul images, this images help as to load the initial configurations
   into our consul when we start it up
   ```shell
    cd consul-importer
   docker build -t consul-importer:1.0 .    
   ```
3.  Let's create all service images
```shell
cd ../scripts
./createImg.sh
```
4. Creates frontend images:
```
cd ../frontend-app
npm run build
docker build -t alexb-io/frontend-app:1.0 .
```
5. checks all images were created
```shell
#should list:
# consul-importer 
# alexb-io/frontend-app
# alexb-io/gamification
# alexb-io/multiplication
# alexb-io/gateway
# alexb-io/logs
docker images
```
6. start up
```shell
cd ../scripts
docker-compose up
```
6.1 scalability after run step 6 we can scale.
````shell
docker-compose up --scale multiplication=2 --scale gamification=2
````

## Check and play
* Interface should run in localhost:3000
* Consul server runs in localhost:8500/ui
* RabbitMq runs in localhost:15672 (user:guest, pw:guest)
  
## Reference:
* [Learn Microservice](https://www.oreilly.com/library/view/learn-microservices-with/9781484261316)
