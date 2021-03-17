package com.seliote.fr.service;

import com.seliote.fr.model.si.captcha.CheckSmsSi;
import com.seliote.fr.model.si.captcha.SmsSi;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * 图形验证码 Service
 *
 * @author seliote
 */
@Validated
public interface CaptchaService {

    /**
     * 获取短信的图形验证码
     *
     * @param si SI
     * @return 图形验证码字节数组
     */
    @NotNull
    Optional<byte[]> sms(@NotNull @Valid SmsSi si);

    /**
     * 校验短信的图形验证码（大小写不敏感）
     *
     * @return 成功返回 true，否则返回 false
     */
    boolean checkSms(@NotNull @Valid CheckSmsSi si);
}
