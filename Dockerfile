# Usa a imagem base do Java
FROM openjdk:17-jdk-slim

# Copia o JAR do projeto para dentro do container
COPY target/api-rest-0.0.1-SNAPSHOT.jar app.jar

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "/app.jar"]