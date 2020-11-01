FROM adoptopenjdk:11-jre-openj9
EXPOSE 7070
ADD /build/libs/scrabble-0.0.1-SNAPSHOT.jar scrabble.jar
ENTRYPOINT ["java", "-jar", "scrabble.jar"]
