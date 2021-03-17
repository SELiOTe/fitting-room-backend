package com.seliote.fr.config.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * Token 模型，即 JWT 中实际包含的数据
 *
 * @author seliote
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenModel {

    // 用户标志 String
    @JsonProperty("aud")
    @NotBlank
    private String audience;

    // 签发日期
    @JsonProperty("iat")
    @NotNull
    private Instant issueAt;
}
