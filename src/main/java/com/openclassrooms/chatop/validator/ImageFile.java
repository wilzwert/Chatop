package com.openclassrooms.chatop.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {ImageFileValidator.class})
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ImageFile {
    String message() default "Uploaded file is not a valid image";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
