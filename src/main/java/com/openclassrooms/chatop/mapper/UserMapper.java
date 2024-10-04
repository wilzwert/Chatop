package com.openclassrooms.chatop.mapper;

import com.openclassrooms.chatop.dto.RegisterUserDto;
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

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    // Assuming createdAt and updatedAt are available in User entity or will be manually set in DTO
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy/MM/dd")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy/MM/dd")
    UserDto userToUserDTO(User user);

    @InheritInverseConfiguration
    User registerUserDtoToUser(RegisterUserDto userDto);
}
