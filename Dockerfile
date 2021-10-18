FROM alpine

RUN apk add openjdk8

EXPOSE 8080

COPY target/app.jar /app.jar

CMD ["java", "-jar", "/app.jar"]