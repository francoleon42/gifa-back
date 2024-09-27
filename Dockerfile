FROM openjdk:17
VOLUME /temp
ENV IMG_PATH=/img
EXPOSE 8080
RUN mkdir -p /.img
ADD ./target/gifa_api-0.0.1-SNAPSHOT.jar gifa_api.jar
ENTRYPOINT ["java", "-jar", "/gifa_api.jar"]