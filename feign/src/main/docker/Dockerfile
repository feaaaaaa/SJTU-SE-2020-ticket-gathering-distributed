FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD auction-0.0.1-SNAPSHOT.jar auction-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/auction-0.0.1-SNAPSHOT.jar"]
