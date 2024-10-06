package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.dto.*;
import com.openclassrooms.chatop.mapper.RentalMapper;
import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.model.User;
import com.openclassrooms.chatop.service.RentalService;
import com.openclassrooms.chatop.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rentals")
@Tag(name = "Rentals", description = "List, create or update rentals")
@ApiResponses({
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
        }),
        @ApiResponse(responseCode = "500", description = "Unexpected error", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
        })
})
public class RentalController {

    private final RentalService rentalService;

    private final UserService userService;

    private final RentalMapper rentalMapper = RentalMapper.INSTANCE;

    public RentalController(@Autowired RentalService rentalService, @Autowired UserService userService) {
        this.rentalService = rentalService;
        this.userService = userService;
    }

    @Operation(summary = "List all rentals", description = "List all rentals")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RentalsDto.class))
            })
    })
    @GetMapping("")
    public RentalsDto getAllRentals() {
        List<Rental> rentals = rentalService.findAllRentals();
        List<RentalDto> rentalDtos = new ArrayList<>();
        rentals.stream().map(rentalMapper::rentalToRentalDto).forEach(rentalDtos::add);
        return new RentalsDto(rentalDtos);
    }


    @Operation(summary = "Get a rental", description = "Get a rental")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RentalDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not found - The rental  was not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
            })
    })
    @GetMapping("/{id}")
    public RentalDto getRental(@PathVariable @Parameter(name = "id", description = "Rental id", example = "1") int id) {
        Optional<Rental> foundRental = rentalService.findRentalById(id);
        if(foundRental.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rental not found");
        }
        return rentalMapper.rentalToRentalDto(foundRental.get());
    }

    @Operation(summary = "Create a rental", description = "Create a rental")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RentalResponseDto.class))
            })
    })
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RentalResponseDto createRental(@Valid @ModelAttribute CreateRentalRequestDto createRentalDto, Principal principal) {
        try {
            Rental createRental = rentalMapper.rentalRequestDtoToRental(createRentalDto);
            Optional<User> foundUser = userService.findUserByEmail(principal.getName());
            if(foundUser.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Cannot get user info");
            }
            createRental.setOwnerId(foundUser.get().getId());
            createRental.setCreatedAt(LocalDateTime.now());
            createRental.setUpdatedAt(LocalDateTime.now());
            Rental rental = rentalService.createRental(createRental, createRentalDto.getPicture());
            return new RentalResponseDto("Rental "+rental.getName()+" created !");
        }
        catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Rental could not be created");
        }
    }

    @Operation(summary = "Update a rental", description = "Update a rental")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RentalResponseDto.class))
            })
    })
    @PutMapping("/{id}")
    public RentalResponseDto updateRental(@PathVariable int id, @Valid UpdateRentalRequestDto updateRentalDto) {
        try {
            Rental updateRental = rentalMapper.rentalRequestDtoToRental(updateRentalDto);
            updateRental.setId(id);
            Rental updatedRental = rentalService.updateRental(updateRental, updateRentalDto.getPicture());
            return new RentalResponseDto("Rental "+updatedRental.getName()+" updated !");
        }
        catch(EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rental not found");
        }
    }
}