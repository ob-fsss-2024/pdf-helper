FROM openjdk:22-slim

EXPOSE 80

RUN apt-get update
RUN apt-get install -y maven
RUN apt-get install -y wget


RUN wget https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-8.14.1-amd64.deb
RUN apt-get install -y ./filebeat-8.14.1-amd64.deb

COPY filebeat.yml /etc/filebeat/filebeat.yml

COPY . /app

RUN cd app && mvn clean package -DskipTests

ENTRYPOINT service filebeat start && java -jar -Dspring.profiles.active=prod /app/target/summer-school.jar






