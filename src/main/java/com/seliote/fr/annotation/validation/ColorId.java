package com.seliote.fr.annotation.validation;

import com.seliote.fr.annotation.validation.validator.ColorIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

/**
 * 商品颜色 ID JSR 380
 * |  1 | white
 * |  2 | black
 * |  3 | red
 * |  4 | orange
 * |  5 | yellow
 * |  6 | green
 * |  7 | blue
 * |  8 | purple
 * |  9 | pink
 *
 * @author seliote
 */
@SuppressWarnings("unused")
@Documented
@Target({ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.FIELD,
        ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ColorIdValidator.class})
@NotNull
@Min(0)
@ReportAsSingleViolation
public @interface ColorId {
    String message() default "Color id not match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.FIELD,
            ElementType.METHOD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        ColorId[] value();
    }
}
