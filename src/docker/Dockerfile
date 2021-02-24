FROM openjdk:15-jdk-alpine

ARG JAR_FILE_NAME
ARG SRC_DIR
ARG jar="./build/libs/${JAR_FILE_NAME}"

COPY ${SRC_DIR} .
RUN ./gradlew build

RUN mv ${jar} /app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar", "/app.jar"]