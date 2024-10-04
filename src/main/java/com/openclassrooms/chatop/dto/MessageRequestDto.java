package com.openclassrooms.chatop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class MessageRequestDto {
    @NotNull
    @JsonProperty("rental_id")
    private int rentalId;
    @NotNull
    @JsonProperty("user_id")
    private int userId;
    @NotBlank
    private String message;
}
