# Usa una imagen base de Ubuntu con OpenJDK 21
FROM ubuntu:22.04

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Instalar Java 21 y las dependencias necesarias para OpenCV
RUN apt-get update && apt-get install -y \
    openjdk-21-jdk \
    libc6 \
    && apt-get clean

# Copia el archivo JAR de la aplicación y la librería local de OpenCV
COPY /target/app.jar app.jar


# Expone el puerto en el que la aplicación escucha (por ejemplo, 8080)
EXPOSE 8080

# Comando para iniciar la aplicación
CMD ["java", "-jar", "/app/app.jar"]