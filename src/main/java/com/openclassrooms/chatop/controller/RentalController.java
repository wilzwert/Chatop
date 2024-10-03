package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.dto.RentalRequestDto;
import com.openclassrooms.chatop.dto.RentalDto;
import com.openclassrooms.chatop.dto.RentalResponseDto;
import com.openclassrooms.chatop.dto.RentalsDto;
import com.openclassrooms.chatop.mapper.RentalMapper;
import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.service.RentalService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    private final RentalMapper rentalMapper = RentalMapper.INSTANCE;

    public RentalController(@Autowired RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("")
    public RentalsDto getAllRentals() {
        List<Rental> rentals = rentalService.findAllRentals();
        List<RentalDto> rentalDtos = new ArrayList<RentalDto>();
        rentals.stream().map(rental -> rentalMapper.rentalToRentalDto(rental)).forEach(rentalDtos::add);
        return new RentalsDto(rentalDtos);
    }

    @GetMapping("/{id}")
    public RentalDto getRental(@PathVariable int id) {
        Optional<Rental> foundRental = rentalService.findRentalById(id);
        if(foundRental.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rental not found");
        }
        return rentalMapper.rentalToRentalDto(foundRental.get());
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RentalResponseDto createRental(@Valid @ModelAttribute RentalRequestDto createRentalDto) {
        try {
            Rental createRental = rentalMapper.rentalRequestDtoToRental(createRentalDto);
            // TODO : extract owner id from current token / authentication
            createRental.setOwnerId(1);
            // TODO handle image file upload
            createRental.setCreatedAt(LocalDateTime.now());
            createRental.setUpdatedAt(LocalDateTime.now());
            Rental rental = rentalService.createRental(createRental);
        }
        catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Rental coud not be created");
        }

        return new RentalResponseDto("Rental created !");
    }

    @PutMapping("/{id}")
    public RentalResponseDto updateRental(@PathVariable int id, @Valid RentalRequestDto createRentalDto) {
        try {
            Rental updateRental = rentalMapper.rentalRequestDtoToRental(createRentalDto);
            updateRental.setId(id);
            rentalService.updateRental(updateRental);
            return new RentalResponseDto("Rental updated !");
        }
        catch(EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rental not found");
        }
    }
}
