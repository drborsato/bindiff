all: build run

run:
	docker run -d -p 8080:8080 --name bindiff -t bindiff:latest

build:
	mvn clean package
	docker build -t bindiff:latest .

test:
	mvn clean verify

stop:
	docker stop bindiff

rm:
	docker rm bindiff

rebuild: stop rm build run