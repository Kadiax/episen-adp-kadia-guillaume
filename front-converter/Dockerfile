FROM maven:3.6.3-jdk-11-slim AS maven-jar-build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean install

FROM openjdk:13-jdk-alpine
COPY --from=maven-jar-build /usr/src/app/target/front-converter-0.0.1-RELEASE.jar /usr/app/front-converter-0.0.1-RELEASE.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/front-converter-0.0.1-RELEASE.jar"]
