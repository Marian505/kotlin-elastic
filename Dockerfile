FROM maven:3-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -B package -DskipTests

FROM eclipse-temurin:17-jre-alpine
EXPOSE 8080
COPY --from=build /app/target/*.jar /app.jar
COPY --from=build /app/src/main/resources/application-k8s.properties /application-k8s.properties
ENV SPRING_CONFIG_LOCATION=file:/application-k8s.properties
ENTRYPOINT ["java", "-jar", "/app.jar"]