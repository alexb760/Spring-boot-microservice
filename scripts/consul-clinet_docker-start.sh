#!/bin/sh
# for dev proposes we init no detached mode
# Also would be convenient initialized in dev mode to see descriptive logs.
# Register a service:
# docker exec lannister /bin/sh -c "echo '{\"service\": {\"name\": \"counting\", \"tags\": [\"go\"], \"port\": 9001}}' >> /consul/config/counting.json"
#
# Reload the client: docker exec lannister consul reload
#
# Use Consul DNS to discover the counting service:
# dig @127.0.0.1 -p 8600 counting.service.consul
# http://localhost:8500.
docker run \
   --name=lannister \
   consul agent -node=client-1 -join=172.17.0.2
