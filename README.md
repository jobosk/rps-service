
# Rock, paper & scissors

This is the back-end of a 'rock, paper & scissors' game.

## About

This service aims to be the provider for the endpoints in the following [API definition](https://github.com/jobosk/rps-api).

The approach to develop this service was API-first, which implies that both the front-end and the back-end that consume and provide this API respectively, could be implemented in parallel.

In this case, that means we could implement the endpoints specified, and the corresponding contract tests, using tools like [Pact](https://pact.io/), to validate those against the API definition itself.

On top of that, to avoid infrastructure requirements, these tests will be executed on a ephemeral testing environment, with the help of [Testcontainers](https://www.testcontainers.org/).

It is worth mentioning that this service is compatible with multiple clients ([front-end](https://github.com/jobosk/rps-front) aplications) simultaneously. This means that plays from each one will be resolved independently, as long as they provide different IDs in their respective requests.

## Requirements

This project requires some infrastructure to run (and be tested). To satisfy those requirements, we've used a [Docker Compose](https://docs.docker.com/compose/) script, which means that it needs to be installed together with your [Docker](https://www.docker.com/) installation.
The script itself is not required, as long as you locally deploy the same infrastructure described in the YAML files for the [running](https://github.com/jobosk/rps-service/blob/master/docker-compose.yaml) and [testing](https://github.com/jobosk/rps-service/blob/master/docker-compose-test.yaml) environments. However Docker actually **is**, since it is used by the tests to mock said infrastructure.

It also expects [Maven](https://maven.apache.org/install.html) and the [JDK 11](https://docs.oracle.com/en/java/javase/11/install/overview-jdk-installation.html) (at least) to be installed.

## Installation

Setup the required infrastructure using the following command on a running Docker environment:
```
docker-compose up -d
```
This will start a container for Redis, which is used to store the user's move before the simultaneous reveal.

And then, start the back-end service with the following command:
```
mvn clean spring-boot:run
```
This will run the service using Spring Boot in the current terminal.

### Cleanup

Once the service has stopped, you can remove the required infrastructure with:
```
docker-compose down
```

## Testing

Setup the required infrastructure using the following command on a running Docker environment:
> **Note:** This might take over 30 seconds, since the Pact Broker container will not start until the DB one is healthy.
```
docker-compose -f docker-compose-test.yaml up -d
```
This will start a container for the Pack Broker (where pacts for contract testing will be stored) and its required DB.

And then, run the tests with the following command:
```
mvn clean test
```
> **Note:** The contract test from [the front-end app](https://github.com/jobosk/rps-front) should've run first, in order to create the consumer pact that the back-end contract test uses to validate itself against the specification. Otherwise, the test will run successfully but no contract would have been validated. This is the chosen behaviour for the current exercise, but it could be changed to fail if no tests are found for this provider.

### Cleanup

Once the tests are done, you can remove the required infrastructure with:
```
docker-compose -f docker-compose-test.yaml down
```

## Front-end support

In case you just want to get over with the back-end setup in order to run [the front-end app](https://github.com/jobosk/rps-front), you can also run the following:
```
docker-compose -f docker-compose-back.yaml up -d
```
This will start one container with Redis and another one with the back-end service itself.
> **Note:** The setup for the latter will run mostly synchronously during the container's creation, but the plugin to start the Spring Boot application will run asynchronously after the container has been successfully created. This means that requests to the back-end immediately after Docker Compose has finished executing the script might not work, until the plugin's execution is complete (which takes around 20 seconds in a background process).

### Cleanup

Once the back-end service is no longer needed, you can remove it together with the required infrastructure with:
```
docker-compose -f docker-compose-back.yaml down
```
