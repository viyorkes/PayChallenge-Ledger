# Use a lightweight base image with OpenJDK 17 installed
FROM alpine:latest

# Install OpenJDK 17
RUN apk --no-cache add openjdk17

# Optionally add a volume if your application needs to write to /tmp
VOLUME /tmp

# Expose the port your app runs on
EXPOSE 8080

# Add your application's jar to the container
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Specify the Java options and run the jar file
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]


