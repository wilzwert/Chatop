package com.openclassrooms.chatop.dto;

import com.openclassrooms.chatop.validator.ImageFile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RentalRequestDto {
    @NotBlank(message = "The name is required")
    private String name;
    @NotNull(message = "Rental surface is required")
    private float surface;
    @NotNull(message = "Rental price is required")
    private float price;
    @ImageFile
    private MultipartFile picture;
    @NotBlank(message = "The description is required")
    private String description;
}
