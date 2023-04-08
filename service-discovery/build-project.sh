#!/bin/bash

# stop container
docker stop service-discovery

# remove container
docker rm service-discovery

# remove container image
docker rmi poc/service-discovery:latest

# build app and docker image
mvn clean install

# run docker image with no export port cause they are accessible by geteway-service
docker run -id --network=localnet --name service-discovery -p 8888:8888 poc/service-discovery