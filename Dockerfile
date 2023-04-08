FROM openjdk:17
EXPOSE 8081
ADD target/*.jar universal-app-docker.jar
ENTRYPOINT ["java","-jar","/universal-app-docker.jar"]