# BinDiff
Java API to compare two bin files Base64 encoded

## Description

The basic idea of the `bindiff` application is to compare two bin files. This files are stored at in memory database `H2`.

It's provided endpoints to manage these files, and another one to compare them.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

**To help on getting started, is provided basic files at database, so we can compare and manage them.**

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

### Run the container

Type `make run`

It's also possible to run `make all`. It will build and run the service.

### Rebuild the service

`make rebuild`. It will stop the actual container, remove it, generates a new image, and finally run the newest image.

### Running the tests

Run `make test`

## How API works

Swagger documentation can be found at: https://app.swaggerhub.com/apis/borsato/BinDiff/1.0

### Examples

#### Comparing files

##### Comparing equal files

```
curl --request GET \
  --url http://localhost:8080/v1/diff/1/
```

##### Comparing files with different size

```
curl --request GET \
  --url http://localhost:8080/v1/diff/2/
```

##### Comparing different files

```
curl --request GET \
  --url http://localhost:8080/v1/diff/3/
```

#### Managing files

##### Get a File

```
curl --request GET \
  --url http://localhost:8080/v1/diff/1/left
```

##### Create a File

```
curl --request POST \
  --url http://localhost:8080/v1/diff/5/right \
  --header 'Content-Type: application/json' \
  --data '{
	"data": "YWJj"
}'
```

##### Update a File

```
curl --request PUT \
  --url http://localhost:8080/v1/diff/4/right \
  --header 'Content-Type: application/json' \
  --data '{
	"data": "YWJjaG"
}'
```

##### Delete a File

```
curl --request DELETE \
  --url http://localhost:8080/v1/diff/4/left
```

## Next steps

* Add new database to store the files, so we can isolate the database from the service, in order to decouple.
* Add New Relic, Prometheus and Grafana integration in order to monitor and receive alerts accordingly to behavior of the solution.
* Add Logging service integration to store the service logs and help us to track.
* Integrate Swagger documentation with service using `swagger-ui`.
* Add BDD tests.
* Add SonarQube to check code smells.
