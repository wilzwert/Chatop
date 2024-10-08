package com.openclassrooms.chatop.mapper;

import com.openclassrooms.chatop.dto.CreateRentalRequestDto;
import com.openclassrooms.chatop.dto.FilenameToUrl;
import com.openclassrooms.chatop.dto.RentalDto;
import com.openclassrooms.chatop.dto.UpdateRentalRequestDto;
import com.openclassrooms.chatop.model.Rental;
import org.mapstruct.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = FilenameToUrl.class)
public abstract class RentalMapper {


    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "surface", target = "surface")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "picture", target = "picture", qualifiedByName = "filenameToUrl")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "owner.id", target = "ownerId")

    // Assuming createdAt and updatedAt are available in User entity or will be manually set in DTO
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy/MM/dd")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy/MM/dd")
    public abstract RentalDto rentalToRentalDto(Rental rental);

    // picture field gets handled in service with file uploading
    @InheritInverseConfiguration
    @Mapping(target = "picture", ignore = true)
    public abstract Rental rentalRequestDtoToRental(UpdateRentalRequestDto updateRentalRequestDto);

    @InheritInverseConfiguration
    @Mapping(target = "picture", ignore = true)
    public abstract Rental rentalRequestDtoToRental(CreateRentalRequestDto createRentalRequestDto);
    /*
    @Named(value="filenameToUrl")
    public String filenameToUrl(String filename) {
        return
                serverProperties.getProtocol()+"://"
                +serverProperties.getHostname()+":"
                +serverProperties.getPort()+"/"
                +storageProperties.getUploadDir()+"/"
                +filename;
    }
     */
}
