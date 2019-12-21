FROM openjdk:11.0.5-jre

COPY ./target/store-api.jar /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch store-api.jar'
ENTRYPOINT ["java","-jar","store-api.jar"]