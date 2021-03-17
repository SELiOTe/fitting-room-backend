package com.seliote.fr.util;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TextUtils 测试
 *
 * @author seliote
 */
@Log4j2
class TextUtilsTest {

    @Test
    void sha1() {
        Assertions.assertEquals("a94a8fe5ccb19ba61c4c0873d391e987982fbbd3", TextUtils.sha1("test"));
    }
}