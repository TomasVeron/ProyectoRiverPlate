FROM adoptopenjdk:11-jre-hotspot
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=*.jar
ADD ${JAR_FILE} riverplatejuninapp.jar
ENTRYPOINT ["java","-jar","riverplatejuninapp.jar"]
                                                       
