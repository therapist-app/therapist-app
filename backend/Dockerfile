FROM gradle:8.5-jdk21 AS builder

WORKDIR /app

COPY gradle gradle
COPY gradlew .
COPY build.gradle settings.gradle ./

RUN chmod +x ./gradlew
RUN ./gradlew dependencies --no-daemon

COPY src src
RUN ./gradlew build --no-daemon

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar /app/

ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080

CMD ["sh", "-c", "java -jar /app/*.jar"]
