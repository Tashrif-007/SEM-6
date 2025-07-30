#!/bin/bash

echo "🚀 Setting up Docker Swarm for Smart Library"

# Check if Docker is running
if ! docker info >/dev/null 2>&1; then
    echo "❌ Docker is not running. Please start Docker first."
    exit 1
fi

# Initialize Docker Swarm (if not already initialized)
echo "📋 Initializing Docker Swarm..."
if ! docker info | grep -q "Swarm: active"; then
    # Get the IP address of the machine
    IP_ADDRESS=$(hostname -I | awk '{print $1}')
    echo "🔧 Initializing swarm with IP: $IP_ADDRESS"
    docker swarm init --advertise-addr $IP_ADDRESS
    
    # Save the join token for workers
    docker swarm join-token worker > worker-join-token.txt
    docker swarm join-token manager > manager-join-token.txt
    
    echo "✅ Docker Swarm initialized successfully!"
    echo "📄 Join tokens saved to worker-join-token.txt and manager-join-token.txt"
else
    echo "✅ Docker Swarm is already active"
fi

# Create overlay network if it doesn't exist
echo "🌐 Creating overlay network..."
docker network create --driver overlay --attachable smartlib-overlay 2>/dev/null || echo "Network smartlib-overlay already exists"

# Pull all required images
echo "📦 Pulling required Docker images..."
docker pull tashrif2001/smartlibrary:user-service
docker pull tashrif2001/smartlibrary:book-service
docker pull tashrif2001/smartlibrary:loan-service
docker pull nginx:latest

echo "✅ All images pulled successfully!"
echo ""
echo "🎯 Your PC is now set up as a Docker Swarm Manager node!"
echo ""
echo "📊 Swarm Status:"
docker node ls
echo ""
echo "🔥 Ready to deploy the stack!"
