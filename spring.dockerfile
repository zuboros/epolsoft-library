FROM openjdk:11

RUN mkdir -p ./bookCollection

VOLUME [ "/bookCollection" ]

ARG JAR_FILE=epolsoft-backend/build/libs/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
