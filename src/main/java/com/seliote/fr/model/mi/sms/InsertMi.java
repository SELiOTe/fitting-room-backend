package com.seliote.fr.model.mi.sms;

import com.seliote.fr.annotation.validation.CountryCode;
import com.seliote.fr.annotation.validation.TelNo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 全量插入一条短信发送记录 MI
 *
 * @author seliote
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsertMi {

    // 国际电话呼叫码
    @CountryCode
    private String countryCode;

    // 手机号码
    @TelNo
    private String telNo;

    // 短信类型, 1 是登录
    private Short type;

    // 发送结果，0 是成功，1 是 API 提供方发送失败
    private Short result;
}
