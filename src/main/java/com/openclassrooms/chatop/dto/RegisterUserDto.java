package com.openclassrooms.chatop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author Wilhelm Zwertvaegher
 * DTO used to handle a user registration
 */
@Data
@Schema(description = "Object expected for user registration request" )
public class RegisterUserDto {
    @NotBlank(message = "The email is required")
    @Email(message = "Email should be valid")
    @Schema(description = "User email")
    private String email;

    @NotBlank(message = "The name is required")
    @Schema(description = "User name")
    private String name;

    @NotBlank(message = "The password is required")
    @Schema(description = "User password")
    private String password;
}
