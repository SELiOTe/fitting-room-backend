package com.seliote.fr.model.sn.captcha;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 获取短信的图形验证码 SN
 *
 * @author seliote
 */
@Data
@AllArgsConstructor
public class GetCaptchaSn {

    // 图形验证码文本
    private String code;

    // 图形验证码图片
    private byte[] img;
}
