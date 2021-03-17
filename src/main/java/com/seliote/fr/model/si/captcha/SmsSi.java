package com.seliote.fr.model.si.captcha;

import com.seliote.fr.annotation.validation.CountryCode;
import com.seliote.fr.annotation.validation.TelNo;
import lombok.Data;

/**
 * 获取短信的图形验证码 Si
 *
 * @author seliote
 */
@Data
public class SmsSi {

    // 国际电话呼叫码
    @CountryCode
    private String countryCode;

    // 手机号码
    @TelNo
    private String telNo;
}
