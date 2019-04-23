FROM anapsix/alpine-java:8_server-jre_unlimited

WORKDIR /var/app

ADD target/scala-2.12/wm4sq_2.12-1.0.jar /var/app/wm4sq.jar

EXPOSE 3339

CMD ["java", "-server -jar /var/app/wm4sq.jar"]