package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.model.User;
import jakarta.transaction.Transactional;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;

import java.util.List;

public interface AclPermissionService {

    void grantOwnerPermissions(ObjectIdentity objectIdentity, List<Sid> sids);

    void removeAllPermissions(ObjectIdentity objectIdentity);

    void removeAllPermissions(List<ObjectIdentity> objectIdentities, Sid sid);

}
