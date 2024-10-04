package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.model.User;
import com.openclassrooms.chatop.repository.RentalRepository;
import com.openclassrooms.chatop.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DbRentalService implements RentalService {

    private final RentalRepository rentalRepository;

    public DbRentalService(@Autowired final RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public List<Rental> findAllRentals() {
        return rentalRepository.findAll();
    }

    public Optional<Rental> findRentalById(final int id) {
        return rentalRepository.findById(id);
    }

    public Rental createRental(final Rental rental) {
        return rentalRepository.save(rental);
    }

    public Rental updateRental(final Rental rental) {
        Optional<Rental> existingRental = findRentalById(rental.getId());
        if(existingRental.isEmpty()) {
            throw new EntityNotFoundException();
        }
        rental.setId(existingRental.get().getId());
        rental.setOwnerId(existingRental.get().getOwnerId());
        rental.setUpdatedAt(LocalDateTime.now());
        return rentalRepository.save(rental);
    }
}
