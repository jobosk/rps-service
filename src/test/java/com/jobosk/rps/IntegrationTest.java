package com.jobosk.rps;

import com.jobosk.rps.config.RedisConfig;
import com.jobosk.rps.controller.PlayController;
import com.jobosk.rps.helper.IRedisTest;
import com.jobosk.rps.model.MoveCodeEnum;
import com.jobosk.rps.repository.UserPlayRepository;
import com.jobosk.rps.service.PlayService;
import com.jobosk.rps.util.EnumUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({
        RedisAutoConfiguration.class
        , RedisConfig.class
        , UserPlayRepository.class
        , PlayService.class
})
@WebMvcTest(PlayController.class)
@AutoConfigureMockMvc(addFilters = false)
@Testcontainers
public class IntegrationTest implements IRedisTest {

    @Autowired
    UserPlayRepository userPlayRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void playMove_whenRock_isOk() throws Exception {
        final UUID userId = UUID.randomUUID();
        this.mockMvc.perform(
                post("/play/{moveCode}", MoveCodeEnum.ROCK.name())
                        .header("x-user-id", userId)
        ).andExpect(status().isOk());
    }

    @Test
    public void playMove_whenPaper_isOk() throws Exception {
        final UUID userId = UUID.randomUUID();
        this.mockMvc.perform(
                post("/play/{moveCode}", MoveCodeEnum.PAPER.name())
                        .header("x-user-id", userId)
        ).andExpect(status().isOk());
    }

    @Test
    public void playMove_whenScissors_isOk() throws Exception {
        final UUID userId = UUID.randomUUID();
        this.mockMvc.perform(
                post("/play/{moveCode}", MoveCodeEnum.SCISSORS.name())
                        .header("x-user-id", userId)
        ).andExpect(status().isOk());
    }

    @Test
    public void playMove_whenLizard_isOk() throws Exception {
        final UUID userId = UUID.randomUUID();
        this.mockMvc.perform(
                post("/play/{moveCode}", MoveCodeEnum.LIZARD.name())
                        .header("x-user-id", userId)
        ).andExpect(status().isOk());
    }

    @Test
    public void playMove_whenSpock_isOk() throws Exception {
        final UUID userId = UUID.randomUUID();
        this.mockMvc.perform(
                post("/play/{moveCode}", MoveCodeEnum.SPOCK.name())
                        .header("x-user-id", userId)
        ).andExpect(status().isOk());
    }

    @Test
    public void playMove_whenUnknownMove_isNok() throws Exception {
        this.mockMvc.perform(
                post("/play/{moveCode}", "SOME_OTHER_MOVE")
                        .header("x-user-id", UUID.randomUUID())
        ).andExpect(status().isNotFound());
    }

    @Test
    public void playMove_unfinishedPlay_isNok() throws Exception {
        final UUID userId = UUID.randomUUID();
        userPlayRepository.saveUserPlay(EnumUtil.getRandomMove(), userId);
        this.mockMvc.perform(
                post("/play/{moveCode}", EnumUtil.getRandomMove().name())
                        .header("x-user-id", userId)
        ).andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void revealPlay_isOk() throws Exception {
        final UUID userId = UUID.randomUUID();
        final MoveCodeEnum move = EnumUtil.getRandomMove();
        userPlayRepository.saveUserPlay(move, userId);
        this.mockMvc.perform(
                        get("/play/reveal")
                                .header("x-user-id", userId)
                )
                .andExpect(status().isOk())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.moveByUser")
                                .value(move.name())
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.moveByMachine").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.outcome").exists())
        ;
        assertEquals(Optional.empty(), userPlayRepository.getUserPlay(userId));
    }

    @Test
    public void revealPlay_missingActivePlay_isNok() throws Exception {
        this.mockMvc.perform(
                        get("/play/reveal")
                                .header("x-user-id", UUID.randomUUID())
                )
                .andExpect(status().isNotFound())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$")
                                .value("missing_active_game")
                )
        ;
    }
}
