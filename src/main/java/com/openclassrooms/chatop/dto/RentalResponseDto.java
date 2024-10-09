package com.openclassrooms.chatop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Wilhelm Zwertvaegher
 * DTO used to send a confirmation message on Rental creation or update
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Response message after rental creation or update" )
public class RentalResponseDto {
    @Schema(description = "The confirmation message")
    private String message;
}
