FROM gradle:6.8.3-jre11-openj9 as build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

RUN gradle --no-daemon --console=plain test build

FROM adoptopenjdk/openjdk11-openj9:alpine

ENV POSTGRESQL_URL="jdbc:postgresql://postgresql-server/demoapp" POSTGRESQL_USERNAME="demoapp" POSTGRESQL_PASSWORD="demoapp"

RUN addgroup -S demoapp && adduser -S demoapp -G demoapp \
    && mkdir /app \
    && chown demoapp.demoapp /app

USER demoapp

COPY --from=build /home/gradle/src/build/libs/*.jar /app/demo-app.jar

EXPOSE 8080

CMD ["java","-jar","/app/demo-app.jar"]