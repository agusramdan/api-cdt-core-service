FROM eclipse-temurin:21-jre-alpine
ENV JDK_JAVA_OPTIONS="-Xms256m -Xmx512m"
VOLUME /tmp
ADD target/*.jar /app.jar
HEALTHCHECK --interval=30s --timeout=3s --retries=1 CMD wget -qO- http://localhost:7180/actuator/health/readinessState | grep UP || exit 1
ENTRYPOINT ["java","-jar","/app.jar"]