FROM openjdk:17-jdk-slim

WORKDIR /app

COPY . .

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

ENV SPRING_PROFILES_ACTIVE=prod

CMD ["java", "-jar", "target/Aurela-web-0.0.1-SNAPSHOT.jar"]
