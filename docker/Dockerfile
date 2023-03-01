FROM openjdk:19
VOLUME /tmp
COPY target/*.jar app.jar
EXPOSE 9000
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=dev","/app.jar"]
