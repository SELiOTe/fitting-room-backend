package com.seliote.fr.config.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.jackson.io.JacksonSerializer;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

import static com.seliote.fr.util.ReflectUtils.getClassName;

/**
 * Token 处理工具
 *
 * @author seliote
 */
@Log4j2
@Component
public class TokenHandler {

    private final ObjectMapper objectMapper;

    // 签名 key
    private final Key key;
    // JwtBuilder
    // JwtParser，线程安全
    private final JwtParser jwtParser;

    @Autowired
    public TokenHandler(ObjectMapper objectMapper,
                        @Value("${sys.token-key}") String key) {
        this.objectMapper = objectMapper;
        this.key = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        jwtParser = Jwts.parserBuilder()
                .deserializeJsonWith(new JacksonDeserializer<>(this.objectMapper))
                .setSigningKey(this.key)
                .build();
        log.debug("Construct {}", getClassName(this));
    }

    /**
     * 生成 Token
     *
     * @param tokenModel Token 信息模型
     * @return 生成的 Token
     */
    @NotBlank
    public String generateToken(@Valid TokenModel tokenModel) {
        var token = Jwts.builder()
                .serializeToJsonWith(new JacksonSerializer<>(this.objectMapper))
                .setAudience(tokenModel.getAudience())
                .setIssuedAt(Date.from(tokenModel.getIssueAt()))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        log.trace("TokenHandler.generateToken(TokenModel) for: {}, result: {}", tokenModel, token);
        return token;
    }

    /**
     * 解析 Token
     *
     * @param token 需要解析的 Token
     * @return Token 信息模型
     */
    @NotNull
    public Optional<TokenModel> parseToken(@NotBlank String token) {
        Claims claims;
        try {
            claims = jwtParser.parseClaimsJws(token).getBody();
        } catch (JwtException exception) {
            log.warn("Error when parse token: {}", token);
            return Optional.empty();
        }
        var tokenModel = new TokenModel(claims.getAudience(), claims.getIssuedAt().toInstant());
        log.trace("TokenHandler.parseToken(String) for: {}, result: {}", token, tokenModel);
        return Optional.of(tokenModel);
    }
}
