package com.openclassrooms.chatop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Common error object")
public class ErrorResponseDto {
    @Schema(description = "Http status code")
    private String status;
    @Schema(description = "Error message")
    private String message;
    @Schema(description = "Error date and time")
    private String time;
}