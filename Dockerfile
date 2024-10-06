FROM openjdk:17
VOLUME /temp
ENV IMG_PATH=/img

# Define the ARG to receive the value at build time
ARG JWT_SECRET_KEY
# Set the environment variable using the ARG value
ENV JWT_SECRET_KEY=${JWT_SECRET_KEY}

EXPOSE 8080
RUN mkdir -p /.img
ADD ./target/gifa_api-0.0.1-SNAPSHOT.jar gifa_api.jar
ENTRYPOINT ["java", "-jar", "/gifa_api.jar"]
