#!/bin/sh
cd ../../logs
./mvnw clean package
export MULTIPLICATION_IMAGE=alexb-io/logs
echo "Build images $MULTIPLICATION_IMAGE"
docker build --build-arg JAR_FILE=target/\*.jar -t $MULTIPLICATION_IMAGE .
#docker run -d -p 8080:8080 $MULTIPLICATION_IMAGE --name=multiplication
