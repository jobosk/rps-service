package com.jobosk.rps.repository;

import com.jobosk.rps.model.MoveCodeEnum;

import java.util.Optional;
import java.util.UUID;

public interface IUserPlayRepository {

    void saveUserPlay(MoveCodeEnum moveCode, UUID userId);

    Optional<MoveCodeEnum> getUserPlay(UUID userId);
}
