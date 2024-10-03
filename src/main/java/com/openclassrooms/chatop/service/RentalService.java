package com.openclassrooms.chatop.service;


import com.openclassrooms.chatop.model.Rental;

import java.util.List;
import java.util.Optional;

public interface RentalService {
    public List<Rental> findAllRentals();

    public Optional<Rental> findRentalById(final int id);
}
