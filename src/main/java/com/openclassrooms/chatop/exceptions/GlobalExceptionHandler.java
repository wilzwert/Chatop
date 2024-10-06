package com.openclassrooms.chatop.exceptions;

import com.openclassrooms.chatop.dto.ErrorResponseDto;
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

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponseDto> generateResponseStatusException(ResponseStatusException ex) {
        ErrorResponseDto ErrorResponseDto = new ErrorResponseDto();
        ErrorResponseDto.setMessage(ex.getReason());
        ErrorResponseDto.setStatus(String.valueOf(ex.getStatusCode().value()));
        ErrorResponseDto.setTime(new Date().toString());
        return new ResponseEntity<>(ErrorResponseDto, ex.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> generateMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ErrorResponseDto ErrorResponseDto = new ErrorResponseDto();
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        StringBuilder errors = new StringBuilder();

        for(FieldError fieldError : fieldErrors){
            errors.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage()).append(". ");
        }
        ErrorResponseDto.setMessage(errors.toString());
        ErrorResponseDto.setStatus(String.valueOf(ex.getStatusCode().value()));
        ErrorResponseDto.setTime(new Date().toString());
        return new ResponseEntity<>(ErrorResponseDto, ex.getStatusCode());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponseDto> generateMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        ErrorResponseDto ErrorResponseDto = new ErrorResponseDto();
        ErrorResponseDto.setStatus(String.valueOf(HttpStatus.PAYLOAD_TOO_LARGE.value()));
        ErrorResponseDto.setMessage("Payload too large");
        ErrorResponseDto.setTime(new Date().toString());
        return new ResponseEntity<>(ErrorResponseDto, HttpStatus.PAYLOAD_TOO_LARGE);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> generateStorageFileNotFoundException(StorageFileNotFoundException ex) {
        ErrorResponseDto ErrorResponseDto = new ErrorResponseDto();
        ErrorResponseDto.setStatus(String.valueOf(HttpStatus.NOT_FOUND.value()));
        ErrorResponseDto.setMessage("File not found");
        ErrorResponseDto.setTime(new Date().toString());
        return new ResponseEntity<>(ErrorResponseDto, HttpStatus.NOT_FOUND);
    }
}