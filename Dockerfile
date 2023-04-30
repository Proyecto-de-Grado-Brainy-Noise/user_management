FROM maven:3.9.0-amazoncorretto-17 AS build
WORKDIR /app
COPY /src ./src
COPY /pom.xml .
COPY ./src/main/resources/email.properties /app/src/main/resources/email.properties
COPY ./src/main/resources/emails /app/src/main/resources/emails
RUN mvn clean package -D skipTests

FROM amazoncorretto:17.0.6-alpine3.17
WORKDIR /app
COPY --from=build /app/target/*.jar ./app.jar
EXPOSE ${SERVER_PORT}
CMD [ "java", "-jar", "./app.jar" ]