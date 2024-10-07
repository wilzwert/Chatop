package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.model.Rental;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.SidRetrievalStrategyImpl;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CustomAclService implements AclService {

    private final MutableAclService aclService;

    private static final Logger logger = LogManager.getLogger(CustomAclService.class);

    // default owner permissions for a domain object
    private static final List<Permission> defaultOwnerPermissions = Arrays.asList(BasePermission.READ, BasePermission.WRITE, BasePermission.DELETE);

    public CustomAclService(MutableAclService aclService) {
        this.aclService = aclService;
    }

    public void grantOwnerPermissions(Rental rental) {
        logger.info("Creating owner acl for rental {}", rental);
        try {
            ObjectIdentity oid = new ObjectIdentityImpl(Rental.class, rental.getId());
            List<Sid> sids = new SidRetrievalStrategyImpl().getSids(SecurityContextHolder.getContext().getAuthentication());
            MutableAcl acl = aclService.createAcl(oid);
            logger.info("Granting owner permissions for rental  {}, oid {}, sids {}", rental, oid, sids);
            grantOwnerPermissions(acl, sids);
        }
        catch(Exception e) {
            logger.error("Error creating owner acl {}", e.getMessage());
        }
    }

    public void grantOwnerPermissions(MutableAcl acl, List<Sid> sids) {
        grantOwnerPermissions(acl, sids, defaultOwnerPermissions);
    }

    @Transactional
    public void grantOwnerPermissions(MutableAcl acl, List<Sid> sids, List<Permission> permissions) {
        sids.forEach((Sid sid) ->
                permissions.forEach((Permission permission) -> {
                    logger.info("Creating owner acl for Sid {} and permission {}", sid, permission);
                    acl.insertAce(acl.getEntries().size(), permission, sid, true);
                })
        );
        aclService.updateAcl(acl);
    }

    public void removeAllPermissions(Rental rental) {
        ObjectIdentity oid = new ObjectIdentityImpl(Rental.class, rental.getId());
        logger.info("Removing all permissions for rental {}, oid {}", rental, oid);
        aclService.deleteAcl(oid, true);
        logger.info("All permissions removed.");
    }
}
