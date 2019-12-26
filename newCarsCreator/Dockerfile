FROM openjdk:11-oracle
COPY CarFactoryServer.jar .
COPY target/uber/ target/uber/
CMD ["java", "-cp", "CarFactoryServer.jar:target/uber/*", "tkhal.cars.creator.controller.NewCarsCreator"]