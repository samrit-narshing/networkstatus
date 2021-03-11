package com.project.dao.user;

import com.project.model.user.DepartmentUserListResource;
import com.project.model.user.DepartmentUserResource;
import com.project.model.user.RoleResource;
import com.project.model.user.RoletListResource;
import com.project.model.util.FileInfo;
import com.project.model.util.UploadedFileContent;
import com.project.util.searchcriteria.DepartmentUserSearchCriteria;

/**
 *
 * @author Samrit
 */
public interface DepartmentUserService {

    public DepartmentUserResource createDepartmentUser(DepartmentUserResource departmentUserResource) throws Exception;

    public RoletListResource readRoles() throws Exception;

    public RoleResource readRolesByID(Long id) throws Exception;

    public DepartmentUserResource readDepartmentUserByUsername(String userName) throws Exception;

    public DepartmentUserResource readDepartmentUserByUsername_ONLY_FOR_REST_LOGIN(String userName) throws Exception;

    public DepartmentUserResource readDepartmentUserByUsernameAndPassword(String userName, String password) throws Exception;

    public DepartmentUserResource readDepartmentUserByID(Long id) throws Exception;

    public DepartmentUserListResource readDepartmentUsersBySearchCriteria(DepartmentUserSearchCriteria searchCriteria) throws Exception;

    public DepartmentUserResource updateDepartmentUser(Long id, DepartmentUserResource departmentUserResource) throws Exception;

    public DepartmentUserResource updateDepartmentUserDetails(Long id, DepartmentUserResource departmentUserResource) throws Exception;

    public DepartmentUserResource updateDepartmentUserPassword(Long id, DepartmentUserResource departmentUserResource) throws Exception;

    public DepartmentUserResource deleteDepartmentUserByID(Long id) throws Exception;

    public FileInfo uploadDepartmentUserProfileImageByObject(UploadedFileContent uploadedFileContent) throws Exception;

    public DepartmentUserResource updateDepartmentUserEnabledStatus(Long id) throws Exception;
}
