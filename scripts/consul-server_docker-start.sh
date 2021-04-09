#!bin/sh
# Start server in detached mode "-d"
# Expose ports 8500 and 8600
# binding ip 0.0.0.0 this allows you to work directly with the datacenter from your local machine
# Access to UI interface "-ui" in port.
#
# check members " docker exec kingslanding consul members "
# References: https://learn.hashicorp.com/tutorials/consul/docker-container-agents
docker run \
    -d \
    -p 8500:8500 \
    -p 8600:8600/udp \
    --name=kingslanding \
    consul agent -server -ui -node=server-1 -bootstrap-expect=1 -client=0.0.0.0
