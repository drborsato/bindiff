# BinDiff
Java API to compare two bin files

## Description

The basic idea of the `bindiff` application is to compare two bin files. This files are stored at in memory database `H2`.

It's provided endpoints to manage these files, and another one to compare them.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

It's provided `Makefile` to help build, test and run the containers.

### Prerequisites

In order to successfully run this project as well as develop in it you need to have the following
installed:

* JDK 11: [Linux](https://www.digitalocean.com/community/tutorials/how-to-install-java-with-apt-on-ubuntu-18-04)

Please note the link above contains instructions on how to setup java using either openjdk
(preferred) or oracle JDK. The PPA used for the oracle JDK bit _is actually discontinued_. If you
insist on installing oracle, please do so using the [appropriate means](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)

* Maven 3+ [Install](https://maven.apache.org/install.html)

Another way to quickly get java/maven going is to use [sdkman](https://sdkman.io)

* Docker: [Linux](https://docs.docker.com/engine/install/ubuntu/)

### Build from sources

Just run `make build` at your terminal.
It will compile and creates the `docker` image to run.

### Running the tests

Run `make test`

### Run the container

Type `make run`

### Rebuild the service

`make rebuild`. It will stop the actual container, remove it, generates a new image, and finally run the newest image.

## How API works

Swagger documentation can be found at: https://app.swaggerhub.com/apis/borsato/BinDiff/1.0

## Next steps

* Add new database to store the files, so we can isolate the database from the service, in order to decouple.
* Add New Relic, Prometheus and Grafana integration in order to monitor and receive alerts accordingly to behavior of the solution.
* Add Logging service integration to store the service logs and help us to track.
* Integrate Swagger documentation with service using `swagger-ui`
