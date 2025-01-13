FROM openjdk:8-jdk
VOLUME /tmp
COPY ./target/integratedHub-0.0.1-SNAPSHOT.jar integratedHub-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/integratedHub-0.0.1-SNAPSHOT.jar","&"]