# Smart Library - Docker Swarm Deployment Guide

This guide explains how to set up your PC as a Docker Swarm manager node and deploy the Smart Library microservices stack.

## 🏗️ Architecture Overview

The Smart Library system consists of:
- **User Service** (Port 8081) - Manages user operations
- **Book Service** (Port 8082) - Handles book management
- **Loan Service** (Port 8083) - Manages book loans
- **NGINX** (Port 80) - API Gateway and load balancer

## 🚀 Quick Start

### Step 1: Initialize Docker Swarm
```bash
./setup-swarm.sh
```

This script will:
- Initialize Docker Swarm on your PC (making it the manager node)
- Create overlay networks
- Pull required Docker images
- Generate join tokens for other nodes

### Step 2: Deploy the Stack
```bash
./deploy-stack.sh
```

This will deploy all services with:
- 2 replicas for each microservice (high availability)
- Automatic restart policies
- Resource limits and reservations
- Load balancing across replicas

## 📋 Docker Swarm vs Docker Compose

| Feature | Docker Compose | Docker Swarm |
|---------|----------------|---------------|
| **Orchestration** | Single host | Multi-host cluster |
| **High Availability** | No | Yes (multiple replicas) |
| **Load Balancing** | Basic | Built-in service discovery |
| **Scaling** | Manual | Dynamic scaling |
| **Rolling Updates** | No | Yes |
| **Service Discovery** | Host-based | Cluster-wide |

## 🔧 Management Commands

### Check Status
```bash
./manage-stack.sh status
```

### View Service Logs
```bash
./manage-stack.sh logs user-service
./manage-stack.sh logs book-service
./manage-stack.sh logs loan-service
```

### Scale Services
```bash
./manage-stack.sh scale user-service 3
./manage-stack.sh scale book-service 4
```

### Update Services
```bash
./manage-stack.sh update user-service
```

### Remove Stack
```bash
./manage-stack.sh remove
```

## 🌐 Adding Worker Nodes

After running `setup-swarm.sh`, you'll get join tokens. To add other machines as worker nodes:

1. On the worker machine, run the command from `worker-join-token.txt`:
```bash
docker swarm join --token SWMTKN-1-xxx... <manager-ip>:2377
```

2. Verify nodes from manager:
```bash
docker node ls
```

## 📊 Monitoring and Health Checks

### Service Status
```bash
docker service ls
docker stack ps smartlibrary
```

### Node Status
```bash
docker node ls
```

### Resource Usage
```bash
docker stats
```

### Service Logs
```bash
docker service logs smartlibrary_user-service
docker service logs smartlibrary_book-service --follow
```

## 🔄 Deployment Features

### High Availability
- Each service runs with 2 replicas
- Automatic failover if a container fails
- Services distributed across available nodes

### Rolling Updates
```bash
# Update service image
docker service update --image tashrif2001/smartlibrary:user-service-v2 smartlibrary_user-service

# Update with zero downtime
docker service update --update-parallelism 1 --update-delay 10s smartlibrary_user-service
```

### Scaling
```bash
# Scale up during high traffic
docker service scale smartlibrary_user-service=5

# Scale down during low traffic  
docker service scale smartlibrary_user-service=1
```

## 🛡️ Security Considerations

1. **Network Isolation**: Services communicate through encrypted overlay network
2. **Resource Limits**: Each service has memory limits to prevent resource exhaustion
3. **Restart Policies**: Automatic recovery from failures
4. **Manager-only Placement**: Critical services can be constrained to manager nodes

## 📈 Production Considerations

### Resource Management
```yaml
deploy:
  resources:
    limits:
      memory: 512M
      cpus: '0.5'
    reservations:
      memory: 256M
      cpus: '0.25'
```

### Health Checks
Add to your Dockerfile:
```dockerfile
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8081/health || exit 1
```

### Persistent Storage
For databases or persistent data:
```yaml
volumes:
  db-data:
    driver: local
```

## 🔍 Troubleshooting

### Service Not Starting
```bash
docker service ps smartlibrary_user-service --no-trunc
docker service logs smartlibrary_user-service
```

### Network Issues
```bash
docker network ls
docker network inspect smartlib-overlay
```

### Node Communication Issues
```bash
# Check node connectivity
docker node ls
docker node inspect <node-id>

# Check swarm status
docker info | grep Swarm
```

## 🌍 Access Points

After deployment:
- **User Service**: http://localhost:8081
- **Book Service**: http://localhost:8082
- **Loan Service**: http://localhost:8083
- **API Gateway**: http://localhost (NGINX)

## 📚 Useful Commands Reference

```bash
# Swarm management
docker swarm init --advertise-addr <ip>
docker swarm join-token worker
docker swarm leave --force

# Stack management
docker stack deploy -c docker-stack.yml smartlibrary
docker stack ls
docker stack rm smartlibrary

# Service management
docker service create --name web --replicas 3 nginx
docker service update --image nginx:alpine web
docker service rm web

# Node management
docker node ls
docker node update --availability drain <node-id>
docker node update --availability active <node-id>
```

## 🎯 Benefits of Swarm Mode

1. **High Availability**: Services continue running even if nodes fail
2. **Load Balancing**: Automatic load distribution across replicas
3. **Service Discovery**: Services can find each other by name
4. **Rolling Updates**: Zero-downtime deployments
5. **Scaling**: Easy horizontal scaling up/down
6. **Security**: Encrypted communication between nodes
7. **Management**: Centralized orchestration from manager nodes
