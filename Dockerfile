FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package

FROM openjdk:17-jdk
COPY target/marsrobot-0.0.1-SNAPSHOT.jar /app/marsrobot.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "marsrobot.jar"]
