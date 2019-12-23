FROM maven:3-jdk-11-openj9 as builder
WORKDIR /opt/app

COPY . /opt/app
ENV MAVEN_OPTS="-Xmx1024m"
RUN mvn clean package -DskipTests

FROM openjdk:11.0.5-jre

WORKDIR /opt/app

COPY --from=builder /opt/app/target/store-api.jar /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch store-api.jar'
ENTRYPOINT ["java","-jar","store-api.jar"]
