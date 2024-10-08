package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.model.User;

public interface AclService {

    void grantOwnerPermissions(Rental rental);
    void removeAllPermissions(Rental rental);
    void removeAllPermissions(User user);
    void removeUser(User user);

}
