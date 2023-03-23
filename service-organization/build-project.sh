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
docker stop service-organization-jvm
# remove container
docker rm service-organization-jvm
# remove container image
docker rmi poc/service-organization-jvm:latest
# build container image
docker build -f Dockerfile.jvm -t poc/service-organization-jvm .
# run container
docker run -id --network=poc-net --name service-organization-jvm -p 8082:8082 poc/service-organization-jvm