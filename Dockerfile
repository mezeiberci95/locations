#FROM openjdk:17-oracle
#RUN mkdir /opt/app
#COPY target/locations-0.0.1-SNAPSHOT.jar /opt/app/locations.jar
#CMD ["java", "-jar", "/opt/app/locations.jar"]

FROM openjdk:17-alpine as builder
WORKDIR application
COPY target/locations-0.0.1-SNAPSHOT.jar locations.jar
RUN java -Djarmode=layertools -jar locations.jar extract

FROM openjdk:17-alpine
WORKDIR application
RUN apk update \
    && apk add --no-cache wget \
    && apk add --no-cache netcat-openbsd \
    && wget https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh
COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", \
  "org.springframework.boot.loader.JarLauncher"]