package com.openclassrooms.chatop.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class ImageFileValidator implements ConstraintValidator<ImageFile, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile f, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;

        if(f != null && !f.isEmpty()) {
            if (Objects.requireNonNull(f.getContentType()).isEmpty() || !isSupportedContentType(f.getContentType())) {
                valid = false;
            }
        }

        return valid;
    }

    public boolean isSupportedContentType(String contentType) {
        return contentType.equals("image/png")
                || contentType.equals("image/jpg")
                || contentType.equals("image/jpeg")
                || contentType.equals("image/gif");
    }
}
