FROM adoptopenjdk/openjdk11:latest
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} apup.jar
ENTRYPOINT ["java","-jar","/apup.jar","-Dvaadin.productionMode"]
