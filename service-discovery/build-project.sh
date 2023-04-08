#!/bin/bash

if [ ! "$(docker network ls | grep poc-net)" ]; then
  echo "Creating poc-net network ..."
  docker network create poc-net
else
  echo "poc-net network exists."
fi

# build the app
mvn clean package
# stop container
docker stop service-discovery-jvm
# remove container
docker rm service-discovery-jvm
# remove container image
docker rmi poc/service-discovery-jvm:latest
# build container image
docker build -f Dockerfile.jvm -t poc/service-discovery-jvm .
# run container
docker run -id --network=poc-net --name service-discovery-jvm -p 8888:8888 poc/service-discovery-jvm