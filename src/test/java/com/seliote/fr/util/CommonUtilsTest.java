package com.seliote.fr.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * CommonUtils 测试
 *
 * @author seliote
 */
class CommonUtilsTest {

    @Test
    void getSecureRandom() {
        Assertions.assertEquals(1, CommonUtils.time2Seconds(1, ChronoUnit.SECONDS));
        Assertions.assertEquals(120, CommonUtils.time2Seconds(2, ChronoUnit.MINUTES));
        Assertions.assertEquals(86400, CommonUtils.time2Seconds(1, ChronoUnit.DAYS));
    }
}