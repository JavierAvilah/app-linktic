
FROM ubuntu:22.04


WORKDIR /app


RUN apt-get update && apt-get install -y \
    openjdk-21-jdk \
    libc6 \
    && apt-get clean


COPY /target/app.jar app.jar



EXPOSE 8080


CMD ["java", "-jar", "/app/app.jar"]