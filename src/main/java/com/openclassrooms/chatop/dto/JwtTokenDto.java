package com.openclassrooms.chatop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Wilhelm Zwertvaegher
 * DTO used to send a token embedded in the http response
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Schema(description = "JWT token response on login success" )
public class JwtTokenDto {
    @Schema(description = "The JWT token")
    private String token;
}
