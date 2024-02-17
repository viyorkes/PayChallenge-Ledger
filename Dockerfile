# Use a base JDK image from Docker Hub
FROM alpine:latest

RUN apk --no-cache add openjdk17

# Add a volume pointing to /tmp
VOLUME /tmp

# Expose the port the app runs on
EXPOSE 8080

# Add the application's jar to the container
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
