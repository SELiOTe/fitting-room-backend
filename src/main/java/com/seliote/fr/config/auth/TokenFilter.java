package com.seliote.fr.config.auth;

import com.seliote.fr.service.RedisService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.seliote.fr.util.ReflectUtils.getClassName;

/**
 * Token 过滤器
 *
 * @author seliote
 */
// Filter 会在 AOP, Advice 之前执行，记得不要抛异常
@Log4j2
@Component
public class TokenFilter extends OncePerRequestFilter {

    public static final String REDIS_NAMESPACE = "token";

    // 请求头中携带 Token 的键
    public static final String TOKEN_HEADER = "Authorization";

    private final RedisService redisService;
    private final TokenHandler tokenHandler;

    @Autowired
    public TokenFilter(RedisService redisService,
                       TokenHandler tokenHandler) {
        this.redisService = redisService;
        this.tokenHandler = tokenHandler;
        log.debug("Construct {}", getClassName(this));
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest httpServletRequest,
                                    @NonNull HttpServletResponse httpServletResponse,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头中的 Token
        var token = httpServletRequest.getHeader(TokenFilter.TOKEN_HEADER);
        log.debug("Handle token: " + token);
        Optional<TokenModel> optional = Optional.empty();
        if (token != null) {
            optional = tokenHandler.parseToken(token);
        }
        if (optional.isPresent()) {
            var tokenModel = optional.get();
            // 获取服务器 redis 缓存中的 token
            var issuedToken = redisService.get(
                    redisService.formatKey(REDIS_NAMESPACE, tokenModel.getAudience()));
            // 对比提供与缓存 token
            if (issuedToken.isPresent()
                    && token.equals(issuedToken.get())
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                var userDetails = new UserDetails(tokenModel.getAudience());
                var authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("Set token context success");
            } else {
                log.warn("Request token not match cache: {}", token);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
