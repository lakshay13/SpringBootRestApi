FROM openjdk:8-jdk-alpine
MAINTAINER lakshay13@gmail.com
VOLUME /tmp
ADD target/SpringBootRestApi-1.0-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]