FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD gateway-0.0.1-SNAPSHOT.jar gateway-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/gateway-0.0.1-SNAPSHOT.jar"]
