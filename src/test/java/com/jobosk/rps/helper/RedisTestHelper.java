package com.jobosk.rps.helper;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

public class RedisTestHelper {

  private static final GenericContainer<?> container;

  private static final String REDIS_VERSION = "redis:6";
  private static final Integer REDIS_PORT = 6379;

  static {
    container = new GenericContainer<>(DockerImageName.parse(REDIS_VERSION))
        .withExposedPorts(REDIS_PORT);
  }

  public static void setup(final DynamicPropertyRegistry registry) {
    registry.add("spring.redis.host", container::getContainerIpAddress);
    registry.add("spring.redis.port", () -> container.getMappedPort(REDIS_PORT));
    container.start();
  }
}
