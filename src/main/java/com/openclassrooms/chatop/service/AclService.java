package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.model.Rental;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;

import java.util.List;

public interface AclService {

    void grantOwnerPermissions(Rental rental);
    void removeAllPermissions(Rental rental);

}
