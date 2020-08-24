FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD eureka-0.0.1-SNAPSHOT.jar eureka-0.0.1-SNAPSHOT.jar
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-javaagent:/var/lib/jenkins/workspace/eurekaServer/skywalking/skywalking-agent.jar","-Dskywalking.agent.service_name=eureka","-Dskywalking.collector.backend_service=34.207.156.185:11800","-jar","/eureka-0.0.1-SNAPSHOT.jar"]
