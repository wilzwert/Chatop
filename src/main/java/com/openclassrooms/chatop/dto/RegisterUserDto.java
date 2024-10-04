package com.openclassrooms.chatop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterUserDto {
    @NotBlank(message = "The email is required")
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "The name is required")
    private String name;
    @NotBlank(message = "The password is required")
    private String password;
}
