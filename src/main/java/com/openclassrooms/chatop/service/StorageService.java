package com.openclassrooms.chatop.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

/**
 * @author Wilhelm Zwertvaegher
 */
public interface StorageService {

    Path load(String filename);

    Resource loadAsResource(String filename);

    void init();

    String store(MultipartFile file);

    void delete(String filename);
}