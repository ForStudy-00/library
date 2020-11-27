FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/library-0.0.1-SNAPSHOT.jar library-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/library-0.0.1-SNAPSHOT.jar", "&"]