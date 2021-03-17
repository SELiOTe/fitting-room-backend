package com.seliote.fr.model.ci.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seliote.fr.annotation.validation.CountryCode;
import com.seliote.fr.annotation.validation.TelNo;
import com.seliote.fr.annotation.validation.VerifyCode;
import lombok.Data;

/**
 * 登录用户帐户 CI
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

    // 短信验证码
    @JsonProperty("verify_code")
    @VerifyCode
    private String verifyCode;
}
