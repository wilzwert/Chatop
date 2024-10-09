package com.openclassrooms.chatop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * @author Wilhelm Zwertvaegher
 * DTO for Message creation
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Object expected for message creation request" )
public class CreateMessageRequestDto {
    @NotNull
    @JsonProperty("rental_id")
    @Schema(description = "Rental id" )
    private int rentalId;
    @NotNull
    @JsonProperty("user_id")
    @Schema(description = "User id (should be the current user !)" )
    private int userId;
    @NotBlank
    @Schema(description = "The message to send" )
    private String message;
}
