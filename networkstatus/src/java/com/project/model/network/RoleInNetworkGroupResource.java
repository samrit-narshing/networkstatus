/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.model.network;

import com.project.model.user.RoleResource;
import org.springframework.hateoas.ResourceSupport;

/**
 *
 * @author samri
 */
public class RoleInNetworkGroupResource extends ResourceSupport {

    private Long roleInNetworkGroupID = 0L;

    private RoleResource roleResource = new RoleResource();

    public Long getRoleInNetworkGroupID() {
        return roleInNetworkGroupID;
    }

    public void setRoleInNetworkGroupID(Long roleInNetworkGroupID) {
        this.roleInNetworkGroupID = roleInNetworkGroupID;
    }

    public RoleResource getRoleResource() {
        return roleResource;
    }

    public void setRoleResource(RoleResource roleResource) {
        this.roleResource = roleResource;
    }

}
