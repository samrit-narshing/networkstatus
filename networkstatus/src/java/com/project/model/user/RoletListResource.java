package com.project.model.user;

import org.springframework.hateoas.ResourceSupport;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samrit
 */
public class RoletListResource extends ResourceSupport {

    private List<RoleResource> roles = new ArrayList<RoleResource>();

    public List<RoleResource> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleResource> roles) {
        this.roles = roles;
    }

}
