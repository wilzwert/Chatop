package com.openclassrooms.chatop.listener;

import com.openclassrooms.chatop.exceptions.StorageFileNotFoundException;
import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.service.FileService;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PreUpdate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class RentalListener {

    private static final Logger logger = LogManager.getLogger(RentalListener.class);

    private final FileService fileService;

    public RentalListener(FileService fileService) {
        this.fileService = fileService;
    }

    @PostRemove
    public void  postRemove(Rental rental) {
        if(rental.getPicture() != null && !rental.getPicture().isBlank()) {
            logger.info("Removing picture for rental {}", rental.getPicture());
            try {
                fileService.deleteFileFromUrl(rental.getPicture());
            }
            catch(StorageFileNotFoundException e) {
                logger.info("Picture file not found");
            }
        }
    }
}
