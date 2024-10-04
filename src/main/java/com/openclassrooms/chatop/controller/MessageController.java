package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.dto.MessageDto;
import com.openclassrooms.chatop.dto.MessageRequestDto;
import com.openclassrooms.chatop.dto.MessageResponseDto;
import com.openclassrooms.chatop.dto.RentalResponseDto;
import com.openclassrooms.chatop.mapper.MessageMapper;
import com.openclassrooms.chatop.model.Message;
import com.openclassrooms.chatop.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    private final MessageMapper messageMapper = MessageMapper.INSTANCE;

    public MessageController(@Autowired MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/")
    public MessageResponseDto createMessage(@Valid @RequestBody MessageRequestDto messageRequestDto) {
        try {
            Message createMessage = messageMapper.messageRequestDtoToMessage(messageRequestDto);
            LocalDateTime now = LocalDateTime.now();
            createMessage.setCreatedAt(now);
            createMessage.setUpdatedAt(now);
            Message message = messageService.createMessage(createMessage);
        }
        catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Message could not be created");
        }

        return new MessageResponseDto("Message created !");
    }
}
