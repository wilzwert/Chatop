package com.openclassrooms.chatop.validator;


import com.openclassrooms.chatop.dto.RentalRequestDto;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class ImageFileValidatorTests {
    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @DisplayName("Given an supported content type the result should be true")
    @Test
    public void testSupportedImageType() {
        ImageFileValidator imageFileValidator = new ImageFileValidator();

        boolean supported = imageFileValidator.isSupportedContentType("image/jpeg");

        assertTrue(supported, "Image type should be supported");
    }

    @DisplayName("Given an unsupported content type the result should be false")
    @Test
    public void testUnsupportedImageType() {
        ImageFileValidator imageFileValidator = new ImageFileValidator();

        boolean supported = imageFileValidator.isSupportedContentType("application/octet-stream");

        assertFalse(supported, "Image type should NOT be supported");
    }

    @DisplayName("Given a image/jpeg multipart file the validation should pass")
    @Test
    public void testShouldBeValidWithImage() {
        ImageFileValidator imageFileValidator = new ImageFileValidator();
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test".getBytes());

        boolean isValid = imageFileValidator.isValid(multipartFile, constraintValidatorContext);

        assertTrue(isValid, "File should be valid");
    }

    @DisplayName("Given a text/plain multipart file the validation should not pass")
    @Test
    public void testShouldNotBeValidWithText() {
        ImageFileValidator imageFileValidator = new ImageFileValidator();
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.jpg", "text/plain", "test".getBytes());

        boolean isValid = imageFileValidator.isValid(multipartFile, constraintValidatorContext);

        assertFalse(isValid, "File should not be valid");
    }

}
