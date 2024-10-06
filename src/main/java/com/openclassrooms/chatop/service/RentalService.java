package com.openclassrooms.chatop.service;


import com.openclassrooms.chatop.model.Rental;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface RentalService {
    Rental createRental(Rental rental, MultipartFile multipartFile);
    Rental updateRental(Rental rental, MultipartFile multipartFile);
    List<Rental> findAllRentals();
    Optional<Rental> findRentalById(final int id);
    String storePicture(final MultipartFile multipartFile);
    public void deleteRental(Rental rental);
}
