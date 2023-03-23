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
docker stop service-department-jvm
# remove container
docker rm service-department-jvm
# remove container image
docker rmi poc/service-department-jvm:latest
# build container image
docker build -f Dockerfile.jvm -t poc/service-department-jvm .
# run container
docker run -id --network=poc-net --name service-department-jvm -p 8081:8081 poc/service-department-jvm