package com.openclassrooms.chatop.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class LoginRequestDto {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
}
