# BUILD STAGE
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# PACKAGE STAGE
FROM openjdk:17
COPY --from=build /app/target/gateway-0.0.1-SNAPSHOT.jar gateway.jar
EXPOSE 9000
CMD ["java","-jar","gateway.jar"]