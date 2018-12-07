FROM openjdk:8
ADD build/libs/docker-spring-rest-0.1.0.jar docker-spring-rest-0.1.0.jar
EXPOSE 9800
ENTRYPOINT ["java","-jar","docker-spring-rest-0.1.0.jar"]