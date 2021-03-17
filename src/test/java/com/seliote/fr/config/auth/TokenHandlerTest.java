package com.seliote.fr.config.auth;

import com.seliote.fr.config.RootContext;
import com.seliote.fr.config.api.ApiContext;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.Instant;

/**
 * TokenHandler 测试
 *
 * @author seliote
 */
@Log4j2
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {RootContext.class, ApiContext.class})
class TokenHandlerTest {

    @Autowired
    TokenHandler tokenHandler;

    @Test
    void test() {
        var aud = "seliote";
        var iat = Instant.now();
        var token = tokenHandler.generateToken(new TokenModel(aud, iat));
        log.info("dadada   " + token);
        var tokenModel = tokenHandler.parseToken(token);
        Assertions.assertTrue(tokenModel.isPresent());
        Assertions.assertEquals(aud, tokenModel.get().getAudience());
        Assertions.assertFalse(Instant.now().isBefore(tokenModel.get().getIssueAt()));
    }
}