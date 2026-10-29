# --- Estágio 1: Build Maven ---
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# --- Estágio 2: Execução leve (JRE 21) ---
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
EXPOSE 8080
COPY --from=build /app/target/*.jar publisher-manager.jar
ENTRYPOINT ["java", "-jar", "publisher-manager.jar"]
