#!/bin/sh
echo "####################################"
echo "Starting RabbitMQ in Terminal Interactive mode:"
echo "####################################"
echo ""
echo ""
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
