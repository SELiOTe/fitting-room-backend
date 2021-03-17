package com.seliote.fr.service.impl;

import com.seliote.fr.exception.ServiceException;
import com.seliote.fr.model.si.captcha.CheckSmsSi;
import com.seliote.fr.model.si.captcha.SmsSi;
import com.seliote.fr.model.sn.captcha.GetCaptchaSn;
import com.seliote.fr.service.CaptchaService;
import com.seliote.fr.service.RedisService;
import com.seliote.fr.util.CommonUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Optional;

import static com.seliote.fr.util.ReflectUtils.getClassName;

/**
 * 图形验证码 Service 实现
 *
 * @author seliote
 */
@Log4j2
@Service
public class CaptchaServiceImpl implements CaptchaService {

    private static final String redisNamespace = "captcha";
    private static final int smsExpiredSeconds = 600;

    private final RedisService redisService;

    @Autowired
    public CaptchaServiceImpl(RedisService redisService) {
        this.redisService = redisService;
        log.debug("Construct {}", getClassName(this));
    }

    @Override
    public Optional<byte[]> sms(SmsSi si) {
        var captcha = getCaptcha();
        log.info("Get captcha for sms {}, result: {}", si, captcha);
        redisService.setex(
                redisService.formatKey(redisNamespace, "sms", si.getCountryCode(), si.getTelNo()),
                smsExpiredSeconds,
                captcha.getCode());
        return Optional.of(captcha.getImg());
    }

    @Override
    public boolean checkSms(CheckSmsSi si) {
        var redisKey = redisService.formatKey(redisNamespace, "sms", si.getCountryCode(), si.getTelNo());
        var redisCache = redisService.get(redisKey);
        var result = redisCache.isPresent() && si.getCaptcha().equalsIgnoreCase(redisCache.get());
        if (result) {
            redisService.del(redisKey);
        }
        return result;
    }

