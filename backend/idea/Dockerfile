FROM alpine

RUN apk add --no-cache openjdk8-jre

COPY target/idea.jar /idea.jar

ENTRYPOINT ["java"]
CMD ["-Dspring.profiles.active=prod", "-jar", "/idea.jar"]