package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.dto.CreateMessageRequestDto;
import com.openclassrooms.chatop.dto.ErrorResponseDto;
import com.openclassrooms.chatop.dto.MessageResponseDto;
import com.openclassrooms.chatop.mapper.MessageMapper;
import com.openclassrooms.chatop.model.Message;
import com.openclassrooms.chatop.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/messages")
@Tag(name = "Messages", description = "Create messages")
@ApiResponses({
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
        }),
        @ApiResponse(responseCode = "500", description = "Unexpected error", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
        })
})
public class MessageController {

    private final MessageService messageService;

    private final MessageMapper messageMapper = MessageMapper.INSTANCE;

    public MessageController(@Autowired MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(summary = "Create a message", description = "Create a message for a Rental")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses({
           @ApiResponse(responseCode = "200", description = "Message created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponseDto.class))
            })
    })
    @PostMapping("/")
    public MessageResponseDto createMessage(@Valid @RequestBody CreateMessageRequestDto createMessageRequestDto) {
        try {
            Message createMessage = messageMapper.messageRequestDtoToMessage(createMessageRequestDto);
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
