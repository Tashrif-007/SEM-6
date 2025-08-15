#!/bin/bash

echo "🚀 Deploying Smart Library Stack to Docker Swarm"

# Check if swarm is active
if ! docker info | grep -q "Swarm: active"; then
    echo "❌ Docker Swarm is not active. Please run setup-swarm.sh first."
    exit 1
fi

# Check if we're on a manager node
if ! docker node ls >/dev/null 2>&1; then
    echo "❌ This node is not a manager. Stack deployment requires manager privileges."
    exit 1
fi

# Deploy the stack
echo "📦 Deploying smartlibrary stack..."
docker stack deploy -c docker-stack.yml smartlibrary

echo "⏳ Waiting for services to start..."
sleep 10

# Show stack status
echo ""
echo "📊 Stack Status:"
docker stack ps smartlibrary

echo ""
echo "🔍 Service Status:"
docker service ls

echo ""
echo "✅ Smart Library Stack deployed successfully!"
echo ""
echo "🌐 Access your services at:"
echo "- MongoDB User DB: mongodb://localhost:27017/userDB"
echo "- MongoDB Book DB: mongodb://localhost:27018/bookDB"
echo "- MongoDB Loan DB: mongodb://localhost:27019/loanDB"
echo "- User Service: http://localhost:8081"
echo "- Book Service: http://localhost:8082" 
echo "- Loan Service: http://localhost:8083"
echo "- NGINX Gateway: http://localhost"
echo ""
echo "📈 Monitor your stack with:"
echo "  docker stack ps smartlibrary"
echo "  docker service logs smartlibrary_user-service"
echo "  docker service logs smartlibrary_book-service"
echo "  docker service logs smartlibrary_loan-service"
echo "  docker service logs smartlibrary_mongo-user"
echo "  docker service logs smartlibrary_mongo-book"
echo "  docker service logs smartlibrary_mongo-loan"
