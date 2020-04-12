# Jonas-Poker Java-Backend
Stomp broker for lobby and poker game management. Stores game state and broadcasts state changes to subscribed users. Sends secret messages (like pocket cards) to users privately.
Includes a lobby with chat and table overview.
## Run on localhost:9090
```
./mvnw clean spring-boot:run
```
## Build jar
```
./mvnw clean package
```
## Build docker image (dev build)
```
./mvnw clean package && docker build -t jonas-poker-java-backend .
```
## Production Build & push to ecr
```
docker build -t jonas-poker-java-backend -f Dockerfile.staged .
docker tag jonas-poker-java-backend 133315004561.dkr.ecr.eu-central-1.amazonaws.com/jonas-poker-java-backend
aws-vault exec jonas-frankfurt -- aws ecr get-login-password --region eu-central-1 | docker login --username AWS --password-stdin 133315004561.dkr.ecr.eu-central-1.amazonaws.com/jonas-poker-java-backend
docker push 133315004561.dkr.ecr.eu-central-1.amazonaws.com/jonas-poker-java-backend
```