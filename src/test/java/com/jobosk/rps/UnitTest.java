package com.jobosk.rps;

import com.jobosk.rps.model.MoveCodeEnum;
import com.jobosk.rps.model.OutcomeCodeEnum;
import com.jobosk.rps.service.PlayService;
import com.jobosk.rps.util.EnumUtil;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Import({
        PlayService.class
})
public class UnitTest {

    @Test
    public void getMove_isOk() {
        assertEquals(Optional.of(MoveCodeEnum.ROCK), EnumUtil.getMove("ROCK"));
        assertEquals(Optional.of(MoveCodeEnum.PAPER), EnumUtil.getMove("PAPER"));
        assertEquals(Optional.of(MoveCodeEnum.SCISSORS), EnumUtil.getMove("SCISSORS"));
        assertEquals(Optional.of(MoveCodeEnum.LIZARD), EnumUtil.getMove("LIZARD"));
        assertEquals(Optional.of(MoveCodeEnum.SPOCK), EnumUtil.getMove("SPOCK"));
        assertEquals(Optional.empty(), EnumUtil.getMove("OTHER_MOVE"));
    }

    @Test
    public void getOutcome_isTie_isOk() {
        assertEquals(OutcomeCodeEnum.TIE, EnumUtil.getOutcome(MoveCodeEnum.ROCK, MoveCodeEnum.ROCK));
        assertEquals(OutcomeCodeEnum.TIE, EnumUtil.getOutcome(MoveCodeEnum.PAPER, MoveCodeEnum.PAPER));
        assertEquals(OutcomeCodeEnum.TIE, EnumUtil.getOutcome(MoveCodeEnum.SCISSORS, MoveCodeEnum.SCISSORS));
        assertEquals(OutcomeCodeEnum.TIE, EnumUtil.getOutcome(MoveCodeEnum.LIZARD, MoveCodeEnum.LIZARD));
        assertEquals(OutcomeCodeEnum.TIE, EnumUtil.getOutcome(MoveCodeEnum.SPOCK, MoveCodeEnum.SPOCK));
    }

    @Test
    public void getOutcome_isVictory_isOk() {
        assertEquals(OutcomeCodeEnum.USER_WINS, EnumUtil.getOutcome(MoveCodeEnum.ROCK, MoveCodeEnum.SCISSORS));
        assertEquals(OutcomeCodeEnum.USER_WINS, EnumUtil.getOutcome(MoveCodeEnum.ROCK, MoveCodeEnum.LIZARD));
        assertEquals(OutcomeCodeEnum.USER_WINS, EnumUtil.getOutcome(MoveCodeEnum.PAPER, MoveCodeEnum.ROCK));
        assertEquals(OutcomeCodeEnum.USER_WINS, EnumUtil.getOutcome(MoveCodeEnum.PAPER, MoveCodeEnum.SPOCK));
        assertEquals(OutcomeCodeEnum.USER_WINS, EnumUtil.getOutcome(MoveCodeEnum.SCISSORS, MoveCodeEnum.PAPER));
        assertEquals(OutcomeCodeEnum.USER_WINS, EnumUtil.getOutcome(MoveCodeEnum.SCISSORS, MoveCodeEnum.LIZARD));
        assertEquals(OutcomeCodeEnum.USER_WINS, EnumUtil.getOutcome(MoveCodeEnum.LIZARD, MoveCodeEnum.SPOCK));
        assertEquals(OutcomeCodeEnum.USER_WINS, EnumUtil.getOutcome(MoveCodeEnum.LIZARD, MoveCodeEnum.PAPER));
        assertEquals(OutcomeCodeEnum.USER_WINS, EnumUtil.getOutcome(MoveCodeEnum.SPOCK, MoveCodeEnum.SCISSORS));
        assertEquals(OutcomeCodeEnum.USER_WINS, EnumUtil.getOutcome(MoveCodeEnum.SPOCK, MoveCodeEnum.ROCK));
    }

    @Test
    public void getOutcome_isDefeat_isOk() {
        assertEquals(OutcomeCodeEnum.MACHINE_WINS, EnumUtil.getOutcome(MoveCodeEnum.ROCK, MoveCodeEnum.PAPER));
        assertEquals(OutcomeCodeEnum.MACHINE_WINS, EnumUtil.getOutcome(MoveCodeEnum.ROCK, MoveCodeEnum.SPOCK));
        assertEquals(OutcomeCodeEnum.MACHINE_WINS, EnumUtil.getOutcome(MoveCodeEnum.PAPER, MoveCodeEnum.SCISSORS));
        assertEquals(OutcomeCodeEnum.MACHINE_WINS, EnumUtil.getOutcome(MoveCodeEnum.PAPER, MoveCodeEnum.LIZARD));
        assertEquals(OutcomeCodeEnum.MACHINE_WINS, EnumUtil.getOutcome(MoveCodeEnum.SCISSORS, MoveCodeEnum.ROCK));
        assertEquals(OutcomeCodeEnum.MACHINE_WINS, EnumUtil.getOutcome(MoveCodeEnum.SCISSORS, MoveCodeEnum.SPOCK));
        assertEquals(OutcomeCodeEnum.MACHINE_WINS, EnumUtil.getOutcome(MoveCodeEnum.LIZARD, MoveCodeEnum.SCISSORS));
        assertEquals(OutcomeCodeEnum.MACHINE_WINS, EnumUtil.getOutcome(MoveCodeEnum.LIZARD, MoveCodeEnum.ROCK));
        assertEquals(OutcomeCodeEnum.MACHINE_WINS, EnumUtil.getOutcome(MoveCodeEnum.SPOCK, MoveCodeEnum.LIZARD));
        assertEquals(OutcomeCodeEnum.MACHINE_WINS, EnumUtil.getOutcome(MoveCodeEnum.SPOCK, MoveCodeEnum.PAPER));
    }
}
