FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD transferbackend-0.0.1-SNAPSHOT.jar transferbackend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/transferbackend-0.0.1-SNAPSHOT.jar"]
