package com.project.model.user;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Samrit
 */
public class DepartmentUserResource extends UserResource {

    private String socialID;
    private String departmentUserField;

    private String errSocialID;
    private String errDepartmentUserField;

    public String getSocialID() {
        return socialID;
    }

    public void setSocialID(String socialID) {
        this.socialID = socialID;
    }

    public String getDepartmentUserField() {
        return departmentUserField;
    }

    public void setDepartmentUserField(String departmentUserField) {
        this.departmentUserField = departmentUserField;
    }

    public String getErrSocialID() {
        return errSocialID;
    }

    public void setErrSocialID(String errSocialID) {
        this.errSocialID = errSocialID;
    }

    public String getErrDepartmentUserField() {
        return errDepartmentUserField;
    }

    public void setErrDepartmentUserField(String errDepartmentUserField) {
        this.errDepartmentUserField = errDepartmentUserField;
    }

}
