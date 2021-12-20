FROM openjdk:8-jdk-alpine

VOLUME /tmp

COPY target/userservice-1.0.jar UserService.jar

ENTRYPOINT ["java","-jar","UserService.jar"]


