package com.openclassrooms.chatop.listener;

import com.openclassrooms.chatop.exceptions.StorageFileNotFoundException;
import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.service.StorageService;
import jakarta.persistence.PostRemove;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Wilhelm Zwertvaegher
 * Rental entity listener used to handle picture deletion on Rental deletion
 */
@Slf4j
public class RentalListener {

    private final StorageService storageService;

    public RentalListener(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostRemove
    public void  postRemove(Rental rental) {
        if(rental.getPicture() != null && !rental.getPicture().isBlank()) {
            log.info("Removing picture for rental {}", rental.getPicture());
            try {
                storageService.delete(rental.getPicture());
            }
            catch(StorageFileNotFoundException e) {
                log.info("Picture file not found");
            }
        }
    }
}
