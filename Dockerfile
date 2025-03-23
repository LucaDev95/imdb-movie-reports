FROM openjdk:21-jdk-slim

# Impostare la directory di lavoro nell'immagine di runtime
WORKDIR /app

COPY target/*.jar app.jar

# Comando per eseguire l'applicazione Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]

EXPOSE 8080