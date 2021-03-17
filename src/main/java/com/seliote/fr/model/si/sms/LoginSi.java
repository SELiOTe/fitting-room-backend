package com.seliote.fr.model.si.sms;

import com.seliote.fr.annotation.validation.Captcha;
import com.seliote.fr.annotation.validation.CountryCode;
import com.seliote.fr.annotation.validation.TelNo;
import lombok.Data;

/**
 * 获取登录短信验证码 SI
 *
 * @author seliote
 */
@Data
public class LoginSi {

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
