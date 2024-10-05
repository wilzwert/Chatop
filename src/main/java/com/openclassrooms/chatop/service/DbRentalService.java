package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.repository.RentalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DbRentalService implements RentalService {

    private final RentalRepository rentalRepository;

    private final StorageService storageService;

    private final FileService fileService;

    public DbRentalService(@Autowired final RentalRepository rentalRepository, @Autowired final StorageService storageService, @Autowired final FileService fileService) {
        this.rentalRepository = rentalRepository;
        this.storageService = storageService;
        this.fileService = fileService;
    }

    public List<Rental> findAllRentals() {
        return rentalRepository.findAll();
    }

    public Optional<Rental> findRentalById(final int id) {
        return rentalRepository.findById(id);
    }

    public Rental createRental(final Rental rental, MultipartFile multipartFile) {
        // TODO handle multipartFile storage and url generation
        rental.setPicture(storePicture(multipartFile));
        return rentalRepository.save(rental);
    }

    public Rental updateRental(final Rental rental, MultipartFile multipartFile) {
        Optional<Rental> existingRental = findRentalById(rental.getId());
        if(existingRental.isEmpty()) {
            throw new EntityNotFoundException();
        }
        rental.setId(existingRental.get().getId());
        rental.setOwnerId(existingRental.get().getOwnerId());
        rental.setUpdatedAt(LocalDateTime.now());

        if(multipartFile != null && !multipartFile.isEmpty() && multipartFile.getOriginalFilename() != null) {
            // get previous picture so that we can delete it if picture is updated
            String previousPicture = existingRental.get().getPicture();
            rental.setPicture(storePicture(multipartFile));
            if(previousPicture != null && !previousPicture.isBlank()) {
                fileService.deleteFileFromUrl(previousPicture);
            }
        }

        return rentalRepository.save(rental);
    }

    public String storePicture(final MultipartFile multipartFile) {
        String newFilename = storageService.store(multipartFile);
        return fileService.generateUrl(storageService.loadAsResource(newFilename));
    }
}
