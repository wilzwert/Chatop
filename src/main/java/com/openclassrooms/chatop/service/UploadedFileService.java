package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.configuration.ServerProperties;
import com.openclassrooms.chatop.configuration.StorageProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class UploadedFileService implements FileService {

    private final ServerProperties serverProperties;
    private final StorageProperties storageProperties;
    private final StorageService storageService;

    public UploadedFileService(ServerProperties serverProperties, StorageProperties storageProperties, StorageService storageService) {
        this.serverProperties = serverProperties;
        this.storageProperties = storageProperties;
        this.storageService = storageService;

    }

    @Override
    public String generateUrl(Resource resource) {
        return
            serverProperties.getProtocol()+"://"
            +serverProperties.getHostname()+":"
            +serverProperties.getPort()
            +"/"
            +storageProperties.getUploadDir()
            +"/"
            +resource.getFilename();
    }

    @Override
    public void deleteFileFromUrl(String fileUrl) {
        // get original filename
        String originalFilename = fileUrl.substring(fileUrl.lastIndexOf("/")+1);
        storageService.delete(originalFilename);
    }
}
