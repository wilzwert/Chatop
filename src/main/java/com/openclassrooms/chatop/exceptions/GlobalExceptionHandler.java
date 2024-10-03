package com.openclassrooms.chatop.exceptions;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Data
    public static class ErrorDTO {
        private String status;
        private String message;
        private String time;
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorDTO> generateResponseStatusException(ResponseStatusException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(ex.getReason());
        errorDTO.setStatus(String.valueOf(ex.getStatusCode().value()));
        errorDTO.setTime(new Date().toString());
        return new ResponseEntity<ErrorDTO>(errorDTO, ex.getStatusCode());
    }
}