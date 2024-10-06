package com.openclassrooms.chatop.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Schema(description = "Object expected for login request" )
public class LoginRequestDto {
    @NotBlank
    @JsonAlias({"login", "email"})
    @Schema(description = "User email (aliased by 'login')")
    private String login;
    @NotBlank
    @Schema(description = "User password")
    private String password;
}
