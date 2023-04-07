package com.jobosk.rps;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junitsupport.Consumer;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import com.jobosk.rps.helper.IRedisTest;
import com.jobosk.rps.model.MoveCodeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.redis.core.RedisTemplate;

@Provider("rps-back")
@Consumer("rps-front")
@PactBroker
//@PactFolder("./src/pacts")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ContractTest implements IRedisTest {

    @LocalServerPort
    int port;

    @Autowired
    RedisTemplate<String, MoveCodeEnum> redisTemplateUserPlays;

    @BeforeEach
    void setUp(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port));
    }

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider.class)
    void verifyPact(PactVerificationContext context) {
        System.setProperty("pact.verifier.publishResults", "true");
        context.verifyInteraction();
    }

    @State("user 00000000-0000-0000-0000-000000000000 doesn't have active plays")
    public void student1Exists() {
        redisTemplateUserPlays.delete("00000000-0000-0000-0000-000000000000");
    }

    @State("user 00000000-0000-0000-0000-000000000000 previously selected rock")
    public void studentsExist() {
        redisTemplateUserPlays.opsForValue().set("00000000-0000-0000-0000-000000000000", MoveCodeEnum.ROCK);
    }
}