    /**
     * 获取图形验证码
     *
     * @return SN
     */
    private GetCaptchaSn getCaptcha() {
        final var baseSize = 200;
        final var textLength = 4;
        final var textRange = "23456789qazwsxedcrfvtgbyhnujmLp".toCharArray();
        final var colorRange = new Color[]{Color.GRAY, Color.BLACK, Color.GREEN, Color.YELLOW,
                Color.ORANGE, Color.RED, Color.PINK, Color.BLUE};
        final var backgroundColor = Color.WHITE;
        final var random = CommonUtils.getSecureRandom();
        var text = getCaptchaText(random, textLength, textRange);
        var img = getImage(baseSize, textLength);
        var g2d = img.createGraphics();
        drawBackground(img, g2d, backgroundColor);
        drawText(baseSize, textLength, text, colorRange, img, g2d, random);
        drawInterference(textLength, colorRange, img, g2d, random);
        g2d.dispose();
        var outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(img, "png", outputStream);
        } catch (IOException exception) {
            log.error("Private CaptchaServiceImpl.getCaptcha() occur IOException, message: {}",
                    exception.getMessage());
            throw new ServiceException(exception);
        }
        log.info("Create captcha sn success");
        return new GetCaptchaSn(text, outputStream.toByteArray());
    }

    /**
     * 获取验证码文本
     *
     * @param secureRandom SecureRandom 对象
     * @param textLength   文本长度
     * @param textRange    文本取值范围
     * @return 验证码文本
     */
    @SuppressWarnings("SameParameterValue")
    private String getCaptchaText(SecureRandom secureRandom, int textLength, char[] textRange) {
        var sb = new StringBuilder();
        for (var i = 0; i < textLength; ++i) {
            sb.append(textRange[secureRandom.nextInt(textRange.length)]);
        }
        return sb.toString();
    }

    /**
     * 获取空白图像
     *
     * @param baseSize   基础大小
     * @param textLength 文本长度
     * @return BufferedImage 对象
     */
    @SuppressWarnings("SameParameterValue")
    private BufferedImage getImage(int baseSize, int textLength) {
        // 文本宽度以及左右留白
        final var imgWidth = Math.round(textLength * 1.0F * baseSize);
        final var imgHeight = Math.round(baseSize * 2.0F);
        return new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
    }

    /**
     * 填充图片背景
     *
     * @param bufferedImage   BufferedImage 对象
     * @param graphics2D      Graphics2D 对象
     * @param backgroundColor 背景色
     */
    private void drawBackground(BufferedImage bufferedImage, Graphics2D graphics2D, Color backgroundColor) {
        graphics2D.setColor(backgroundColor);
        graphics2D.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
    }

    /**
     * 绘制文本
     *
     * @param baseSize      基础大小
     * @param textLength    文本长度
     * @param text          文本内容
     * @param colors        文本色彩范围
     * @param bufferedImage BufferedImage 对象
     * @param graphics2D    Graphics2D 对象
     * @param random        SecureRandom 对象
     */
    @SuppressWarnings("SameParameterValue")
    private void drawText(int baseSize, int textLength, String text, Color[] colors, BufferedImage bufferedImage,
                          Graphics2D graphics2D, SecureRandom random) {
        // 左边留白宽度
        final var leftSpace = baseSize / 2;
        // 每个字符宽度
        final var eachCharWidth = (bufferedImage.getWidth() - baseSize) / textLength;
        // 将角度转化为弧度
        final var angle2Radian = Math.PI / 180;
        for (var i = 0; i < textLength; ++i) {
            // 随机字体颜色
            graphics2D.setColor(colors[random.nextInt(colors.length)]);
            // 随机字体样式
            graphics2D.setFont(
                    new Font(null, random.nextInt(2) == 1 ? Font.ITALIC : Font.PLAIN, baseSize));
            // 计算位置与角度，基准量 + 偏移量
            var x = leftSpace + i * eachCharWidth + random.nextInt(baseSize / 3);
            var y = baseSize + random.nextInt((bufferedImage.getHeight() - baseSize) / 2);
            var radian = (random.nextInt(90) - 45) * angle2Radian;
            // 位置
            graphics2D.translate(x, y);
            // 角度
            graphics2D.rotate(radian);
            graphics2D.drawString(text.substring(i, i + 1), 0, 0);
            // 再设置回去
            graphics2D.rotate(-radian);
            graphics2D.translate(-x, -y);
        }
    }

    /**
     * 绘制干扰线
     *
     * @param textLength    文本长度
     * @param colors        干扰线色彩范围
     * @param bufferedImage BufferedImage 对象
     * @param graphics2D    Graphics2D 对象
     * @param random        SecureRandom 对象
     */
    @SuppressWarnings("SameParameterValue")
    private void drawInterference(int textLength, Color[] colors, BufferedImage bufferedImage,
                                  Graphics2D graphics2D, SecureRandom random) {
        // 干扰线条数
        final var count = Math.round(textLength * 7);
        // 干扰线最大宽度
        final var maxWidth = Math.round(bufferedImage.getHeight() * 0.03F);
        // 图片宽高
        final var imgWidth = bufferedImage.getWidth();
        final var imgHeight = bufferedImage.getHeight();
        for (var i = 0; i < count; ++i) {
            // 设置颜色
            graphics2D.setColor(colors[random.nextInt(colors.length)]);
            // 设置线宽
            graphics2D.setStroke(new BasicStroke(random.nextInt(maxWidth)));
            if (random.nextInt(3) > 0) {
                // 1/3 概率画直线
                graphics2D.drawLine(random.nextInt(imgWidth), random.nextInt(imgHeight),
                        random.nextInt(imgWidth), random.nextInt(imgHeight));
            } else {
                // 2/3 概率画曲线
                graphics2D.draw(new QuadCurve2D.Double(random.nextInt(imgWidth), random.nextInt(imgHeight),
                        random.nextInt(imgWidth), random.nextInt(imgHeight),
                        random.nextInt(imgWidth), random.nextInt(imgHeight)));
            }
        }
    }
}
