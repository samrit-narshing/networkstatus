package com.project.model.user;

import org.springframework.hateoas.ResourceSupport;
import java.util.Set;

/**
 *
 * @author Samrit
 */
public class UserResource extends ResourceSupport {

    private Long userID;
    private String username;
    private String password;
    private Set<RoleResource> roles;

    private Boolean enabled = false;
    private String sessionTimeout;
    private String accountExpiration;
    private Boolean neverExpire = false;
    private String currentPassword;

    private String address1;
    private String address2;
    private String phoneNo;
    private String mobileNo;
    private String email;
    private String gender;

    private String firstName;
    private String middleName;
    private String lastName;

    private String profileImage;

    private String errProfileImage;

    private String confirmPassword;
    private String errUsername;
    private String errPassword;
    private String errConfirmPassword;
    private String errRole;
    private String errSessionTimeout;
    private String errAccountExpiration;
    private String errCurrentPassword;
    private String errAddress1;
    private String errAddress2;
    private String errPhoneNo;
    private String errMobileNo;
    private String errEmail;
    private String errGender;

    private String errFirstName;
    private String errMiddleName;
    private String errLastName;

    private String[] selectedRoles;

    private String userType;

    private boolean passwordExpire = false;

    private String entryByUsername = "";
    private String entryByUserType = "";
    private String lastUpdateByUsername = "";
    private String lastUpdateByUserType = "";
    private Long lastModifiedUnixTime = 0L;

    private String adminUsername = "";
    private String adminOperatorUsername = "";
    private String adminUserType = "";
    private String adminOperatorUserType = "";
    
    private String syncronizedVersion = "";

    public String getSyncronizedVersion() {
        return syncronizedVersion;
    }

    public void setSyncronizedVersion(String syncronizedVersion) {
        this.syncronizedVersion = syncronizedVersion;
    }
     

    public String getAdminUserType() {
        return adminUserType;
    }

    public void setAdminUserType(String adminUserType) {
        this.adminUserType = adminUserType;
    }

    public String getAdminOperatorUserType() {
        return adminOperatorUserType;
    }

    public void setAdminOperatorUserType(String adminOperatorUserType) {
        this.adminOperatorUserType = adminOperatorUserType;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public String getAdminOperatorUsername() {
        return adminOperatorUsername;
    }

    public void setAdminOperatorUsername(String adminOperatorUsername) {
        this.adminOperatorUsername = adminOperatorUsername;
    }

    public String getEntryByUsername() {
        return entryByUsername;
    }

    public void setEntryByUsername(String entryByUsername) {
        this.entryByUsername = entryByUsername;
    }

    public String getEntryByUserType() {
        return entryByUserType;
    }

    public void setEntryByUserType(String entryByUserType) {
        this.entryByUserType = entryByUserType;
    }

    public String getLastUpdateByUsername() {
        return lastUpdateByUsername;
    }

    public void setLastUpdateByUsername(String lastUpdateByUsername) {
        this.lastUpdateByUsername = lastUpdateByUsername;
    }

    public String getLastUpdateByUserType() {
        return lastUpdateByUserType;
    }

    public void setLastUpdateByUserType(String lastUpdateByUserType) {
        this.lastUpdateByUserType = lastUpdateByUserType;
    }

    public boolean isPasswordExpire() {
        return passwordExpire;
    }

    public void setPasswordExpire(boolean passwordExpire) {
        this.passwordExpire = passwordExpire;
    }

    public Long getLastModifiedUnixTime() {
        return lastModifiedUnixTime;
    }

    public void setLastModifiedUnixTime(Long lastModifiedUnixTime) {
        this.lastModifiedUnixTime = lastModifiedUnixTime;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getErrFirstName() {
        return errFirstName;
    }

    public void setErrFirstName(String errFirstName) {
        this.errFirstName = errFirstName;
    }

    public String getErrMiddleName() {
        return errMiddleName;
    }

    public void setErrMiddleName(String errMiddleName) {
        this.errMiddleName = errMiddleName;
    }

    public String getErrLastName() {
        return errLastName;
    }

    public void setErrLastName(String errLastName) {
        this.errLastName = errLastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getErrGender() {
        return errGender;
    }

    public void setErrGender(String errGender) {
        this.errGender = errGender;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getErrCurrentPassword() {
        return errCurrentPassword;
    }

    public void setErrCurrentPassword(String errCurrentPassword) {
        this.errCurrentPassword = errCurrentPassword;
    }

    public String[] getSelectedRoles() {
        return selectedRoles;
    }

    public void setSelectedRoles(String[] selectedRoles) {
        this.selectedRoles = selectedRoles;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<RoleResource> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleResource> roles) {
        this.roles = roles;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getErrUsername() {
        return errUsername;
    }

    public void setErrUsername(String errUsername) {
        this.errUsername = errUsername;
    }

    public String getErrPassword() {
        return errPassword;
    }

    public void setErrPassword(String errPassword) {
        this.errPassword = errPassword;
    }

    public String getErrConfirmPassword() {
        return errConfirmPassword;
    }

    public void setErrConfirmPassword(String errConfirmPassword) {
        this.errConfirmPassword = errConfirmPassword;
    }

    public String getErrRole() {
        return errRole;
    }

    public void setErrRole(String errRole) {
        this.errRole = errRole;
    }

    public String getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(String sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public String getAccountExpiration() {
        return accountExpiration;
    }

    public void setAccountExpiration(String accountExpiration) {
        this.accountExpiration = accountExpiration;
    }

    public Boolean getNeverExpire() {
        return neverExpire;
    }

    public void setNeverExpire(Boolean neverExpire) {
        this.neverExpire = neverExpire;
    }

    public String getErrSessionTimeout() {
        return errSessionTimeout;
    }

    public void setErrSessionTimeout(String errSessionTimeout) {
        this.errSessionTimeout = errSessionTimeout;
    }

    public String getErrAccountExpiration() {
        return errAccountExpiration;
    }

    public void setErrAccountExpiration(String errAccountExpiration) {
        this.errAccountExpiration = errAccountExpiration;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getErrAddress1() {
        return errAddress1;
    }

    public void setErrAddress1(String errAddress1) {
        this.errAddress1 = errAddress1;
    }

    public String getErrAddress2() {
        return errAddress2;
    }

    public void setErrAddress2(String errAddress2) {
        this.errAddress2 = errAddress2;
    }

    public String getErrPhoneNo() {
        return errPhoneNo;
    }

    public void setErrPhoneNo(String errPhoneNo) {
        this.errPhoneNo = errPhoneNo;
    }

    public String getErrMobileNo() {
        return errMobileNo;
    }

    public void setErrMobileNo(String errMobileNo) {
        this.errMobileNo = errMobileNo;
    }

    public String getErrEmail() {
        return errEmail;
    }

    public void setErrEmail(String errEmail) {
        this.errEmail = errEmail;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getErrProfileImage() {
        return errProfileImage;
    }

    public void setErrProfileImage(String errProfileImage) {
        this.errProfileImage = errProfileImage;
    }

}
