FROM openjdk:11-slim as builder

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN chmod +x ./gradlew
RUN ./gradlew bootjar

FROM openjdk:11-slim

COPY --from=builder build/libs/*.jar accounts-0.0.1-SNAPSHOT.jar
VOLUME /tmp
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/accounts-0.0.1-SNAPSHOT.jar"]