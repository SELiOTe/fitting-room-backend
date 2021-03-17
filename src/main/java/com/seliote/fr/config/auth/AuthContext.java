package com.seliote.fr.config.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seliote.fr.model.co.Co;
import com.seliote.fr.util.CommonUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.seliote.fr.model.co.Co.*;
import static com.seliote.fr.util.ReflectUtils.getClassName;

/**
 * 认证与授权上下文
 *
 * @author seliote
 */
@Log4j2
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthContext extends WebSecurityConfigurerAdapter {

    private final ObjectMapper objectMapper;
    private final TokenFilter tokenFilter;

    @Autowired
    public AuthContext(ObjectMapper objectMapper, TokenFilter tokenFilter) {
        this.objectMapper = objectMapper;
        this.tokenFilter = tokenFilter;
        log.debug("Construct {}", getClassName(this));
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        log.debug("Override authenticationManager()");
        return super.authenticationManager();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/static/**");
        log.debug("Override configure(WebSecurity)");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 无需携带 Token 即可访问的 URL
        final String[] withoutTokenUrls = {"/captcha/sms", "/sms/login", "/user/login"};
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().antMatchers(withoutTokenUrls).permitAll()
                .and().authorizeRequests().anyRequest().authenticated()
                .and().exceptionHandling()
                .authenticationEntryPoint((req, resp, e) -> {
                    resp.getWriter()
                            .write(objectMapper.writeValueAsString(Co.cco(UNAUTHORIZED_CODE, UNAUTHORIZED_MSG)));
                    log.warn("401 for: {}, {}",
                            CommonUtils.getIp(req).orElse("null"), CommonUtils.getUri().orElse("null"));
                })
                .accessDeniedHandler((req, resp, e) -> {
                    resp.getWriter()
                            .write(objectMapper.writeValueAsString(Co.cco(FORBIDDEN_CODE, FORBIDDEN_MSG)));
                    log.warn("403 for: {}, {}",
                            CommonUtils.getIp(req).orElse("null"), CommonUtils.getUri().orElse("null"));
                });
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
        log.debug("Override configure(HttpSecurity)");
    }
}
