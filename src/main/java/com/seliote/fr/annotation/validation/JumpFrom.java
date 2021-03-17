package com.seliote.fr.annotation.validation;

import com.seliote.fr.annotation.validation.validator.JumpFromValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

/**
 * 跳转来源 JSR 380
 * 0 为未知
 * 1 商品首页列表
 *
 * @author seliote
 */
@SuppressWarnings("unused")
@Documented
@Target({ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.FIELD,
        ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {JumpFromValidator.class})
@NotNull
@ReportAsSingleViolation
public @interface JumpFrom {
    String message() default "JumpFrom illegal";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.FIELD,
            ElementType.METHOD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        JumpFrom[] value();
    }
}
