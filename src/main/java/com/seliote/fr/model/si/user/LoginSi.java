package com.seliote.fr.model.si.user;

import com.seliote.fr.annotation.validation.Captcha;
import com.seliote.fr.annotation.validation.CountryCode;
import com.seliote.fr.annotation.validation.TelNo;
import com.seliote.fr.annotation.validation.VerifyCode;
import lombok.Data;

/**
 * 登录用户帐户 SI
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

    // 短信验证码
    @VerifyCode
    private String verifyCode;
}
