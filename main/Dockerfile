FROM maven:3.8.6-openjdk-18 AS build
WORKDIR /app
COPY . .
RUN mvn package -pl main -am

FROM amazoncorretto:18-alpine
WORKDIR /app
COPY --from=build /app/main/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]