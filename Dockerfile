FROM openjdk:17
WORKDIR /app

COPY /build/libs/test-0.0.1-SNAPSHOT.jar build/

WORKDIR /app/build
EXPOSE 8080
ENTRYPOINT java -jar test-0.0.1-SNAPSHOT.jar