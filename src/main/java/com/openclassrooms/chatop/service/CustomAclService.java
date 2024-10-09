package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.model.User;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.domain.SidRetrievalStrategyImpl;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wilhelm Zwertvaegher
 */
@Service
@Slf4j
public class CustomAclService implements AclService {

    private final CustomAclPermissionService customAclPermissionService;

    private static final String DELETE_SID = "DELETE FROM acl_sid WHERE sid = ?";

    private static final String SELECT_OBJECT_IDENTITIES_FOR_SID = "SELECT DISTINCT acl_object_identity, class FROM acl_entry " +
            "INNER JOIN acl_object_identity ON acl_entry.acl_object_identity = acl_object_identity.id " +
            "INNER JOIN acl_class ON acl_object_identity.object_id_class = acl_class.id " +
            "INNER JOIN acl_sid ON acl_entry.sid = acl_sid.id " +
            "WHERE acl_sid.sid = ?";

    private JdbcTemplate jdbcTemplate;

    public CustomAclService(CustomAclPermissionService customAclPermissionService, JdbcTemplate jdbcTemplate) {
        this.customAclPermissionService = customAclPermissionService;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void grantOwnerPermissions(Rental rental) {
        log.info("Creating owner acl for rental {}", rental.getId());
        try {
            ObjectIdentity oid = new ObjectIdentityImpl(Rental.class, rental.getId());
            List<Sid> sids = getContextSid();
            customAclPermissionService.grantOwnerPermissions(oid, sids);
        }
        catch(Exception e) {
            log.error("Error creating owner acl for rental {}", rental.getId(), e);
        }
    }


    @Override
    @Transactional
    public void removeAllPermissions(Rental rental) {
        log.info("Removing all permissions for rental {}", rental.getId());
        ObjectIdentity oid = new ObjectIdentityImpl(Rental.class, rental.getId());
        customAclPermissionService.removeAllPermissions(oid);
        log.info("All permissions removed.");
    }

    @Override
    @Transactional
    public void removeAllPermissions(User user) {
        PrincipalSid sid = new PrincipalSid(user.getEmail());

        // lister les objets pour lesquels l'utilisateur a des permissions
        List<ObjectIdentity> objectIdentities = jdbcTemplate.query(SELECT_OBJECT_IDENTITIES_FOR_SID,
                (row, rowNum) -> {
                    log.info("Remove all permissions : genereate OID with {}", row);
                    return new ObjectIdentityImpl(
                            row.getString("class"),
                            row.getLong("acl_object_identity")
                    );
                },
                new Object[] { sid.getPrincipal()}
        );

        if(!objectIdentities.isEmpty()) {
            try {
                customAclPermissionService.removeAllPermissions(objectIdentities, sid);
            } catch (Exception e) {
                log.error("Remove all permissions : error encountered {}", e.getMessage());
            }
        }
    }

    @Transactional
    public void removeUser(User user) {
        removeAllPermissions(user);
        PrincipalSid sid = new PrincipalSid(user.getEmail());
        try {
            log.info("Remove user  : deleting sid");
            jdbcTemplate.update(DELETE_SID, sid.getPrincipal());
        } catch (DataAccessException e) {
            log.error("Remove user  :error encountered while deleting sid {}", e.getMessage());
        }
    }

    private List<Sid> getContextSid() {
        return new SidRetrievalStrategyImpl().getSids(SecurityContextHolder.getContext().getAuthentication());
    }
}