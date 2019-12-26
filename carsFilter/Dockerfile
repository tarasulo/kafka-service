FROM openjdk:11-oracle
COPY Filter.jar .
COPY target/uber/ target/uber/
CMD ["java", "-cp", "Filter.jar:target/uber/*", "tkhal.filter.controller.MessageFilterController"]