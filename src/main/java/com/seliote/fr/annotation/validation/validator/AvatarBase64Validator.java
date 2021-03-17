package com.seliote.fr.annotation.validation.validator;

import com.seliote.fr.annotation.validation.AvatarBase64;
import com.seliote.fr.util.TextUtils;
import lombok.extern.log4j.Log4j2;

import javax.imageio.ImageIO;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * 头像 Base 64 JSR 380 校验实现
 *
 * @author seliote
 */
@Log4j2
public class AvatarBase64Validator implements ConstraintValidator<AvatarBase64, String> {

    @Override
    public void initialize(AvatarBase64 constraintAnnotation) {
    }

    @Override
    public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
        log.trace("Check avatar base 64: {}", str);
        var bytes = TextUtils.base64Decode(str);
        log.trace("Avatar size: {}", bytes.length);
        if (bytes.length < 1024 || bytes.length > 10 * 1024 * 1024) {
            log.error("Avatar size {} not match", bytes.length);
            return false;
        }
        try {
            var image = ImageIO.read(new ByteArrayInputStream(bytes));
            // 计算是否大致是正方形
            var width = image.getWidth();
            var height = image.getHeight();
            log.trace("Avatar width: {}, height: {}", width, height);
            if (width == 0 || height == 0 || (Math.abs(width - height) > width / 10)) {
                log.error("Avatar is not a square");
                return false;
            }
            return true;
        } catch (IOException exception) {
            log.error("Could not read avatar as image");
            return false;
        }
    }
}
