package com.openclassrooms.chatop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @author Wilhelm Zwertvaegher
 * DTO used to send a User in a http response
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representation of a user" )
public class UserDto {
    private int id;

    @Schema(description = "User email")
    private String email;

    @Schema(description = "User name")
    private String name;

    @JsonProperty("created_at")
    @Schema(description = "User creation date")
    private String createdAt;

    @JsonProperty("updated_at")
    @Schema(description = "User update date")
    private String updatedAt;
}
