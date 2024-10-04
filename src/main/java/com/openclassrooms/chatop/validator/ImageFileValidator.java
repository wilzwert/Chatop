package com.openclassrooms.chatop.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.web.multipart.MultipartFile;

public class ImageFileValidator implements ConstraintValidator<ImageFile, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile f, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;

        if(f != null && !f.isEmpty()) {
            if (!isSupportedContentType(f.getContentType())) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate("Image must be png, gif or jpg")
                        .addConstraintViolation();
                valid = false;
            }
        }

        return valid;
    }

    private boolean isSupportedContentType(String contentType) {
        return contentType.equals("image/png")
                || contentType.equals("image/jpg")
                || contentType.equals("image/jpeg")
                || contentType.equals("image/gif");
    }
}
