#!/usr/bin/env bash

echo "==================================="
echo "Creating all images game related"
echo "==================================="
echo "Create Muntiplication image..."
./services/multiplication-docker.sh
echo "Create gamification image..."
./services/gamification-docker.sh
echo "Create gateway image..."
./services/gateway-docker.sh
echo "Create logs image..."
./services/logs-docker.sh
