FROM adoptopenjdk:14-jdk

RUN [ "apt-get" , "update" ]

RUN [ "apt-get", "install", "-y", "maven" ]

WORKDIR /catcher/

COPY src ./src

COPY pom.xml .

RUN [ "mvn", "clean"]

RUN [ "mvn", "package" ]

WORKDIR /catcher/

CMD [ "java", "-jar", "target/discord-catcher-DEV-ALPHA-with-dependencies.jar" ]





