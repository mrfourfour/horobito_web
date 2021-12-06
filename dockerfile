FROM openjdk:12
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=build/libs/demo-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} demo.jar

ENTRYPOINT ["java", "-jar", "/demo.jar"]