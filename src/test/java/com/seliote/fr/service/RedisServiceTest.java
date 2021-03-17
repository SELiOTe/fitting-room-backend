package com.seliote.fr.service;

import com.seliote.fr.config.RootContext;
import com.seliote.fr.config.api.ApiContext;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Redis Service 测试类
 *
 * @author seliote
 */
@Log4j2
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {RootContext.class, ApiContext.class})
class RedisServiceTest {

    @Autowired
    RedisService redisService;

    @Test
    @SneakyThrows
    void test() {
        var k1 = redisService.formatKey("k1");
        var v1 = "v1";
        var k2 = redisService.formatKey("k2");
        var v2 = "6";

        redisService.setex(k1, 3, v1);
        var result = redisService.get(k1);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(result.get(), v1);
        Thread.sleep(3500);
        result = redisService.get(k1);
        Assertions.assertTrue(result.isEmpty());

        redisService.set(k1, v1);
        result = redisService.get(k1);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(result.get(), v1);

        redisService.set(k2, v2);
        var incrResult = redisService.incr(k2);
        Assertions.assertEquals(incrResult, Long.parseLong(v2) + 1);
        Assertions.assertEquals(redisService.del(k2), 1);

        redisService.expire(k1, 3);
        Thread.sleep(3500);
        result = redisService.get(k1);
        Assertions.assertTrue(result.isEmpty());
    }
}