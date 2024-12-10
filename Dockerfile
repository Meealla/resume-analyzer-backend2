# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the local code to the container
COPY . .

COPY gradlew gradlew
COPY gradle gradle
RUN ./gradlew build

# Install Gradle (optional if needed for your project)
RUN apt-get update && apt-get install -y gradle

# Build the application (assuming your build file is build.gradle)
RUN ./gradlew build

# Expose the port your app will run on
EXPOSE 8082

# Define the command to run your application


CMD ["java", "-jar", "build/libs/resume-analyzer-backend1-0.0.1-SNAPSHOT.jar"]