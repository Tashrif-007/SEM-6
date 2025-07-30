#!/bin/bash

echo "🧹 Managing Smart Library Stack"

case $1 in
    "status")
        echo "📊 Stack Status:"
        docker stack ps smartlibrary
        echo ""
        echo "🔍 Service Status:"
        docker service ls
        ;;
    "logs")
        if [ -z "$2" ]; then
            echo "📜 Available services: user-service, book-service, loan-service, nginx"
            echo "Usage: ./manage-stack.sh logs <service-name>"
        else
            echo "📜 Logs for smartlibrary_$2:"
            docker service logs smartlibrary_$2
        fi
        ;;
    "scale")
        if [ -z "$2" ] || [ -z "$3" ]; then
            echo "⚖️ Usage: ./manage-stack.sh scale <service-name> <replicas>"
            echo "Example: ./manage-stack.sh scale user-service 3"
        else
            echo "⚖️ Scaling smartlibrary_$2 to $3 replicas..."
            docker service scale smartlibrary_$2=$3
        fi
        ;;
    "update")
        if [ -z "$2" ]; then
            echo "🔄 Available services: user-service, book-service, loan-service"
            echo "Usage: ./manage-stack.sh update <service-name>"
        else
            echo "🔄 Updating smartlibrary_$2..."
            docker service update --force smartlibrary_$2
        fi
        ;;
    "remove")
        echo "🗑️ Removing smartlibrary stack..."
        docker stack rm smartlibrary
        echo "✅ Stack removed successfully!"
        ;;
    "nodes")
        echo "🖥️ Swarm Nodes:"
        docker node ls
        ;;
    *)
        echo "🛠️ Smart Library Stack Management"
        echo ""
        echo "Available commands:"
        echo "  status  - Show stack and service status"
        echo "  logs    - Show logs for a specific service"
        echo "  scale   - Scale a service to specified replicas"
        echo "  update  - Force update a service"
        echo "  remove  - Remove the entire stack"
        echo "  nodes   - Show swarm nodes"
        echo ""
        echo "Examples:"
        echo "  ./manage-stack.sh status"
        echo "  ./manage-stack.sh logs user-service"
        echo "  ./manage-stack.sh scale book-service 3"
        echo "  ./manage-stack.sh update loan-service"
        ;;
esac
