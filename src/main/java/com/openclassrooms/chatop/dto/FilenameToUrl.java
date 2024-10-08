package com.openclassrooms.chatop.dto;

import com.openclassrooms.chatop.configuration.ServerProperties;
import com.openclassrooms.chatop.configuration.StorageProperties;
import org.springframework.stereotype.Component;

@Component
public class FilenameToUrl {

    protected ServerProperties serverProperties;

    protected StorageProperties storageProperties;

    public FilenameToUrl(ServerProperties serverProperties, StorageProperties storageProperties) {
        this.serverProperties = serverProperties;
        this.storageProperties = storageProperties;
    }

    public String filenameToUrl(String filename) {
        return serverProperties.getProtocol()+"://"
                +serverProperties.getHostname()+":"
                +serverProperties.getPort()+"/"
                +storageProperties.getUploadDir()+"/"
                +filename;
    }
}
