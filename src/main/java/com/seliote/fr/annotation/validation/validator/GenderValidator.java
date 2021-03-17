package com.seliote.fr.annotation.validation.validator;

import com.seliote.fr.annotation.validation.Gender;
import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 性别 JSR 380 校验实现
 *
 * @author seliote
 */
@Log4j2
public class GenderValidator implements ConstraintValidator<Gender, Short> {

    private Set<Short> genderRange;

    @Override
    public void initialize(Gender constraintAnnotation) {
        genderRange = new HashSet<>();
        genderRange.add((short) 0);
        genderRange.add((short) 1);
        genderRange.add((short) 2);
    }

    @Override
    public boolean isValid(Short value, ConstraintValidatorContext constraintValidatorContext) {
        log.trace("Check gender {}", value);
        return genderRange.contains(value);
    }
}
