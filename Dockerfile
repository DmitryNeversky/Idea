FROM openjdk:17-jdk-alpine

EXPOSE 8080

RUN mkdir "/uploaded"

COPY target/app.jar /app.jar

ENTRYPOINT ["java"]
CMD ["-jar", "/app.jar"]