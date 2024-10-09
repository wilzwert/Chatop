package com.openclassrooms.chatop.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @author Wilhelm Zwertvaegher
 * Custom annotation to validate image file
 * Used for rental requests (create or update)
 */
@Documented
@Constraint(validatedBy = {ImageFileValidator.class})
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ImageFile {
    String message() default "Uploaded file is not a valid image";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
