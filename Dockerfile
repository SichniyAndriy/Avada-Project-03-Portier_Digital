# syntax=docker/dockerfile:1

FROM tomcat:11-jdk17-temurin
LABEL authors="Andriy Sichniy"

COPY ./target/PortierDigital-SichniyA.war \
     /usr/local/tomcat/webapps/PortierDigital-SichniyA.war
COPY ./context.xml /usr/local/tomcat/conf/context.xml
VOLUME /apache-tomcat-11.0.4/webapps/resources/:/usr/local/tomcat/webapps/resources/

EXPOSE 8088

ENTRYPOINT ["catalina.sh", "run"]
