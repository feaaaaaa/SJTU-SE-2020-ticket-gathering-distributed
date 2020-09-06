FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD gateway-0.0.1-SNAPSHOT.jar gateway-0.0.1-SNAPSHOT.jar
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-javaagent:/data/skywalking-agent.jar","-Dskywalking.agent.service_name=gateway","-Dskywalking_config=/data/agent.config","-Dskywalking.collector.backend_service=172.17.0.1:30335","-jar","/gateway-0.0.1-SNAPSHOT.jar"]
