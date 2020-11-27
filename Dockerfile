FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/library.jar library.jar
ENTRYPOINT ["java","-jar","/library.jar", "&"]