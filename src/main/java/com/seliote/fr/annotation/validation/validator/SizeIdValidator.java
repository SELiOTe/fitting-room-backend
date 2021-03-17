package com.seliote.fr.annotation.validation.validator;

import com.seliote.fr.annotation.validation.SizeId;
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
public class SizeIdValidator implements ConstraintValidator<SizeId, Long> {

    private Set<Long> sizeRange;

    @Override
    public void initialize(SizeId constraintAnnotation) {
        sizeRange = LongStream.rangeClosed(1, 11).boxed().collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext constraintValidatorContext) {
        log.trace("Check ColorId {}", value);
        return sizeRange.contains(value);
    }
}
