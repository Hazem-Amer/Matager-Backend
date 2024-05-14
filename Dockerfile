FROM amazoncorretto:17

MAINTAINER "Abdullah Sayed Ahmed Sallam"

EXPOSE 5009
EXPOSE 80
EXPOSE 443

COPY target/*.jar matager-backend.jar

ENTRYPOINT ["java", "-jar", "/matager-backend.jar"]