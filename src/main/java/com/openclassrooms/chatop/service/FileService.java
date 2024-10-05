package com.openclassrooms.chatop.service;

import org.springframework.core.io.Resource;

public interface FileService {
    String generateUrl(Resource resource);
    void deleteFileFromUrl(String fileUrl);
}
