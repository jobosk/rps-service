package com.jobosk.rps.util;

import com.jobosk.rps.model.MoveCodeEnum;
import com.jobosk.rps.model.OutcomeCodeEnum;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;
import java.util.Random;

@Log4j2
public class EnumUtil {

    public static Optional<MoveCodeEnum> getMove(final String moveCode) {
        if (moveCode != null) {
            try {
                return Optional.of(MoveCodeEnum.fromValue(moveCode));
            } catch (final Exception e) {
                log.error("error_parsing_move_code", e);
            }
        }
        return Optional.empty();
    }

    public static MoveCodeEnum getRandomMove() {
        final MoveCodeEnum[] moves = MoveCodeEnum.values();
        return moves[new Random().nextInt(moves.length)];
    }

    public static OutcomeCodeEnum getOutcome(final MoveCodeEnum moveByUser, final MoveCodeEnum moveByMachine) {
        if (moveByUser != moveByMachine) {
            switch (moveByUser) {
                case ROCK:
                    switch (moveByMachine) {
                        case SCISSORS:
                        case LIZARD:
                            return OutcomeCodeEnum.USER_WINS;
                    }
                    return OutcomeCodeEnum.MACHINE_WINS;
                case PAPER:
                    switch (moveByMachine) {
                        case ROCK:
                        case SPOCK:
                            return OutcomeCodeEnum.USER_WINS;
                    }
                    return OutcomeCodeEnum.MACHINE_WINS;
                case SCISSORS:
                    switch (moveByMachine) {
                        case PAPER:
                        case LIZARD:
                            return OutcomeCodeEnum.USER_WINS;
                    }
                    return OutcomeCodeEnum.MACHINE_WINS;
                case LIZARD:
                    switch (moveByMachine) {
                        case PAPER:
                        case SPOCK:
                            return OutcomeCodeEnum.USER_WINS;
                    }
                    return OutcomeCodeEnum.MACHINE_WINS;
                case SPOCK:
                    switch (moveByMachine) {
                        case ROCK:
                        case SCISSORS:
                            return OutcomeCodeEnum.USER_WINS;
                    }
                    return OutcomeCodeEnum.MACHINE_WINS;
            }
        }
        return OutcomeCodeEnum.TIE;
    }
}
