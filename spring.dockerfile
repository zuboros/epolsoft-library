FROM openjdk:11

VOLUME [ "/bookCollection" ]
VOLUME [ "/data/postgres-librarydb" ]

ARG JAR_FILE=epolsoft-backend/build/libs/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]