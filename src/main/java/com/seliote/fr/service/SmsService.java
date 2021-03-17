package com.seliote.fr.service;

import com.seliote.fr.model.si.sms.CheckLoginSi;
import com.seliote.fr.model.si.sms.LoginSi;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 短信 Service
 *
 * @author seliote
 */
@Validated
public interface SmsService {

    /**
     * 获取登录短信验证码
     *
     * @param si SI
     * @return 发送结果，0 为成功，-1 为未知原因失败，1 为图形验证码错误
     */
    int login(@NotNull @Valid LoginSi si);

    /**
     * 校验登录短信验证码
     *
     * @param si SI
     * @return 成功返回 true，否则返回 false
     */
    boolean checkLogin(@NotNull @Valid CheckLoginSi si);
}
