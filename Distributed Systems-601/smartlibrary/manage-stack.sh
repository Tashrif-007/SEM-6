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
            echo "📜 Available services: user-service, book-service, loan-service, nginx, mongo-user, mongo-book, mongo-loan"
            echo "Usage: ./manage-stack.sh logs <service-name>"
        else
            echo "📜 Logs for smartlibrary_$2:"
            docker service logs smartlibrary_$2
        fi
        ;;
    "scale")
        if [ -z "$2" ] || [ -z "$3" ]; then
            echo "⚖️ Usage: ./manage-stack.sh scale <service-name> <replicas>"
            echo "Available services: user-service, book-service, loan-service"
            echo "Note: MongoDB services should remain at 1 replica"
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
    "mongo")
        echo "🗄️ MongoDB Services Status:"
        echo "- User DB: smartlibrary_mongo-user (Port 27017)"
        echo "- Book DB: smartlibrary_mongo-book (Port 27018)" 
        echo "- Loan DB: smartlibrary_mongo-loan (Port 27019)"
        echo ""
        docker service ls | grep mongo
        ;;
    "volumes")
        echo "💾 MongoDB Data Volumes:"
        docker volume ls | grep -E "(user_data|book_data|loan_data)" || echo "No MongoDB volumes found"
        ;;
    "remove")
        echo "🗑️ Removing smartlibrary stack..."
        docker stack rm smartlibrary
        echo "✅ Stack removed successfully!"
        echo "💡 To remove MongoDB volumes as well, run:"
        echo "   docker volume rm smartlibrary_mongo_user_data smartlibrary_mongo_book_data smartlibrary_mongo_loan_data"
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
        echo "  mongo   - Show MongoDB services status"
        echo "  volumes - Show MongoDB data volumes"
        echo "  remove  - Remove the entire stack"
        echo "  nodes   - Show swarm nodes"
        echo ""
        echo "Examples:"
        echo "  ./manage-stack.sh status"
        echo "  ./manage-stack.sh logs user-service"
        echo "  ./manage-stack.sh logs mongo-user"
        echo "  ./manage-stack.sh scale book-service 3"
        echo "  ./manage-stack.sh update loan-service"
        echo "  ./manage-stack.sh mongo"
        echo "  ./manage-stack.sh volumes"
        ;;
esac
