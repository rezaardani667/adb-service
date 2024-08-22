FROM openjdk:21-jdk
ARG JAR_FILE=*.jar
COPY target/AdbService-0.0.1-SNAPSHOT.jar AdbService-0.0.1-SNAPSHOT.jar
EXPOSE 8086
ENTRYPOINT [\
"java", \
"-jar", \
"AdbService-0.0.1-SNAPSHOT.jar" \
]