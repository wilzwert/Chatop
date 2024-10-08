package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.dto.CreateMessageRequestDto;
import com.openclassrooms.chatop.dto.ErrorResponseDto;
import com.openclassrooms.chatop.dto.MessageResponseDto;
import com.openclassrooms.chatop.mapper.MessageMapper;
import com.openclassrooms.chatop.model.Message;
import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.model.User;
import com.openclassrooms.chatop.service.MessageService;
import com.openclassrooms.chatop.service.RentalService;
import com.openclassrooms.chatop.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Optional;

/**
 * @author Wilhelm Zwertvaegher
 * Rest controller for messages related endpoints
 */
@RestController
@Slf4j
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
    private final UserService userService;

    private final RentalService rentalService;

    private final MessageService messageService;

    private final MessageMapper messageMapper;

    public MessageController(
            MessageService messageService,
            UserService userService,
            RentalService rentalService,
            MessageMapper messageMapper) {
        this.messageService = messageService;
        this.userService = userService;
        this.rentalService = rentalService;
        this.messageMapper = messageMapper;
    }

    @Operation(summary = "Create a message", description = "Create a message for a Rental")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses({
           @ApiResponse(responseCode = "200", description = "Message created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponseDto.class))
            })
    })
    @PostMapping("/")
    public MessageResponseDto createMessage(@Valid @RequestBody CreateMessageRequestDto createMessageRequestDto, Principal principal) {
        log.info("Create a message");

        Optional<User> foundUser = userService.findUserByEmail(principal.getName());
        if(foundUser.isEmpty()) {
            log.warn("Create a message : couldn't get user info");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Cannot get user info");
        }

        if(createMessageRequestDto.getUserId() != foundUser.get().getId()) {
            log.warn("Create a message : request user id does not match current user info");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Cannot get user info");
        }

        Optional<Rental> foundRental = rentalService.findRentalById(createMessageRequestDto.getRentalId());
        if(foundRental.isEmpty()) {
            log.warn("Create a message : couldn't find rental {}", createMessageRequestDto.getRentalId());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Cannot get rental info");
        }

        try {
            Message createMessage = messageMapper.messageRequestDtoToMessage(createMessageRequestDto);
            createMessage.setUser(foundUser.get());
            createMessage.setRental(foundRental.get());
            Message message = messageService.createMessage(createMessage);
            log.info("Message created : {}", message);
        }
        catch(Exception e) {
            log.error("Create a message : message could not be created", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Message could not be created"+e.getMessage());
        }

        return new MessageResponseDto("Message created !");
    }
}