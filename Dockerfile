FROM adoptopenjdk/openjdk11:latest
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} uiapp.jar
ENTRYPOINT ["java","-jar","/uiapp.jar","-Dvaadin.productionMode"]
