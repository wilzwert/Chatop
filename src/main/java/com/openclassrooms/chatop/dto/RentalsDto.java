package com.openclassrooms.chatop.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapper;

import java.util.List;


@RequiredArgsConstructor
@Getter
@Setter
public class RentalsDto {
    private final List<RentalDto> rentals;
}
