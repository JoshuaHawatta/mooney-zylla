FROM openjdk:17-jdk-alpine

WORKDIR /usr/src

COPY target/*.jar ./target/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/app.jar"]