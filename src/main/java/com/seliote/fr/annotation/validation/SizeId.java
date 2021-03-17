package com.seliote.fr.annotation.validation;

import com.seliote.fr.annotation.validation.validator.SizeIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

/**
 * 商品尺码 ID JSR 380
 * |  1 | 150
 * |  2 | 155
 * |  3 | 160
 * |  4 | 165
 * |  5 | 170
 * |  6 | 175
 * |  7 | 180
 * |  8 | 185
 * |  9 | 190
 * | 10 | 195
 * | 11 | 200
 *
 * @author seliote
 */
@SuppressWarnings("unused")
@Documented
@Target({ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.FIELD,
        ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {SizeIdValidator.class})
@NotNull
@Min(0)
@ReportAsSingleViolation
public @interface SizeId {
    String message() default "Size id not match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.FIELD,
            ElementType.METHOD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        SizeId[] value();
    }
}
