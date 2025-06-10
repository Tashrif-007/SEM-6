#!/bin/bash

# Create docker network
docker network create smartlib-net 2>/dev/null || echo "Network smartlib-net already exists"

# Pull images from Docker Hub
docker pull tashrif2001/smartlibrary:user-service
docker pull tashrif2001/smartlibrary:book-service
docker pull tashrif2001/smartlibrary:loan-service
docker pull nginx:latest

# Run user-service
docker run -d --name user-service \
  --network smartlib-net \
  -p 8081:8081 \
  tashrif2001/smartlibrary:user-service

# Run book-service
docker run -d --name book-service \
  --network smartlib-net \
  -p 8082:8082 \
  tashrif2001/smartlibrary:book-service

# Run loan-service
docker run -d --name loan-service \
  --network smartlib-net \
  -p 8083:8083 \
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
echo "- User Service: http://localhost:8081"
echo "- Book Service: http://localhost:8082"
echo "- Loan Service: http://localhost:8083"
echo "- NGINX Gateway: http://localhost"
