#!/bin/sh
cd ../../gateway
./mvnw clean package
export MULTIPLICATION_IMAGE=alexb-io/gateway
echo "Build images $MULTIPLICATION_IMAGE"
docker build --build-arg JAR_FILE=target/\*.jar -t $MULTIPLICATION_IMAGE .
#docker run -d -p 8080:8080 $MULTIPLICATION_IMAGE --name=gateway
