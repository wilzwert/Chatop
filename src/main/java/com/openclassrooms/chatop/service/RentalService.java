package com.openclassrooms.chatop.service;


import com.openclassrooms.chatop.model.Rental;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface RentalService {
    public Rental createRental(Rental rental, MultipartFile multipartFile);
    // public Rental updateRental(Rental rental);
    public Rental updateRental(Rental rental, MultipartFile multipartFile);
    public List<Rental> findAllRentals();
    public Optional<Rental> findRentalById(final int id);
    public String storePicture(final MultipartFile multipartFile);
}
