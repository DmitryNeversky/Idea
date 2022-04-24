FROM openjdk:8-jdk-alpine

COPY target/notifier.jar /notifier.jar

ENTRYPOINT ["java"]
CMD ["-jar", "/notifier.jar"]