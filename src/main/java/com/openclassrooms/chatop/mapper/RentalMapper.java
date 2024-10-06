package com.openclassrooms.chatop.mapper;

import com.openclassrooms.chatop.dto.CreateRentalRequestDto;
import com.openclassrooms.chatop.dto.RentalDto;
import com.openclassrooms.chatop.dto.UpdateRentalRequestDto;
import com.openclassrooms.chatop.model.Rental;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RentalMapper {

    RentalMapper INSTANCE = Mappers.getMapper(RentalMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "surface", target = "surface")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "picture", target = "picture")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "ownerId", target = "ownerId")

    // Assuming createdAt and updatedAt are available in User entity or will be manually set in DTO
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy/MM/dd")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy/MM/dd")
    RentalDto rentalToRentalDto(Rental rental);

    // picture field gets handled in service with file uploading
    @InheritInverseConfiguration
    @Mapping(target = "picture", ignore = true)
    Rental rentalRequestDtoToRental(UpdateRentalRequestDto updateRentalRequestDto);

    @InheritInverseConfiguration
    @Mapping(target = "picture", ignore = true)
    Rental rentalRequestDtoToRental(CreateRentalRequestDto createRentalRequestDto);
}
