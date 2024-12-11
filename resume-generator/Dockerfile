FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy necessary files: gradle wrapper and the application source code
COPY gradlew gradlew
COPY gradle gradle
COPY . .

# Give execution permission to the gradle wrapper and run the build
RUN chmod +x gradlew && ./gradlew build

# Expose the application port
EXPOSE 8082

# Command to run the application
CMD ["java", "-jar", "build/libs/resume-analyzer-backend1-0.0.1-SNAPSHOT.jar"]