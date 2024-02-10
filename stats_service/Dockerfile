FROM maven:3.8.1-openjdk-11 AS client-build
COPY stats-client /app
WORKDIR /app

FROM maven:3.8.1-openjdk-11 AS dto-build
COPY stats-dto /app
WORKDIR /app

FROM maven:3.8.1-openjdk-11 AS server-build
COPY stats-server /app
WORKDIR /app

FROM amazoncorretto:11
COPY --from=client-build /app/target/*.jar /app/client.jar
COPY --from=dto-build /app/target/*.jar /app/dto.jar
COPY --from=server-build /app/target/*.jar /app/server.jar
ENTRYPOINT ["java", "-jar", "/app/server.jar"]
