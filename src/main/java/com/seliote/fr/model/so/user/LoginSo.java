package com.seliote.fr.model.so.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 登录用户帐户 SO
 *
 * @author seliote
 */
@Data
@AllArgsConstructor
public class LoginSo {

    // 登录结果，0 为登录成功且 Token 域必存在，其他 Token 域均不存在，1 为短信验证码错误
    @NotNull
    private Integer loginResult;

    // 登录 Token
    private String token;
}
