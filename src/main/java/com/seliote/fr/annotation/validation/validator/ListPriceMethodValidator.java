package com.seliote.fr.annotation.validation.validator;

import com.seliote.fr.annotation.validation.ListPriceMethod;
import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 商品售价计算方式 JSR 380 校验实现
 *
 * @author seliote
 */
@Log4j2
public class ListPriceMethodValidator implements ConstraintValidator<ListPriceMethod, Integer> {

    private Set<Integer> listPriceMethodRange;

    @Override
    public void initialize(ListPriceMethod constraintAnnotation) {
        listPriceMethodRange = new HashSet<>();
        listPriceMethodRange.add(0);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        log.trace("Check ListPriceMethod {}", value);
        return listPriceMethodRange.contains(value);
    }
}
