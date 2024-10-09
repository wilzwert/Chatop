package com.openclassrooms.chatop.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.openclassrooms.chatop.configuration.StorageProperties;
import com.openclassrooms.chatop.exceptions.StorageException;
import com.openclassrooms.chatop.exceptions.StorageFileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Wilhelm Zwertvaegher
 */
@Service
@Slf4j
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    public FileSystemStorageService(StorageProperties properties) {

        if(properties.getUploadDir().trim().isEmpty()) {
            log.error("Cannot initialize service: upload location is empty");
            throw new StorageException("File upload location can not be Empty.");
        }

        this.rootLocation = Paths.get(properties.getUploadDir());
    }

    @Override
    public String store(MultipartFile file) {
        try {
            if (file.isEmpty() || file.getOriginalFilename() == null) {
                log.error("Cannot store an empty file");
                throw new StorageException("Failed to store empty file.");
            }

            // generate unique file name to avoid uploads collisions
            String uniqueFileName = java.util.UUID.randomUUID()
                    +"."
                    + file.getOriginalFilename().split("\\.")[1];

            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(uniqueFileName))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                log.error("Cannot store file outside current directory.");
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }

            return uniqueFileName;
        }
        catch (IOException e) {
            log.error("Failed to store file", e);
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public void delete(String filename) {
        Resource resource = loadAsResource(filename);
        try {
            boolean result = resource.getFile().delete();
            if(!result) {
                log.error("Cannot get file for deletion: {}", filename);
                throw new StorageFileNotFoundException("Cannot get file for deletion");
            }
        } catch (IOException e) {
            log.error("Cannot get file for deletion: {}", filename, e);
            throw new StorageFileNotFoundException("Cannot get file for deletion", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                log.info("Resource loaded for {}", filename);
                return resource;
            }
            else {
                log.error("Could not read file {}", filename);
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename
                );

            }
        }
        catch (MalformedURLException e) {
            log.error("Could not read file {}", filename, e);
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            log.error("Could not initialize storage", e);
            throw new StorageException("Could not initialize storage", e);
        }
    }
}