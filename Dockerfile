FROM openjdk:19-alpine
ARG JAR_FILE=out/artifacts/ServerComponent_jar/ServerComponent.jar
COPY ${JAR_FILE} ServerComponent.jar
ENTRYPOINT ["java","-jar","ServerComponent.jar"]