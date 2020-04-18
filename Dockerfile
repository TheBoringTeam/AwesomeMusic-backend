FROM adoptopenjdk/openjdk13:alpine
MAINTAINER holkerdev
VOLUME /tmp
EXPOSE 8080
ADD build/libs/awesome-music-1.0-SNAPSHOT.jar awesome-music.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/awesome-music.jar"]