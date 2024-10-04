package com.openclassrooms.chatop.mapper;

import com.openclassrooms.chatop.dto.RentalDto;
import com.openclassrooms.chatop.dto.RentalRequestDto;
import com.openclassrooms.chatop.dto.UserDto;
import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper
public interface RentalMapper {

    RentalMapper INSTANCE = Mappers.getMapper(RentalMapper.class);
    //DateTimeFormatter DATE_FORMATTER_USER_TO_DTO = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    // DateTimeFormatter DATE_FORMATTER_DTO_TO_USER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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

    @InheritInverseConfiguration
    @Mapping(target = "picture", ignore = true)
    Rental rentalRequestDtoToRental(RentalRequestDto rentalRequestDto);

    /*
    default String map(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATE_FORMATTER_USER_TO_DTO) : null;
    }

    default LocalDateTime map(String dateTime) {
        return dateTime != null ? LocalDateTime.parse(dateTime, DATE_FORMATTER_DTO_TO_USER) : null;
    }*/
}
