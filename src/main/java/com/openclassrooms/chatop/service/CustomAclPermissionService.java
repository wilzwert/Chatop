package com.openclassrooms.chatop.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.*;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Wilhelm Zwertvaegher
 */
@Service
@Slf4j
public class CustomAclPermissionService implements AclPermissionService {
    
    // default owner permissions for a domain object
    private static final List<Permission> defaultOwnerPermissions = Arrays.asList(BasePermission.READ, BasePermission.WRITE, BasePermission.DELETE);

    private final MutableAclService aclService;

    public CustomAclPermissionService(MutableAclService aclService) {
        this.aclService = aclService;
    }

    @Transactional
    public void grantOwnerPermissions(ObjectIdentity objectIdentity, List<Sid> sids) {
        MutableAcl acl = aclService.createAcl(objectIdentity);
        sids.forEach((Sid sid) ->
                defaultOwnerPermissions.forEach((Permission permission) -> {
                    log.info("Creating owner acl for Sid {} and permission {}", sid, permission);
                    acl.insertAce(acl.getEntries().size(), permission, sid, true);
                })
        );
        aclService.updateAcl(acl);
    }

    @Override
    @Transactional
    public void removeAllPermissions(ObjectIdentity objectIdentity) {
        log.info("Removing all permissions for object identity {}", objectIdentity);
        aclService.deleteAcl(objectIdentity, true);
        log.info("All permissions removed");
    }

    @Override
    @Transactional
    public void removeAllPermissions(List<ObjectIdentity> objectIdentities, Sid sid) {
        try {
            // get acls for found object identities, filtered by sid
            log.info("Removing all permissions for Oids {}", objectIdentities);
            Map<ObjectIdentity, Acl> acls = aclService.readAclsById(objectIdentities);
            log.info("Remove all permissions, found acls {}", acls);
            for (Acl acl : acls.values()) {
                MutableAcl mutableAcl = (MutableAcl) acl;
                for (int i = 0; i < acl.getEntries().size(); i++) {
                    AccessControlEntry aclEntry = acl.getEntries().get(i);
                    if(aclEntry.getSid().equals(sid)) {
                        log.info("Remove all permissions : remove entry {}", acl.getEntries().get(i));
                        mutableAcl.deleteAce(i);
                        // deletion removes index, we have to manually adjust
                        i--;
                    }
                }

                if (mutableAcl.getEntries().isEmpty()) {
                    log.info("Remove all permissions : delete acl {}", mutableAcl);
                    aclService.deleteAcl(acl.getObjectIdentity(), true);
                } else {
                    log.info("Remove all permissions : acl entries not empty {}", mutableAcl.getEntries());
                    aclService.updateAcl(mutableAcl);
                }
            }
        } catch (NotFoundException e) {
            log.info("Remove all permissions : no acls found for {}", objectIdentities, e);
        }
    }
}
