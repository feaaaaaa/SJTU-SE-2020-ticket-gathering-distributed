FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD eureka-0.0.1-SNAPSHOT.jar eureka-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/eureka-0.0.1-SNAPSHOT.jar"]
