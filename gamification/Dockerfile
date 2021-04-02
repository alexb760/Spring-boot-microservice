#Build image:
# docker build --build-arg JAR_FILE=target/\*.jar -t springio/gs-spring-boot-docker .
# run the image:
# run -p 8080:8080 springio/gs-spring-boot-docker
# in detach mode -- background:
# run -d -p 8080:8080 springio/gs-spring-boot-docker
FROM openjdk:15-jdk-alpine
# Adding a specific user for the execution
RUN addgroup -S game && adduser -S game -G game
USER game:game
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
