package com.jobosk.rps.controller;

import com.jobosk.rps.api.PlayApi;
import com.jobosk.rps.model.PlayResult;
import com.jobosk.rps.service.PlayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PlayController implements PlayApi {

    private final PlayService playService;

    @Override
    public ResponseEntity<Void> playMoveCodePost(final String moveCode, final UUID xUserId) {
        playService.lockUserMove(moveCode, xUserId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<PlayResult> playRevealGet(final UUID xUserId) {
        return new ResponseEntity<>(playService.getPlayResult(xUserId), HttpStatus.OK);
    }
}
