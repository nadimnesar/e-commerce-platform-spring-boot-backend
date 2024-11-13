FROM gradle:8.8-jdk21 AS build
LABEL authors="nadimnesar"

WORKDIR /app

COPY --chown=gradle:gradle . .

RUN ./gradlew clean build -x test

FROM openjdk:21-jdk-slim

COPY --from=build /app/build/libs/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
