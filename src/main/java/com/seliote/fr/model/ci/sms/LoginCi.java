package com.seliote.fr.model.ci.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seliote.fr.annotation.validation.Captcha;
import com.seliote.fr.annotation.validation.CountryCode;
import com.seliote.fr.annotation.validation.TelNo;
import lombok.Data;

/**
 * 获取登录短信验证码 CI
 *
 * @author seliote
 */
@Data
public class LoginCi {

    // 国际电话呼叫码
    @JsonProperty("country_code")
    @CountryCode
    private String countryCode;

    // 手机号码
    @JsonProperty("tel_no")
    @TelNo
    private String telNo;

    // 图形验证码
    @JsonProperty("captcha")
    @Captcha
    private String captcha;
}
