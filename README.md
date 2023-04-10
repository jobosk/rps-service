
# Rock, paper & scissors

This is the back-end of a 'rock, paper & scissors' game.

## How it works

First, the user will select one of the three available moves (*ROCK*, *PAPER* or *SCISSORS*) and lock its move for the current play.

Obviously at this point there cannot be another active play, and any attemps to restart the game will be revoked.

Once this is done, the play is revealed simultaneously for both players and the winner is decided.

## API

### Play a move

This action will lock the player's move to start a new play. If there was already another ongoing play or the selected move is not a valid one, the action will be revoked.

**POST** `/play/<move>`
```
No body
```

Headers:
> **X-USER-ID**: *UUID v4 of the current player*

### Resolve active play

This action will resolve the current play (if there is one), and will determine if either the player or the machine won (or if it's a tie), based on the move previously selected by the player.

**GET** `/play/reveal`

Headers:
> **X-USER-ID**: *UUID v4 of the current player*

## Installation

Setup the required infrastructure using the following command on a running Docker:
```
docker-compose up -d
```
This will start a container for Redis, which is used to store the user's move before the simultaneous reveal.

And then, start the back-end service with the following command:
```
mvn clean spring-boot:run
```

## Testing

Setup the required infrastructure using the following command on a running Docker:
```
docker-compose -f docker-compose-test.yml up -d
```
This will start a container for the Pack Broker (where pacts for contract testing will be stored) and its required DB.

And then, run the tests with the following command:
> **Note:** The contract test from [the front-end app](https://github.com/jobosk/rps-front) should've run first, in order to create the consumer pact that the back-end contract test uses to validate itself against the specification.
```
mvn clean test
```
