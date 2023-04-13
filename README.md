
# Rock, paper & scissors

This is the back-end of a 'rock, paper & scissors' game.

## About

This service aims to be the provider for the endpoints in the following [API definition](https://github.com/jobosk/rps-api).

The approach to develop of this service was API-first, which implies that both the front-end and the back-end that consume and provide this API respectively, could be implemented in parallel.

In this case, that means we could implement the endpoints specified, and the corresponding contract tests, using with tools like [Pact](https://pact.io/), to validate those against the API definition itself.

On top of that, to avoid infrastructure requirements, these tests will be executed on a ephemeral testing environment, with the help of [Testcontainers](https://www.testcontainers.org/).

## Requirements

This project requires some infrastructure to run (and be tested). To satisfice those requirements, we've used a [Docker Compose](https://docs.docker.com/compose/) script, which means that it needs to be installed together with your [Docker](https://www.docker.com/) installation.
The script itself is not required, as long as you locally deploy the same infrastructure described in the YML files for the [running](https://github.com/jobosk/rps-service/blob/master/docker-compose.yaml) and [testing](https://github.com/jobosk/rps-service/blob/master/docker-compose-test.yaml) environments. However Docker actually **is**, since it is used by the tests to mock said insfrastructure.

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

## Testing

Setup the required infrastructure using the following command on a running Docker environment:
```
docker-compose -f docker-compose-test.yml up -d
```
This will start a container for the Pack Broker (where pacts for contract testing will be stored) and its required DB.

And then, run the tests with the following command:
```
mvn clean test
```
> **Note:** The contract test from [the front-end app](https://github.com/jobosk/rps-front) should've run first, in order to create the consumer pact that the back-end contract test uses to validate itself against the specification. Otherwise, the test will run successfully but no contract would have been validated. This is the chosen behaviour for the current exercise, but it could be changed to fail if no tests are found for this provider.
