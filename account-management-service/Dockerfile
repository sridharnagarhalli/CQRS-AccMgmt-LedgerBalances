FROM bellsoft/liberica-openjdk-alpine-musl:17

WORKDIR /app

COPY target/account-management-service-0.0.1-SNAPSHOT.jar /app/account-management-service-0.0.1-SNAPSHOT.jar

EXPOSE 9281

ENTRYPOINT ["java","-jar","/account-management-service-0.0.1-SNAPSHOT.jar"]