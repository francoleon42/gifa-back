# Stage 1: Build
FROM openjdk:17-jdk AS build

# Setting working directory
WORKDIR /app

# Copy the pom.xml and download dependencies before copying the source code
# This step is important to take advantage of Docker caching.
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create Runtime Image
FROM openjdk:17-jdk-slim

# Set a non-root user to run the application
RUN useradd -m appuser

# Set the working directory
WORKDIR /app

# Define environment variables
ENV IMG_PATH=/img
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Copy the JAR file from the previous stage
COPY --from=build /app/target/gifa_api-0.0.1-SNAPSHOT.jar /app/gifa_api.jar

# Create necessary volumes and directories
VOLUME /temp
RUN mkdir -p /img

# Set the ARG to receive the value at build time
ARG JWT_SECRET_KEY
# Set the environment variable using the ARG value
ENV JWT_SECRET_KEY=${JWT_SECRET_KEY}

# Expose the necessary port
EXPOSE 8080

# Use the non-root user created earlier
USER appuser

# Run the application
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -DIMG_PATH=${IMG_PATH} -DJWT_SECRET_KEY=${JWT_SECRET_KEY} -jar /app/gifa_api.jar"]
