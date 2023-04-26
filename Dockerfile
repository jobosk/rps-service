FROM maven:3.6.3 AS maven
WORKDIR /source
COPY . /source
RUN mvn clean package -DskipTests dependency:resolve-plugins
CMD mvn spring-boot:run