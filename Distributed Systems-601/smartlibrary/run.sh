#!/bin/bash

# Create docker network
docker network create smartlib-overlay 2>/dev/null || echo "Network smartlib-overlay already exists"

# Pull images from Docker Hub
docker pull tashrif2001/smartlibrary:user-service
docker pull tashrif2001/smartlibrary:book-service
docker pull tashrif2001/smartlibrary:loan-service
docker pull nginx:latest
docker pull mongo:latest

echo "🗄️ Starting MongoDB services..."

# Run MongoDB for user-service
docker run -d --name mongo-user \
  --network smartlib-net \
  -p 27017:27017 \
  -e MONGO_INITDB_DATABASE=userDB \
  -v mongo_user_data:/data/db \
  mongo:latest

# Run MongoDB for book-service
docker run -d --name mongo-book \
  --network smartlib-net \
  -p 27018:27017 \
  -e MONGO_INITDB_DATABASE=bookDB \
  -v mongo_book_data:/data/db \
  mongo:latest

# Run MongoDB for loan-service
docker run -d --name mongo-loan \
  --network smartlib-net \
  -p 27019:27017 \
  -e MONGO_INITDB_DATABASE=loanDB \
  -v mongo_loan_data:/data/db \
  mongo:latest

# Wait for MongoDB services to be ready
echo "⏳ Waiting for MongoDB services to start..."
sleep 10

echo "🚀 Starting application services..."

# Run user-service
docker run -d --name user-service \
  --network smartlib-net \
  -p 8081:8081 \
  -e MONGO_URL=mongodb://mongo-user:27017/userDB \
  tashrif2001/smartlibrary:user-service

# Run book-service
docker run -d --name book-service \
  --network smartlib-net \
  -p 8082:8082 \
  -e MONGO_URL=mongodb://mongo-book:27017/bookDB \
  tashrif2001/smartlibrary:book-service

# Run loan-service
docker run -d --name loan-service \
  --network smartlib-net \
  -p 8083:8083 \
  -e MONGO_URL=mongodb://mongo-loan:27017/loanDB \
  -e USER_SERVICE_URL=http://user-service:8081 \
  -e BOOK_SERVICE_URL=http://book-service:8082 \
  tashrif2001/smartlibrary:loan-service

# Run nginx (ensure default.conf exists)
if [ ! -f ./nginx/default.conf ]; then
  echo "Error: ./nginx/default.conf not found."
  exit 1
fi

docker run -d --name nginx \
  --network smartlib-net \
  -p 80:80 \
  -v "$(pwd)/nginx/default.conf:/etc/nginx/conf.d/default.conf:ro" \
  nginx:latest

echo "✅ All services are up and running on:"
echo "- MongoDB User DB: mongodb://localhost:27017/userDB"
echo "- MongoDB Book DB: mongodb://localhost:27018/bookDB"
echo "- MongoDB Loan DB: mongodb://localhost:27019/loanDB"
echo "- User Service: http://localhost:8081"
echo "- Book Service: http://localhost:8082"
echo "- Loan Service: http://localhost:8083"
echo "- NGINX Gateway: http://localhost"
