package com.seliote.fr.model.co.captcha;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 获取短信的图形验证码 CO
 *
 * @author seliote
 */
@Data
@AllArgsConstructor
public class SmsCo {

    // 图形验证码 Base 64 编码
    @JsonProperty("captcha")
    @NotBlank
    private String captcha;
}
