FROM java:8-jre
WORKDIR usr/src
ENV MYSQL_DATABASE=qnadb
ENV MYSQL_USER=app_root
ENV MYSQL_PASSWORD=root123
ENV MYSQL_CI_URL=jdbc:mysql://localhost:3306/qnadb
ADD ./target/QnAApi-1.0.jar /usr/src/QnAApi-1.0.0.RELEASE.jar
ENTRYPOINT ["java","-jar","QnAApi-1.0.0.RELEASE.jar"]