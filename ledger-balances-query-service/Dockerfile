FROM bellsoft/liberica-openjdk-alpine-musl:17

WORKDIR /app

COPY target/ledger-balances-query-service-0.0.1-SNAPSHOT.jar /app/ledger-balances-query-service.jar

EXPOSE 8281

ENTRYPOINT ["java","-jar","/ledger-balances-query-service.jar"]