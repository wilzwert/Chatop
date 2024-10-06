package com.openclassrooms.chatop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Common error object")
public class ErrorResponseDto {
    private String status;
    private String message;
    private String time;
}