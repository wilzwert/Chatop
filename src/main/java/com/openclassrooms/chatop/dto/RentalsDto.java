package com.openclassrooms.chatop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;


@RequiredArgsConstructor
@Getter
@Setter
@Schema(description = "Representation of a list of rentals" )
public class RentalsDto {
    @Schema(description = "The list of rentals")
    private final List<RentalDto> rentals;
}
