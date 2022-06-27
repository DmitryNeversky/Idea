FROM openjdk:8-jdk-alpine

RUN apk add --no-cache mutt
COPY muttrc ~/.muttrc

COPY target/emailer.jar /emailer.jar

ENTRYPOINT ["java"]
CMD ["-Dspring.profiles.active=prod", "-jar", "/emailer.jar"]