/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.dao.user;

import com.project.model.user.RoleResource;
import com.project.model.user.RoletListResource;
import com.project.util.searchcriteria.RoleSearchCriteria;

/**
 *
 * @author SAM
 */
public interface RoleService {

    public RoleResource createRole(RoleResource userResource) throws Exception;

    public RoleResource readRolesByID(Long id) throws Exception;

    public RoletListResource readRolesBySearchCriteria(RoleSearchCriteria searchCriteria) throws Exception;

    public RoleResource updateRole(Long id, RoleResource userResource) throws Exception;

    public RoleResource updateRoleEnabledStatus(Long id) throws Exception;

    public RoleResource readRoleByName(String name) throws Exception;

}
