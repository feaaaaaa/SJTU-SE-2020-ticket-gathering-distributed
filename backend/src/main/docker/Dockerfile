FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD ticketgathering-0.0.1-SNAPSHOT.jar ticketgathering-0.0.1-SNAPSHOT.jar
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-javaagent:/data/skywalking-agent.jar","-Dskywalking.agent.service_name=ticketgathering","-Dskywalking.collector.backend_service=10.0.0.51:11800","-jar","/ticketgathering-0.0.1-SNAPSHOT.jar"]
