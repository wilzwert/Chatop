package com.openclassrooms.chatop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * @author Wilhelm Zwertvaegher
 * DTO used to send a list of Message entities in a http response
 */
@RequiredArgsConstructor
@Getter
@Setter
@Schema(description = "Representation of a list of rentals" )
public class RentalsDto {
    @Schema(description = "The list of rentals")
    private final List<RentalDto> rentals;
}
