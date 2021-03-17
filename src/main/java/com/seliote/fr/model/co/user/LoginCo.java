package com.seliote.fr.model.co.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 登录用户帐户 CO
 *
 * @author seliote
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginCo {

    // 登录结果，0 为登录成功且 Token 域必存在，其他为登录失败且 Token 域未知，1 为短信验证码错误
    @JsonProperty("login_result")
    @NotNull
    private Integer loginResult;

    // 登录 Token
    @JsonProperty("token")
    private String token;
}
