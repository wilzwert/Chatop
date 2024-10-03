package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.dto.RentalDto;
import com.openclassrooms.chatop.dto.RentalsDto;
import com.openclassrooms.chatop.mapper.RentalMapper;
import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
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

    @PostMapping("/")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public String createRental(@RequestBody Rental rental) {
        return "TODO : create rental";
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public String updateRental(@PathVariable int id) {
        return "TODO : update rental";
    }
}
