# Stage 1: Build con Maven
# Stage 1: Builder con Java 21 e Maven
FROM openjdk:21-jdk-slim AS builder

# Installa Maven
RUN apt-get update && apt-get install -y maven

WORKDIR /app

# Copia pom.xml e la cartella src per sfruttare la cache
COPY pom.xml .
COPY src ./src

# Esegui la build del progetto (saltando i test se desiderato)
RUN mvn clean package -DskipTests

# Stage 2: Immagine finale con OpenJDK 21
FROM openjdk:21-jdk-slim

WORKDIR /app

# Copia il JAR generato dallo stage builder
COPY --from=builder /app/target/*.jar imdb-movie-reports.jar

# Imposta la variabile d'ambiente per il profilo Spring
ENV SPRING_PROFILES_ACTIVE=docker

# Comando di avvio
ENTRYPOINT ["java", "-jar", "imdb-movie-reports.jar"]

