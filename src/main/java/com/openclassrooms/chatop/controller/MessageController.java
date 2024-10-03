package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.model.Message;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @PostMapping("/")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public String createMessage(@RequestBody Message message) {
        return "TODO : create message";
    }
}
