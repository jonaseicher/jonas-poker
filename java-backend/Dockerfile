FROM openjdk:11

RUN adduser --system --group spring
USER spring:spring

COPY target/*.jar app.jar

EXPOSE 8280

ENTRYPOINT ["java","-jar","/app.jar"]
