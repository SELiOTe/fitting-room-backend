package com.seliote.fr.model.ci.captcha;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seliote.fr.annotation.validation.CountryCode;
import com.seliote.fr.annotation.validation.TelNo;
import lombok.Data;

/**
 * 获取短信的图形验证码 CI
 *
 * @author seliote
 */
@Data
public class SmsCi {

    // 国际电话呼叫码
    @JsonProperty("country_code")
    @CountryCode
    private String countryCode;

    // 手机号码
    @JsonProperty("tel_no")
    @TelNo
    private String telNo;
}
