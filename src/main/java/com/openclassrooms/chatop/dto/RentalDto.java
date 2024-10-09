package com.openclassrooms.chatop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @author Wilhelm Zwertvaegher
 * DTO used to send a Rental in an http response
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Rental representation" )
public class RentalDto {
    private int id;

    @Schema(description = "Rental name")
    private String name;

    @Schema(description = "Rental surface in square meters")
    private float surface;

    @Schema(description = "Rental price in euros per night")
    private float price;

    @Schema(description = "Rental picture URL")
    private String picture;

    @Schema(description = "Rental description")
    private String description;

    @JsonProperty("owner_id")
    @Schema(description = "Rental owner user id")
    private int ownerId;

    @JsonProperty("created_at")
    @Schema(description = "Creation date")
    private String createdAt;

    @JsonProperty("updated_at")
    @Schema(description = "Update date")
    private String updatedAt;
}
