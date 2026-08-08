# Build Stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
# Pointing Docker to the nested folder for both the pom and the source code
COPY director-ai/pom.xml .
COPY director-ai/src ./src
RUN mvn clean package -DskipTests

# Run Stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]