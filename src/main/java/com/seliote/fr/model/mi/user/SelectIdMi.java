package com.seliote.fr.model.mi.user;

import com.seliote.fr.annotation.validation.CountryCode;
import com.seliote.fr.annotation.validation.TelNo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 获取用户 ID MI
 *
 * @author seliote
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectIdMi {

    // 国际电话呼叫码
    @CountryCode
    private String countryCode;

    // 手机号码
    @TelNo
    private String telNo;
}
