# Run Frontend and backend together:

This will build and serve on localhost:9090:

    ./mvnw clean install && ./mvnw --projects java-backend spring-boot:run


# Run FE separately
This will serve on localhost:8180 and use the backend at localhost:9090, which you can run with the above commands, or any other way (e.g. debug mode in VSCode).

    cd frontend
    npm run serve

# Heroku
The Procfile contains the command for starting the spring boot app (which contains the frontend).

    git push heroku master

# TODO

github actions to build and push to heroku