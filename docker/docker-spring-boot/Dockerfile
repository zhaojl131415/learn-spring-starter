FROM java:8

COPY target/docker-sample-1.0-SNAPSHOT.jar /docker-spring-boot.jar

CMD ["--server.port=8080"]

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/docker-spring-boot.jar"]