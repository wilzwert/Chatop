package com.openclassrooms.chatop.model;

import io.jsonwebtoken.Claims;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtToken {
    @NotNull
    private String token;
    private Claims claims;
}
