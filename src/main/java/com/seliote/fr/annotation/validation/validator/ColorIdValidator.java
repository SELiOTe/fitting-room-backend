package com.seliote.fr.annotation.validation.validator;

import com.seliote.fr.annotation.validation.ColorId;
import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * 商品售价计算方式 JSR 380 校验实现
 *
 * @author seliote
 */
@Log4j2
public class ColorIdValidator implements ConstraintValidator<ColorId, Long> {

    private Set<Long> colorSet;

    @Override
    public void initialize(ColorId constraintAnnotation) {
        colorSet = LongStream.rangeClosed(1, 9).boxed().collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext constraintValidatorContext) {
        log.trace("Check ColorId {}", value);
        return colorSet.contains(value);
    }
}
