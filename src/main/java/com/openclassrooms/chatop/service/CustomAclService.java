package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.model.Rental;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.SidRetrievalStrategyImpl;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomAclService {

    private MutableAclService aclService;

    private Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    public CustomAclService(MutableAclService aclService) {
        this.aclService = aclService;
    }

    public void createOwnerAcl(Rental rental) {
        ObjectIdentity oid = new ObjectIdentityImpl(Rental.class, rental.getId());
        MutableAcl acl = aclService.createAcl(oid);
        List<Sid> sids = new SidRetrievalStrategyImpl().getSids(SecurityContextHolder.getContext().getAuthentication());
        sids.forEach((Sid sid) -> acl.insertAce(acl.getEntries().size(), BasePermission.DELETE, sid, true));
        aclService.updateAcl(acl);
    }
}
