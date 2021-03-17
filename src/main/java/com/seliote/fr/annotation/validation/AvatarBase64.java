package com.seliote.fr.annotation.validation;

import com.seliote.fr.annotation.validation.validator.AvatarBase64Validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotBlank;
import java.lang.annotation.*;

/**
 * 头像 Base 64 JSR 380，包含文件大小，图片尺寸校验
 *
 * @author seliote
 */
@SuppressWarnings("unused")
@Documented
@Target({ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.FIELD,
        ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {AvatarBase64Validator.class})
@NotBlank
@ReportAsSingleViolation
public @interface AvatarBase64 {
    String message() default "Avatar illegal";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.FIELD,
            ElementType.METHOD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        AvatarBase64[] value();
    }
}
