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
        if (moveByUser == moveByMachine) {
            return OutcomeCodeEnum.TIE;
        }
        if (moveByUser == MoveCodeEnum.ROCK) {
            return moveByMachine == MoveCodeEnum.SCISSORS ? OutcomeCodeEnum.USER_WINS : OutcomeCodeEnum.MACHINE_WINS;
        }
        if (moveByUser == MoveCodeEnum.PAPER) {
            return moveByMachine == MoveCodeEnum.ROCK ? OutcomeCodeEnum.USER_WINS : OutcomeCodeEnum.MACHINE_WINS;
        }
        return moveByMachine == MoveCodeEnum.PAPER ? OutcomeCodeEnum.USER_WINS : OutcomeCodeEnum.MACHINE_WINS;
    }
}
