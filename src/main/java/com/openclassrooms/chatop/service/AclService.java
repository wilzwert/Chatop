package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.model.User;

/**
 * @author Wilhelm Zwertvaegher
 * We use a custom service to handle acls creation and removal on domain objects
 * and handle user acls deletion
 */
public interface AclService {

    void grantOwnerPermissions(Rental rental);
    void removeAllPermissions(Rental rental);
    void removeAllPermissions(User user);
    void removeUser(User user);

}
