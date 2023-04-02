package com.jobosk.rps.service;

import com.jobosk.rps.exception.ControlledException;
import com.jobosk.rps.model.MoveCodeEnum;
import com.jobosk.rps.model.PlayResult;
import com.jobosk.rps.repository.IUserPlayRepository;
import com.jobosk.rps.util.EnumUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class PlayService {

    private final IUserPlayRepository userPlayRepository;

    public void lockUserMove(final String moveCode, final UUID userId) {
        userPlayRepository.saveUserPlay(
                EnumUtil.getMove(moveCode)
                        .orElseThrow(() -> new ControlledException(HttpStatus.NOT_FOUND, "unknown_move_code"))
                , userId
        );
    }

    public PlayResult getPlayResult(final UUID userId) {
        final MoveCodeEnum moveByUser = userPlayRepository.getUserPlay(userId)
                .orElseThrow(() -> new ControlledException(HttpStatus.NOT_FOUND, "missing_active_game"));
        final MoveCodeEnum moveByMachine = EnumUtil.getRandomMove();
        return new PlayResult()
                .moveByUser(moveByUser)
                .moveByMachine(moveByMachine)
                .outcome(EnumUtil.getOutcome(moveByUser, moveByMachine));
    }
}
