FROM eclipse-temurin:21-jre-alpine
ENV JDK_JAVA_OPTIONS="-Xms256m -Xmx512m"
RUN apk --no-cache add bash curl
VOLUME /tmp
ADD target/*.jar /app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]