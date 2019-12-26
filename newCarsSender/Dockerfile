FROM openjdk:11-oracle
COPY Sender.jar .
COPY target/uber/ target/uber/
CMD ["java", "-cp", "Sender.jar:target/uber/*", "tkhal.sender.controller.NewCarsSender"]