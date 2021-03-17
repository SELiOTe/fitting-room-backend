package com.seliote.fr.model.si.user;

import com.seliote.fr.annotation.validation.CountryCode;
import com.seliote.fr.annotation.validation.TelNo;
import lombok.Data;

/**
 * 判断用户是否注册 SI
 *
 * @author seliote
 */
@Data
public class IsRegisteredSi {

    // 国际电话呼叫码
    @CountryCode
    private String countryCode;

    // 手机号码
    @TelNo
    private String telNo;
}
