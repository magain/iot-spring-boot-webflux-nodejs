# General

This repository contains the source code of three applications:

* DeviceSimulator
* LoggerService
* ApiGateway

## Applications Start Sequence

1. LoggerService
2. ApiGateway
3. DeviceSimulator 

## Environment

The system has been build to run a single system because all communication goes to _localhost_.

## Prerequisites

The following software has to be install to build and run all applications:
* Java 1.8
* Maven 3.6.1
* node.js v12.1.0 including npm
* protoc 3.7.1

The mentioned versions are those used during development.

## Technology

The following table lists the technologies and frameworks used to develop the applications

|Application|Language|Framework|Dependencies|Remarks|
|---|---|---|---|---|
|**DeviceSimulator**|Java| |org.json|pure Java|
|**ApiGateway**|Java|Spring Boot|spring-boot-starter-webflux||
| | | |spring-boot-starter-test| |
| | | |reactor-test| |
| | | |protobuf-java| |
| | | |protobuf-maven-plugi| |
| | | |spring-boot-maven-plugin| |
| | | |build-helper-maven-plugin| |
|**LoggerService**|JavaScript|node.js|google-protobuf| |
| | | |dgram| |
| | | |winston| |
         

# LoggerService

The application _LoggerService_ is a node.js application. Its use case is to receive binary Google Protocol Buffer messages sent over the UDP protocol,
deserialize these received messages and log them into a log file located in the folder path/to/LoggerService/logs.

**Install Packages & Run**

To install necessary npm package and start the application open a terminal and execute the following commands inside the folder path/to/LoggerService:
```
npm install
node server.js
```
# ApiGateway

The _ApiGateway_ is an application acting as a gateway receiving requestand to to pass them on to the _LoggerService_. Its main use case in
addition to pass all incoming requests on to the _LoggerService_ is to translate/convert received JSON payload to Google Protocol Buffer format. The communication protocol
also changes from HTTP to UDP.

**Build**

The project _ApiGateway_ is a Maven project. To build the project open a terminal and execute the following command inside the folder path/to/ApiGateway:
```
mvn clean install
```
**Run**

The application can then be started by executing the following command inside the folder path/to/ApiGateway:
```
java -jar target/ApiGateway-0.0.1-SNAPSHOT.jar
```
# DeviceSimulator

The _DeviceSimulator_ is an application for testing the _ApiGateway_ and the _LoggerService_.
It simulates 20 devices each sending HTTP POST request with a JSON payload to the _ApiGateway_.

**Build**

The project _DeviceSimulator_ is a Maven project. To build the project open a terminal and execute the following command inside the folder path/to/DeviceSimulator:
```
mvn clean install
```

**Run**

The application can then be started by executing the following command inside the folder path/to/DeviceSimulator:
```
java -jar target/DeviceSimulator-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

