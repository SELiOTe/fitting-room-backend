package com.seliote.fr.service.impl;

import com.seliote.fr.mapper.SmsMapper;
import com.seliote.fr.model.mi.sms.InsertMi;
import com.seliote.fr.model.si.captcha.CheckSmsSi;
import com.seliote.fr.model.si.sms.CheckLoginSi;
import com.seliote.fr.model.si.sms.LoginSi;
import com.seliote.fr.service.CaptchaService;
import com.seliote.fr.service.RedisService;
import com.seliote.fr.service.SmsService;
import com.seliote.fr.util.CommonUtils;
import com.seliote.fr.util.ReflectUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.seliote.fr.util.ReflectUtils.getClassName;

/**
 * 短信 Service 实现
 *
 * @author seliote
 */
@Log4j2
@Service
public class SmsServiceImpl implements SmsService {

    private static final String redisNamespace = "sms";
    private static final int smsExpiredSeconds = 300;

    private final CaptchaService captchaService;
    private final RedisService redisService;
    private final SmsMapper smsMapper;

    @Autowired
    public SmsServiceImpl(CaptchaService captchaService,
                          RedisService redisService,
                          SmsMapper smsMapper) {
        this.captchaService = captchaService;
        this.redisService = redisService;
        this.smsMapper = smsMapper;
        log.debug("Construct {}", getClassName(this));
    }

    @Override
    public int login(LoginSi si) {
        // 校验图形验证码并发送短信
        if (!captchaService.checkSms(ReflectUtils.copy(si, CheckSmsSi.class))) {
            log.warn("Check captcha failed for: {}", si);
            return 1;
        }
        log.info("Check captcha success for: {}", si);
        var random = CommonUtils.getSecureRandom();
        var sb = new StringBuilder();
        for (var i = 0; i < 6; ++i) {
            sb.append(random.nextInt(9) + 1);
        }
        var verifyCode = sb.toString();
        // TODO 模拟真实发送短信
        log.debug("Send login sms for: {}, code: {}", si, verifyCode);
        redisService.setex(
                redisService.formatKey(redisNamespace, "login",
                        si.getCountryCode(), si.getTelNo()),
                smsExpiredSeconds,
                verifyCode);
        var sendResult = 0;
        var mi = new InsertMi(si.getCountryCode(), si.getTelNo(), (short) 1, (short) sendResult);
        smsMapper.insert(mi);
        log.info("Send login sms for: {}, result: {}", si, sendResult);
        return 0;
    }

    @Override
    public boolean checkLogin(CheckLoginSi si) {
        var redisKey =
                redisService.formatKey(redisNamespace, "login", si.getCountryCode(), si.getTelNo());
        //var redisCache = redisService.get(redisKey);
        //var result = redisCache.isPresent() && si.getVerifyCode().equalsIgnoreCase(redisCache.get());
        // TODO 模拟真实发送短信结果对比
        final var result = true;
        //noinspection ConstantConditions
        if (result) {
            redisService.del(redisKey);
        }
        return result;
    }
}
