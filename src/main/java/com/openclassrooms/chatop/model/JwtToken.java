package com.openclassrooms.chatop.model;

import io.jsonwebtoken.Claims;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Wilhelm Zwertvaegher
 * Simple class to represent a JWT token and its claims
 */
@Getter
@Setter
@AllArgsConstructor
public class JwtToken {
    @NotNull
    private String token;
    private Claims claims;
}
