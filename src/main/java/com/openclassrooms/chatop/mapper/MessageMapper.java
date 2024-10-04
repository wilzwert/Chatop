package com.openclassrooms.chatop.mapper;

import com.openclassrooms.chatop.dto.MessageDto;
import com.openclassrooms.chatop.dto.MessageRequestDto;
import com.openclassrooms.chatop.dto.RentalDto;
import com.openclassrooms.chatop.dto.RentalRequestDto;
import com.openclassrooms.chatop.model.Message;
import com.openclassrooms.chatop.model.Rental;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MessageMapper {

    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "rentalId", target = "rentalId")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "message", target = "message")

    // Assuming createdAt and updatedAt are available in Message entity or will be manually set in DTO
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy/MM/dd")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy/MM/dd")
    MessageDto messageToMessageDto(Message message);

    @InheritInverseConfiguration
    Message messageRequestDtoToMessage(MessageRequestDto messageRequestDto);
}
