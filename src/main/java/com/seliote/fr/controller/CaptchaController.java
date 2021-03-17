package com.seliote.fr.controller;

import com.seliote.fr.annotation.ApiFrequency;
import com.seliote.fr.annotation.stereotype.ApiController;
import com.seliote.fr.config.api.ApiFrequencyType;
import com.seliote.fr.exception.ServiceException;
import com.seliote.fr.model.ci.captcha.SmsCi;
import com.seliote.fr.model.co.Co;
import com.seliote.fr.model.co.captcha.SmsCo;
import com.seliote.fr.model.si.captcha.SmsSi;
import com.seliote.fr.service.CaptchaService;
import com.seliote.fr.util.ReflectUtils;
import com.seliote.fr.util.TextUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.seliote.fr.util.ReflectUtils.getClassName;

/**
 * 图形验证码 Controller
 *
 * @author seliote
 */
@Log4j2
@Validated
@ApiController
@RequestMapping(value = "captcha", method = RequestMethod.POST)
public class CaptchaController {

    private final CaptchaService captchaService;

    @Autowired
    public CaptchaController(CaptchaService captchaService) {
        this.captchaService = captchaService;
        log.debug("Construct {}", getClassName(this));
    }

    /**
     * 获取短信的图形验证码
     *
     * @param ci CI
     * @return CO
     */
    @ApiFrequency(type = ApiFrequencyType.ARG, key = "countryCode&&telNo")
    @RequestMapping("sms")
    @ResponseBody
    @NotNull
    @Valid
    public Co<SmsCo> sms(@Valid @RequestBody SmsCi ci) {
        var captchaBytes = captchaService.sms(ReflectUtils.copy(ci, SmsSi.class));
        if (captchaBytes.isEmpty()) {
            log.error("Get captcha for: {}, service return empty", ci);
            throw new ServiceException("service return empty");
        }
        var smsCo = new SmsCo(TextUtils.base64Encode(captchaBytes.get()));
        return Co.cco(smsCo);
    }
}
