FROM openjdk:8-jdk-alpine
LABEL maintainer="apriadchenko@gmail.com"
EXPOSE 8080
ARG JAR_FILE=target/test-app-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} test-app.jar
ENTRYPOINT ["java","-jar","/test-app.jar"]