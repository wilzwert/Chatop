package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.model.Message;
import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.repository.MessageRepository;
import com.openclassrooms.chatop.repository.RentalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DbMessageService implements MessageService {

    private final MessageRepository messageRepository;

    public DbMessageService(@Autowired final MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message createMessage(final Message message) {
        return messageRepository.save(message);
    }

}
