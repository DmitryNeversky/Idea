FROM openjdk:8-jdk-alpine

COPY target/idea.jar /idea.jar

ENTRYPOINT ["java"]
CMD ["-Dspring.profiles.active=prod", "-jar", "/idea.jar"]