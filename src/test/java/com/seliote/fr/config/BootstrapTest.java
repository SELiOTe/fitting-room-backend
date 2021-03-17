package com.seliote.fr.config;

import com.seliote.fr.config.api.ApiContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Spring 框架启动类测试
 *
 * @author seliote
 */
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {RootContext.class, ApiContext.class})
class BootstrapTest {

    @Test
    void onStartup() {
    }
}