FROM openjdk:21
ARG JAR_FILE=api-service/build/libs/api-service.jar
COPY ${JAR_FILE} ./app.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "./app.jar"]