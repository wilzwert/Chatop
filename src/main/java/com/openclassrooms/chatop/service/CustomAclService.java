package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.model.Rental;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.SidRetrievalStrategyImpl;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CustomAclService {

    private MutableAclService aclService;

    private Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    private static Logger logger = LogManager.getLogger(CustomAclService.class);

    // default owner permissions for a domain object
    private static List<Permission> defaultOwnerPermissions = Arrays.asList(BasePermission.READ, BasePermission.WRITE, BasePermission.DELETE);

    public CustomAclService(MutableAclService aclService) {
        this.aclService = aclService;
    }

    public void createOwnerAcl(Rental rental) {
        logger.info("Creating owner acl");
        try {
            ObjectIdentity oid = new ObjectIdentityImpl(Rental.class, rental.getId());
            logger.info("Creating owner acl : retrieve sids for rental {}", rental);
            List<Sid> sids = new SidRetrievalStrategyImpl().getSids(SecurityContextHolder.getContext().getAuthentication());
            logger.info("Creating owner acl : creating acl object with sids {}", sids);
            MutableAcl acl = aclService.createAcl(oid);
            logger.info("Creating owner acl : creating acl object with acl {}", acl);
            createOwnerAcl(acl, sids);
        }
        catch(Exception e) {
            logger.error("Error creating owner acl {}", e.getMessage());
        }
    }

    private void createOwnerAcl(MutableAcl acl, List<Sid> sids) {
        createOwnerAcl(acl, sids, defaultOwnerPermissions);
    }

    @Transactional
    public void createOwnerAcl(MutableAcl acl, List<Sid> sids, List<Permission> permissions) {
        sids.forEach((Sid sid) ->
                permissions.forEach((Permission permission) -> {
                    logger.info("Creating owner acl for Sid {} and permission {}", sid, permission);
                    acl.insertAce(acl.getEntries().size(), permission, sid, true);
                })
        );
        aclService.updateAcl(acl);
    }
}
