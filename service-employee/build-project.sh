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
docker stop service-employee-jvm
# remove container
docker rm service-employee-jvm
# remove container image
docker rmi poc/service-employee-jvm:latest
# build container image
docker build -f Dockerfile.jvm -t poc/service-employee-jvm .
# run container
docker run -id --network=poc-net --name service-employee-jvm -p 8080:8080 poc/service-employee-jvm