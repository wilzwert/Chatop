package com.openclassrooms.chatop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Message representation" )
public class MessageDto {
    private int id;

    @JsonProperty("rental_id")
    @Schema(description = "Rental id")
    private int rentalId;

    @JsonProperty("user_id")
    @Schema(description = "Sender user id")
    private int userId;

    @Schema(description = "The message sent")
    private String message;

    @JsonProperty("created_at")
    @Schema(description = "Creation date")
    private String createdAt;

    @JsonProperty("updated_at")
    @Schema(description = "Update date")
    private String updatedAt;
}
