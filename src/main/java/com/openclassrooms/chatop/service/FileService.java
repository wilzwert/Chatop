package com.openclassrooms.chatop.service;

import org.springframework.core.io.Resource;

public interface FileService {
    public String generateUrl(Resource resource);
    public String generateUrl(String fileName);
    public void deleteFileFromUrl(String fileUrl);
}
