package com.jobosk.rps;

import com.jobosk.rps.helper.IRedisTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class SmokeTest implements IRedisTest {

    @Test
    void loadContext_isOk() {
    }
}
