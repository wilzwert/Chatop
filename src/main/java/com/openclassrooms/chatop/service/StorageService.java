package com.openclassrooms.chatop.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    Path load(String filename);

    Resource loadAsResource(String filename);

    void init();

    void store(MultipartFile file);

    void delete(String filename);
}