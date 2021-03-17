package com.seliote.fr.controller;

import com.seliote.fr.annotation.ApiFrequency;
import com.seliote.fr.annotation.stereotype.ApiController;
import com.seliote.fr.config.api.ApiFrequencyType;
import com.seliote.fr.exception.ApiException;
import com.seliote.fr.model.ci.sms.LoginCi;
import com.seliote.fr.model.co.Co;
import com.seliote.fr.model.co.sms.LoginCo;
import com.seliote.fr.model.si.sms.LoginSi;
import com.seliote.fr.service.SmsService;
import com.seliote.fr.util.ReflectUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.temporal.ChronoUnit;

import static com.seliote.fr.util.ReflectUtils.getClassName;

/**
 * 短信 Controller
 *
 * @author seliote
 */
@Log4j2
@Validated
@ApiController
@RequestMapping(value = "sms", method = {RequestMethod.POST})
public class SmsController {

    private final SmsService smsService;

    @Autowired
    public SmsController(SmsService smsService) {
        this.smsService = smsService;
        log.debug("Construct {}", getClassName(this));
    }

    /**
     * 获取登录短信验证码
     *
     * @param ci CI
     * @return CO
     */
    @ApiFrequency(type = ApiFrequencyType.ARG, key = "countryCode&&telNo", unit = ChronoUnit.HOURS)
    @RequestMapping("login")
    @ResponseBody
    @NotNull
    @Valid
    public Co<LoginCo> login(@Valid @RequestBody LoginCi ci) {
        var result = smsService.login(ReflectUtils.copy(ci, LoginSi.class));
        if (result == 0 || result == 1) {
            return Co.cco(new LoginCo(result));
        } else {
            log.error("Login for: {}, service return: {}", ci, result);
            throw new ApiException("service return value error");
        }
    }
}
