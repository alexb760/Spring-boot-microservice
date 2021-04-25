#!/bin/sh
#make sure before build images you have to run: npm run build
cd ../../frontend-app
export MULTIPLICATION_IMAGE=alexb-io/frontend-app:1.0
echo "Build images $MULTIPLICATION_IMAGE"
docker build  -t $MULTIPLICATION_IMAGE .
