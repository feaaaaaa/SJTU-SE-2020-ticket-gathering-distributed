FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD transfer-backend-0.0.1-SNAPSHOT.jar transfer-backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/transfer-backend-0.0.1-SNAPSHOT.jar"]
