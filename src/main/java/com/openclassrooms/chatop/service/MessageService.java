package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.model.Message;

/**
 * @author Wilhelm Zwertvaegher
 */
public interface MessageService {
    Message createMessage(Message message);
}
