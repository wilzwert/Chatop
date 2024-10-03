package com.openclassrooms.chatop.mapper;

import com.openclassrooms.chatop.dto.UserDto;
import com.openclassrooms.chatop.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    DateTimeFormatter DATE_FORMATTER_USER_TO_DTO = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    DateTimeFormatter DATE_FORMATTER_DTO_TO_USER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    // Assuming createdAt and updatedAt are available in User entity or will be manually set in DTO
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    UserDto userToUserDTO(User user);

    @InheritInverseConfiguration
    User userDTOToUser(UserDto userDto);

    default String map(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATE_FORMATTER_USER_TO_DTO) : null;
    }

    default LocalDateTime map(String dateTime) {
        return dateTime != null ? LocalDateTime.parse(dateTime, DATE_FORMATTER_DTO_TO_USER) : null;
    }
}
