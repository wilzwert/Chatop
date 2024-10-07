package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.model.Rental;

public interface AclService {

    void grantOwnerPermissions(Rental rental);
    void removeAllPermissions(Rental rental);

}
