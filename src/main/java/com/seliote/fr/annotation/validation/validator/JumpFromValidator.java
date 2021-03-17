package com.seliote.fr.annotation.validation.validator;

import com.seliote.fr.annotation.validation.JumpFrom;
import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 跳转来源 JSR 380 校验实现
 *
 * @author seliote
 */
@Log4j2
public class JumpFromValidator implements ConstraintValidator<JumpFrom, Integer> {

    private Set<Integer> jumpFromRange;

    @Override
    public void initialize(JumpFrom constraintAnnotation) {
        jumpFromRange =  new HashSet<>();
        jumpFromRange.add(0);
        jumpFromRange.add(1);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        log.trace("Check JumpFrom {}", value);
        return jumpFromRange.contains(value);
    }
}
