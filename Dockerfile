FROM openjdk:17-alpine3.14
WORKDIR /app
COPY target/app.jar  /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]