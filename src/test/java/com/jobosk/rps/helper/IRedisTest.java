package com.jobosk.rps.helper;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

public interface IRedisTest {

  @DynamicPropertySource
  static void setup(final DynamicPropertyRegistry registry) {
    RedisTestHelper.setup(registry);
  }
}
