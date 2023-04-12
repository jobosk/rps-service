
# Rock, paper & scissors

This is the back-end of a 'rock, paper & scissors' game.

## About

This service aims to be the provider for the endpoints in the following [API definition](https://github.com/jobosk/rps-api).

The approach to develop of this service was API-first, which implies that both the front-end and the back-end that consume and provide this API respectively, could be implemented in parallel.

In this particular service, we would need to implement the endpoints specified and those would be tested against a contract based on the API definition, using tools like [Pact](https://pact.io/).

On top of that, to avoid infrastructure requirements, these tests will be executed on a ephemeral testing environment, with the help of [Testcontainers](https://www.testcontainers.org/).

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
> **Note:** The contract test from [the front-end app](https://github.com/jobosk/rps-front) should've run first, in order to create the consumer pact that the back-end contract test uses to validate itself against the specification.
```
mvn clean test
```
