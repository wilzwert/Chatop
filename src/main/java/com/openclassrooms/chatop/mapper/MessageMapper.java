package com.openclassrooms.chatop.mapper;

import com.openclassrooms.chatop.dto.MessageDto;
import com.openclassrooms.chatop.dto.CreateMessageRequestDto;
import com.openclassrooms.chatop.model.Message;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MessageMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "rental.id", target = "rentalId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "message", target = "message")

    // Assuming createdAt and updatedAt are available in Message entity or will be manually set in DTO
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy/MM/dd")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy/MM/dd")
    MessageDto messageToMessageDto(Message message);

    @InheritInverseConfiguration
    Message messageRequestDtoToMessage(CreateMessageRequestDto createMessageRequestDto);
}
