package com.openclassrooms.chatop.listener;

import com.openclassrooms.chatop.exceptions.StorageFileNotFoundException;
import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.service.StorageService;
import jakarta.persistence.PostRemove;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RentalListener {

    private static final Logger logger = LogManager.getLogger(RentalListener.class);

    private final StorageService storageService;

    public RentalListener(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostRemove
    public void  postRemove(Rental rental) {
        if(rental.getPicture() != null && !rental.getPicture().isBlank()) {
            logger.info("Removing picture for rental {}", rental.getPicture());
            try {
                storageService.delete(rental.getPicture());
            }
            catch(StorageFileNotFoundException e) {
                logger.info("Picture file not found");
            }
        }
    }
}
