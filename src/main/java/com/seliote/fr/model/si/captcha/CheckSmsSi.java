package com.seliote.fr.model.si.captcha;

import com.seliote.fr.annotation.validation.Captcha;
import com.seliote.fr.annotation.validation.CountryCode;
import com.seliote.fr.annotation.validation.TelNo;
import lombok.Data;

/**
 * 校验短信的图形验证码 SI
 *
 * @author seliote
 */
@Data
public class CheckSmsSi {

    // 国际电话呼叫码
    @CountryCode
    private String countryCode;

    // 手机号码
    @TelNo
    private String telNo;

    // 图形验证码
    @Captcha
    private String captcha;
}
