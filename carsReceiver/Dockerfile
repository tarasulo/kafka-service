FROM openjdk:11-oracle
COPY Consumer.jar .
COPY target/uber/ target/uber/
CMD ["java", "-cp", "Consumer.jar:target/uber/*", "tkhal.receiver.controller.CarStandardizerController"]