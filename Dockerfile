# MAVEN IMAGE CONFIGURATIONS
FROM maven:3.8.1-openjdk-17 AS build

WORKDIR /usr/src

COPY pom.xml .

#MAKE ALL DEPENDENCIES DOWNLAOD TO ENSURE DOCKER CACHE
RUN mvn dependency:go-offline

COPY . ./

RUN mvn clean package

FROM openjdk:17-jdk-alpine

WORKDIR /usr/src

#COPY THE BUILDED PROJECT ON MAVEN IMAGE
COPY --from=build /usr/src/target/*.jar ./app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
