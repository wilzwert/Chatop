package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.configuration.ServerProperties;
import com.openclassrooms.chatop.configuration.StorageProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class UploadedFileService implements FileService {

    private final ServerProperties serverProperties;
    private final StorageProperties storageProperties;

    public UploadedFileService(ServerProperties serverProperties, StorageProperties storageProperties) {
        this.serverProperties = serverProperties;
        this.storageProperties = storageProperties;

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
    public String generateUrl(String fileName) {
        return
            serverProperties.getProtocol()+"://"
            +serverProperties.getHostname()+":"
            +serverProperties.getPort()
            +"/"
            +storageProperties.getUploadDir()
            +"/"
            +fileName;
    }
}
