# Use OpenJDK as base image
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

RUN chmod +x mvnw && ./mvnw clean package -DskipTests

RUN cp target/DTS-developer-challenge-*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]