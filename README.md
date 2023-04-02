
# Rock, paper & scissors

This is the back-end of a 'rock, paper & scissors' game.

## How it works

First, the user will select one of the three available moves (*ROCK*, *PAPER* or *SCISSORS*) and lock its move for the current play.

Obviously at this point there cannot be another active play, and any attemps to restart the game will be revoked.

Once this is done, the play is revealed simultaneously for both players and the winner is decided.

## API



## Installation

Setup the required infrastructure using the following command on a running Docker:
```
docker-compose up -d
```
This will start a container for Redis, which is used to store the user's move before the simultaneous reveal.

## Testing

Setup the required infrastructure using the following command on a running Docker:
```
docker-compose -f docker-compose-test.yml up -d
```
This will start a container for the Pack Broker (where pacts for contract testing will be stored) and its required DB.
