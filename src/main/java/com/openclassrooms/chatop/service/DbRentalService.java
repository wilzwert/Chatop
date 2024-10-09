package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.repository.RentalRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * @author Wilhelm Zwertvaegher
 */
@Service
public class DbRentalService implements RentalService {

    private final RentalRepository rentalRepository;

    private final StorageService storageService;

    private final AclService aclService;

    public DbRentalService(
            final RentalRepository rentalRepository,
            final StorageService storageService,
            final AclService aclService) {
        this.rentalRepository = rentalRepository;
        this.storageService = storageService;
        this.aclService = aclService;
    }

    public List<Rental> findAllRentals() {
        return rentalRepository.findAll();
    }

    public Optional<Rental> findRentalById(final int id) {
        return rentalRepository.findById(id);
    }

    @Transactional
    public Rental createRental(final Rental rental, MultipartFile multipartFile) {
        rental.setPicture(storePicture(multipartFile));
        Rental newRental = rentalRepository.save(rental);
        aclService.grantOwnerPermissions(rental);
        return newRental;
    }

    public Rental updateRental(final Rental updateRental, MultipartFile multipartFile) {
        Optional<Rental> existingRental = findRentalById(updateRental.getId());
        if(existingRental.isEmpty()) {
            throw new EntityNotFoundException();
        }

        Rental rental = existingRental.get();
        rental.setName(updateRental.getName());
        rental.setDescription(updateRental.getDescription());
        rental.setSurface(updateRental.getSurface());
        rental.setPrice(updateRental.getPrice());

        if(multipartFile != null && !multipartFile.isEmpty() && multipartFile.getOriginalFilename() != null) {
            // get previous picture so that we can delete it if picture is updated
            String previousPicture = rental.getPicture();
            rental.setPicture(storePicture(multipartFile));
            if(previousPicture != null && !previousPicture.isBlank()) {
                storageService.delete(previousPicture);
            }
        }

        return rentalRepository.save(rental);
    }

    public void deleteRental(final Rental rental) {
            rentalRepository.delete(rental);
            aclService.removeAllPermissions(rental);
    }

    public String storePicture(final MultipartFile multipartFile) {
        return storageService.store(multipartFile);
    }
}
