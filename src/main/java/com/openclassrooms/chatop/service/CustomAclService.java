package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.model.User;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.domain.SidRetrievalStrategyImpl;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomAclService implements AclService {

    private final MutableAclService aclService;

    private static final Logger logger = LogManager.getLogger(CustomAclService.class);

    private static final String DELETE_SID = "DELETE FROM acl_sid WHERE sid = ?";

    private static final String SELECT_OBJECT_IDENTITIES_FOR_SID = "SELECT DISTINCT acl_object_identity, class FROM acl_entry " +
            "INNER JOIN acl_object_identity ON acl_entry.acl_object_identity = acl_object_identity.id " +
            "INNER JOIN acl_class ON acl_object_identity.object_id_class = acl_class.id " +
            "INNER JOIN acl_sid ON acl_entry.sid = acl_sid.id " +
            "WHERE acl_sid.sid = ?";

    // default owner permissions for a domain object
    private static final List<Permission> defaultOwnerPermissions = Arrays.asList(BasePermission.READ, BasePermission.WRITE, BasePermission.DELETE);

    private JdbcTemplate jdbcTemplate;

    public CustomAclService(MutableAclService aclService, JdbcTemplate jdbcTemplate) {
        this.aclService = aclService;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void grantOwnerPermissions(Rental rental) {
        logger.info("Creating owner acl for rental {}", rental.getId());
        try {
            ObjectIdentity oid = new ObjectIdentityImpl(Rental.class, rental.getId());
            List<Sid> sids = new SidRetrievalStrategyImpl().getSids(SecurityContextHolder.getContext().getAuthentication());
            MutableAcl acl = aclService.createAcl(oid);
            logger.info("Granting owner permissions for rental  {}, oid {}, sids {}", rental.getId(), oid, sids);
            grantOwnerPermissions(acl, sids);
        }
        catch(Exception e) {
            logger.error("Error creating owner acl {}", e.getMessage());
            throw e;
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

    @Override
    public void removeAllPermissions(Rental rental) {
        ObjectIdentity oid = new ObjectIdentityImpl(Rental.class, rental.getId());
        logger.info("Removing all permissions for rental {}, oid {}", rental.getId(), oid);
        aclService.deleteAcl(oid, true);
        logger.info("All permissions removed.");
    }

    @Override
    public void removeAllPermissions(User user) {
        PrincipalSid sid = new PrincipalSid(user.getEmail());

        // lister les objets pour lesquels l'utilisateur a des permissions
        List<ObjectIdentity> objectIdentities = jdbcTemplate.query(SELECT_OBJECT_IDENTITIES_FOR_SID,
                (row, rowNum) -> {
                    logger.info("Remove all permissions : genereate OID with {}", row);
                    return new ObjectIdentityImpl(
                            row.getString("class"),
                            row.getLong("acl_object_identity")
                    );
                },
                new Object[] { sid.getPrincipal()}
        );

        if(!objectIdentities.isEmpty()) {
            try {
                // get acls for found object identities, filtered by sid
                Map<ObjectIdentity, Acl> acls = aclService.readAclsById(objectIdentities, List.of(sid));
                logger.info("Remove all permissions, found acls {}", acls);
                for (Acl acl : acls.values()) {
                    MutableAcl mutableAcl = (MutableAcl) acl;
                    for (int i = 0; i < acl.getEntries().size(); i++) {
                        if (acl.getEntries().get(i).getSid().equals(sid)) {
                            logger.info("Remove all permissions : remove entry {}", acl.getEntries().get(i));
                            mutableAcl.deleteAce(i);
                            // deletion removes index, we have to manually adjust
                            i--;
                        }
                    }

                    if (mutableAcl.getEntries().isEmpty()) {
                        logger.info("Remove all permissions : delete acl {}", mutableAcl);
                        aclService.deleteAcl(acl.getObjectIdentity(), true);
                    } else {
                        logger.info("Remove all permissions : acl entries not empty {}", mutableAcl.getEntries());
                        aclService.updateAcl(mutableAcl);
                    }
                }
            } catch (NotFoundException e) {
                logger.info("Remove all permissions : no acls found for {}", objectIdentities);
            } catch (Exception e) {
                logger.error("Remove all permissions : error encountered {}", e.getMessage());
            }
        }
    }

    @Override
    public void removeUser(User user) {
        removeAllPermissions(user);
        PrincipalSid sid = new PrincipalSid(user.getEmail());
        try {
            logger.info("Remove user  : deleting sid");
            jdbcTemplate.update(DELETE_SID, sid.getPrincipal());
        } catch (DataAccessException e) {
            logger.error("Remove user  :error encountered while deleting sid {}", e.getMessage());
        }
    }
}
