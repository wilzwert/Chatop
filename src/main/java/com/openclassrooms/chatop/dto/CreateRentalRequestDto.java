package com.openclassrooms.chatop.dto;

import com.openclassrooms.chatop.validator.ImageFile;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Wilhelm Zwertvaegher
 * DTO for Rental creation request
 */
@Data
@Schema(description = "Object expected for rental creation request" )
public class CreateRentalRequestDto implements RentalActionRequestDto {
    @NotBlank(message = "The name is required")
    @Schema(description = "Name of the rental" )
    private String name;

    @NotNull(message = "Rental surface is required")
    @Schema(description = "Rental surface in square meters" )
    private float surface;

    @NotNull(message = "Rental price is required")
    @Schema(description = "Rental price in euros per day" )
    private float price;

    @NotNull(message = "Image file is required")
    @ImageFile
    @Schema(description = "Picture to display", name = "picture",  type="string", format = "binary")
    private MultipartFile picture;

    @NotBlank(message = "The description is required")
    @Schema(description = "Rental description" )
    private String description;
}
