package com.openclassrooms.chatop.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> generateMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        StringBuilder errors = new StringBuilder();

        for(FieldError fieldError : fieldErrors){
            errors.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage()).append(". ");
        }
        errorDTO.setMessage(errors.toString());
        errorDTO.setStatus(String.valueOf(ex.getStatusCode().value()));
        errorDTO.setTime(new Date().toString());
        return new ResponseEntity<ErrorDTO>(errorDTO, ex.getStatusCode());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorDTO> generateMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(String.valueOf(HttpStatus.PAYLOAD_TOO_LARGE.value()));
        errorDTO.setMessage("Payload too large");
        errorDTO.setTime(new Date().toString());
        return new ResponseEntity<ErrorDTO>(errorDTO, HttpStatus.PAYLOAD_TOO_LARGE);
    }
}