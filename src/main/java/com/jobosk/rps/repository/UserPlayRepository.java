package com.jobosk.rps.repository;

import com.jobosk.rps.exception.ControlledException;
import com.jobosk.rps.model.MoveCodeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class UserPlayRepository implements IUserPlayRepository {

    private final RedisTemplate<String, MoveCodeEnum> redisTemplateUserPlays;

    @Override
    public void saveUserPlay(final @NotNull MoveCodeEnum moveCode, final @NotNull UUID userId) {
        final String id = userId.toString();
        final MoveCodeEnum unfinishedPlay = redisTemplateUserPlays.opsForValue().get(id);
        if (unfinishedPlay != null) {
            throw new ControlledException(HttpStatus.METHOD_NOT_ALLOWED, "unfinished_play_found");
        }
        redisTemplateUserPlays.opsForValue().set(id, moveCode);
        redisTemplateUserPlays.expire(id, 30, TimeUnit.SECONDS);
    }

    @Override
    public Optional<MoveCodeEnum> getUserPlay(final UUID userId) {
        return Optional.ofNullable(userId)
                .map(UUID::toString)
                .map(id -> redisTemplateUserPlays.opsForValue().getAndDelete(id));
    }
}
