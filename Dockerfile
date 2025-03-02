FROM alpine:latest

RUN apk add --no-cache openjdk21-jre

WORKDIR /dev

ENV JAR_NAME=refresh-api.jar

COPY target/$JAR_NAME $JAR_NAME

ENTRYPOINT java -jar $JAR_NAME