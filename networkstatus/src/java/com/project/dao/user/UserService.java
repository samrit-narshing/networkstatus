/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.dao.user;

import com.project.model.user.RoleResource;
import com.project.model.user.RoletListResource;
import com.project.model.user.UserResource;
import com.project.model.user.UsertListResource;
import com.project.model.util.EmailData;
import com.project.model.util.FileInfo;
import com.project.model.util.UploadedFileContent;
import com.project.util.searchcriteria.UserSearchCriteria;

/**
 *
 * @author SAM
 */
public interface UserService {

    public UserResource createUser(UserResource userResource) throws Exception;

    public RoletListResource readRoles() throws Exception;

    public RoleResource readRolesByID(Long id) throws Exception;

    public UserResource readUserByUsername(String userName) throws Exception;

    public UserResource readUserByUsername_ONLY_FOR_REST_LOGIN(String userName) throws Exception;

    public UserResource readUserByUsernameAndPassword(String userName, String password) throws Exception;

    public UserResource readUserByID(Long id) throws Exception;

    public UsertListResource readUsersBySearchCriteria(UserSearchCriteria searchCriteria) throws Exception;

    public UserResource updateUser(Long id, UserResource userResource) throws Exception;

    public UserResource updateUserDetails(Long id, UserResource userResource) throws Exception;

    public UserResource updateUserPassword(Long id, UserResource userResource) throws Exception;

    public UserResource updateUserExpiredPassword(Long id, UserResource userResource) throws Exception;

    public UserResource deleteUserByID(Long id) throws Exception;

    public UserResource emailUserByID(Long id, String password) throws Exception;

    public UserResource sendEmailToUser(EmailData emailData) throws Exception;

    public FileInfo uploadUserProfileImageByObject(UploadedFileContent uploadedFileContent) throws Exception;

    public UserResource updateUserEnabledStatus(Long id) throws Exception;

}
