FROM openjdk:17-jdk

#ADD ./build/libs/*.jar app.jar

ADD ./*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]