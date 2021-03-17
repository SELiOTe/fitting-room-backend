package com.seliote.fr.model.co.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 获取登录短信验证码 CO
 *
 * @author seliote
 */
@Data
@AllArgsConstructor
public class LoginCo {

    // 发送结果，0 为成功，-1 为未知原因失败，1 为图形验证码错误
    @JsonProperty("send_result")
    @NotNull
    private Integer sendResult;
}
