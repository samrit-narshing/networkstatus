/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.web.controller;

import com.project.config.PropertiesConfig;
import com.project.dao.user.DepartmentUserService;
import com.project.dao.user.UserService;
import com.project.exception.rest.BadRequestException;
import com.project.exception.rest.InternalServerException;
import com.project.exception.rest.UnAuthorizedException;
import com.project.model.user.DepartmentUserListResource;
import com.project.model.user.DepartmentUserResource;
import com.project.model.user.RoleResource;
import com.project.model.user.RoletListResource;
import com.project.model.user.UserResource;
import com.project.model.util.FileInfo;
import com.project.model.util.UploadedFileContent;
import com.project.util.ApplicationMessageEnum;
import com.project.util.CryptUtil;
import com.project.util.Log4jUtil;
import com.project.util.SHA1;
import com.project.util.Validate;
import com.project.util.searchcriteria.DepartmentUserSearchCriteria;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author SAM
 */
@Controller
@RequestMapping(value = "/departmentuserman")
public class DepartmentUserManagement_CLIENT_MVC_Controller extends Base_Controller {

    final int maxRecordsToFetch = 20;

    @Autowired
    private DepartmentUserService departmentUserService;

    @Autowired
    private UserService userService;

    @Autowired
    @Resource(name = "propertiesConfig")
    private PropertiesConfig propertiesConfig;

    @RequestMapping(value = "/departmentuser/add", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView addDepartmentUserDisplay(final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            Locale locale = new Locale("en", "US");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
            long timeInMillis = Calendar.getInstance().getTimeInMillis();
            String initialDeviceDate = simpleDateFormat.format(timeInMillis);

            DepartmentUserResource departmentUserResource = new DepartmentUserResource();
            departmentUserResource.setAccountExpiration(initialDeviceDate);
            departmentUserResource.setSessionTimeout("28800");
            model.addObject("departmentUserResource", departmentUserResource);

            RoletListResource readRoletListResource = departmentUserService.readRoles();
            List<RoleResource> roleResource = readRoletListResource.getRoles();

            if (roleResource == null || roleResource.isEmpty()) {
                redirectAttributes.addFlashAttribute("pageMessage", "Please Enter Department Roles First.");
                return new ModelAndView("redirect:" + "/result");
            }

            model.addObject("roleResource", roleResource);
            model.setViewName("departmentuser_add");
            return model;
        } catch (UnAuthorizedException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (InternalServerException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (BadRequestException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (Exception e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }

    }

    @RequestMapping(value = "/departmentuser/add/submit", method = {RequestMethod.POST})
    public ModelAndView addDepartmentUserProcess(@ModelAttribute("departmentUserResource") DepartmentUserResource departmentUserResource, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");
            model.setViewName("departmentuser_add");
            model.addObject("departmentUserResource", departmentUserResource);

            String password = "P@ssw0rd";
            departmentUserResource.setPassword(password);
            //sha password encryption
            CryptUtil cryptUtil = new CryptUtil();
            String encryptedPassword = (cryptUtil.asHex(SHA1.SHA1(departmentUserResource.getPassword())));
            boolean isValidated = true;

            Validate validate = new Validate();

            if (validate.isEmptyString(departmentUserResource.getFirstName()) || (!validate.isValidDefaultTextLength(departmentUserResource.getFirstName()))) {
                isValidated = false;
                departmentUserResource.setErrFirstName("*");
            }

            if (!validate.isEmptyString(departmentUserResource.getMiddleName())) {
                if (!validate.isValidDefaultTextLength(departmentUserResource.getMiddleName())) {
                    isValidated = false;
                    departmentUserResource.setErrMiddleName("*");
                }
            }

            if (validate.isEmptyString(departmentUserResource.getLastName()) || (!validate.isValidDefaultTextLength(departmentUserResource.getLastName()))) {
                isValidated = false;
                departmentUserResource.setErrLastName("*");
            }

            if (!validate.isEmptyString(departmentUserResource.getAddress1())) {
                if (!validate.isValidDefaultTextLength(departmentUserResource.getAddress1())) {
                    isValidated = false;
                    departmentUserResource.setErrAddress1("*");
                }
            }

            if (!validate.isEmptyString(departmentUserResource.getAddress2())) {
                if (!validate.isValidDefaultTextLength(departmentUserResource.getAddress2())) {
                    isValidated = false;
                    departmentUserResource.setErrAddress2("*");
                }
            }

//            if (validate.userTMP(departmentUserResource.getUsername())) {
//                if (isUserAlreadyExist(departmentUserResource.getUsername())) {
//                    isValidated = false;
//                    departmentUserResource.setErrUsername("Already exist");
//                }
//            } else {
//                isValidated = false;
//                departmentUserResource.setErrUsername("Incorrect");
//            }
//            if (validate.passwordTMP(departmentUserResource.getConfirmPassword())) {
//                if (departmentUserResource.getPassword().compareTo(departmentUserResource.getConfirmPassword()) != 0) {
//                    isValidated = false;
//                    departmentUserResource.setErrConfirmPassword("Password and confirm password don't match");
//                }
//            } else {
//                isValidated = false;
//                departmentUserResource.setErrConfirmPassword("Incorrect & Length also should be in between 5-10 letters");
//            }
//            if (departmentUserResource.getSelectedRoles() == null || departmentUserResource.getSelectedRoles().length == 0) {
//                departmentUserResource.setSelectedRoles(new String[0]);
//                isValidated = false;
//                departmentUserResource.setErrRole("Role is not selected");
//            } else {
//                for (String role : departmentUserResource.getSelectedRoles()) {
//                    if (role.equalsIgnoreCase("7")) {
//                        isValidated = true;
//                        departmentUserResource.setErrRole("");
//                        break;
//                    } else {
//                        isValidated = false;
//                        departmentUserResource.setErrRole("Please select DepartmentUser Role too.");
//                    }
//                }
//            }
            if (departmentUserResource.getSelectedRoles() == null) {
                departmentUserResource.setSelectedRoles(new String[0]);
            }

            if (!(validate.isValidRADIUS_Timeout(departmentUserResource.getSessionTimeout()))) {
                isValidated = false;
                departmentUserResource.setErrSessionTimeout("Incorrect");
            }
            if (!(validate.isValidCalendarDate(departmentUserResource.getAccountExpiration()))) {
                isValidated = false;
                departmentUserResource.setErrAccountExpiration("Incorrect");
            }

            if (departmentUserResource.getEmail().trim().equals("") || !(validate.isValidEmail(departmentUserResource.getEmail())) || (!validate.isValidDefaultTextLength(departmentUserResource.getEmail()))) {
                isValidated = false;
                departmentUserResource.setErrEmail("Incorrect");
            }

            if (!departmentUserResource.getPhoneNo().equals("") && !(validate.isValidPhoneNumber(departmentUserResource.getPhoneNo()))) {
                isValidated = false;
                departmentUserResource.setErrPhoneNo("Incorrect");
            }

            if (!departmentUserResource.getMobileNo().equals("") && !(validate.isValidPhoneNumber(departmentUserResource.getMobileNo()))) {
                isValidated = false;
                departmentUserResource.setErrMobileNo("Incorrect");
            }

            if (validate.isEmptyString(departmentUserResource.getSocialID()) || (!validate.isValidDefaultTextLength(departmentUserResource.getSocialID()))) {
                isValidated = false;
                departmentUserResource.setErrSocialID("Incorrect");
            }

            if (validate.isEmptyString(departmentUserResource.getDepartmentUserField()) || (!validate.isValidDefaultTextLength(departmentUserResource.getDepartmentUserField()))) {
                isValidated = false;
                departmentUserResource.setErrDepartmentUserField("Incorrect");
            }

            if (departmentUserResource.getSelectedRoles() == null || departmentUserResource.getSelectedRoles().length == 0) {
                departmentUserResource.setSelectedRoles(new String[0]);
                isValidated = false;
                departmentUserResource.setErrRole("Role is not selected");
            }

            if (isValidated) {
                departmentUserResource.setPassword(encryptedPassword);

                Set<RoleResource> roleResources = new HashSet<RoleResource>();
                for (String r : departmentUserResource.getSelectedRoles()) {
                    roleResources.add(departmentUserService.readRolesByID(Long.parseLong(r)));
                }

                departmentUserResource.setRoles(roleResources);
                DepartmentUserResource readUserResource = departmentUserService.createDepartmentUser(departmentUserResource);
                readUserResource.setPassword(password);
                model.addObject("departmentUserResource", readUserResource);
                model.setViewName("departmentuser_add_success");
                return model;
//                ParentSearchCriteria searchCriteria = new ParentSearchCriteria();
//                searchCriteria.setId(readUserResource.getUserID());
//                searchCriteria.setStatusCode(1); // 1 for inserted
//                redirectAttributes.addFlashAttribute("searchCriteria", searchCriteria);
//                redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_ADD_RECORD.getMessage());
//                return new ModelAndView("redirect:" + "/parentman/parent/search");

            }

            //refilling default values
            RoletListResource roletListResource = departmentUserService.readRoles();
            List<RoleResource> roleResource = roletListResource.getRoles();
            for (RoleResource role : roleResource) {
                for (String selectedRole : departmentUserResource.getSelectedRoles()) {
                    if (Long.valueOf(selectedRole).equals(role.getRoleID())) {
                        role.setSelected(true);
                        break;
                    }
                }
            }
            model.addObject("roleResource", roleResource);

            return model;
        } catch (UnAuthorizedException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (InternalServerException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (BadRequestException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (Exception e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

    @RequestMapping(value = "/departmentuser/search", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView searchDepartmentUserDisplay(@ModelAttribute("searchCriteria") DepartmentUserSearchCriteria searchCriteria, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            searchCriteria.setLimitResult(maxRecordsToFetch);

            DepartmentUserListResource departmentUserListResource = departmentUserService.readDepartmentUsersBySearchCriteria(searchCriteria);
            model.addObject("departmentUserListResource", departmentUserListResource);
            if (searchCriteria.getStatusCode() == null) {

            } else if (searchCriteria.getStatusCode() == 1) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_ADD_RECORD.getMessage());
            } else if (searchCriteria.getStatusCode() == 2) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
            } else if (searchCriteria.getStatusCode() == 3) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
            }

            model.addObject("searchCriteria", searchCriteria);

            model.setViewName("departmentuser_list");
            return model;
        } catch (UnAuthorizedException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (InternalServerException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (BadRequestException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (Exception e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

    @RequestMapping(value = "/departmentuser/delete/submit", method = {RequestMethod.POST})
    public ModelAndView deleteDepartmentUserProcess(@RequestParam(value = "id", required = true) Long id, final RedirectAttributes redirectAttributes) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            DepartmentUserResource readDepartmentUserResource = departmentUserService.deleteDepartmentUserByID(id);

            DepartmentUserSearchCriteria searchCriteria = new DepartmentUserSearchCriteria();
            searchCriteria.setId(0L);//for search all
            searchCriteria.setStatusCode(3); // 3 for deleted
            redirectAttributes.addFlashAttribute("searchCriteria", searchCriteria);
            redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
            return new ModelAndView("redirect:" + "/departmentuserman/departmentuser/search");

        } catch (UnAuthorizedException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (InternalServerException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (BadRequestException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (Exception e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

    @RequestMapping(value = "/departmentuser/edit", method = {RequestMethod.POST})
    public ModelAndView editDepartmentUserDisplay(@RequestParam(value = "id", required = true) Long id, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            DepartmentUserResource departmentUserResource = departmentUserService.readDepartmentUserByID(id);
            model.addObject("departmentUserResource", departmentUserResource);

            //refilling default values
            RoletListResource roletListResource = departmentUserService.readRoles();
            List<RoleResource> roleResource = roletListResource.getRoles();
            for (RoleResource role : roleResource) {
                for (RoleResource userRole : departmentUserResource.getRoles()) {
                    if (role.getRoleID().equals(userRole.getRoleID())) {
                        role.setSelected(true);
                        break;
                    }
                }
            }
            model.addObject("roleResource", roleResource);

            if (roleResource == null || roleResource.isEmpty()) {
                redirectAttributes.addFlashAttribute("pageMessage", "Please Enter Department Roles First.");
                return new ModelAndView("redirect:" + "/result");
            }

            if (departmentUserResource.getAccountExpiration().equalsIgnoreCase("Never Expires")) {
                Locale locale = new Locale("en", "US");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
                long timeInMillis = Calendar.getInstance().getTimeInMillis();
                String initialDeviceDate = simpleDateFormat.format(timeInMillis);
                departmentUserResource.setAccountExpiration(initialDeviceDate);
                departmentUserResource.setNeverExpire(true);
            }

            model.setViewName("departmentuser_edit");
            return model;
        } catch (UnAuthorizedException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (InternalServerException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (BadRequestException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (Exception e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

    @RequestMapping(value = "/departmentuser/edit/submit", method = {RequestMethod.POST})
    public ModelAndView editDepartmentUserProcess(@RequestParam(value = "id", required = true) Long id, @ModelAttribute("departmentUserResource") DepartmentUserResource departmentUserResource, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            DepartmentUserResource readParentResource = departmentUserService.readDepartmentUserByID(id);
            departmentUserResource.setUserID(readParentResource.getUserID());

            boolean isValidated = true;
            Validate validate = new Validate();

            if (validate.isEmptyString(departmentUserResource.getFirstName()) || (!validate.isValidDefaultTextLength(departmentUserResource.getFirstName()))) {
                isValidated = false;
                departmentUserResource.setErrFirstName("*");
            }

            if (!validate.isEmptyString(departmentUserResource.getMiddleName())) {
                if (!validate.isValidDefaultTextLength(departmentUserResource.getMiddleName())) {
                    isValidated = false;
                    departmentUserResource.setErrMiddleName("*");
                }
            }

            if (validate.isEmptyString(departmentUserResource.getLastName()) || (!validate.isValidDefaultTextLength(departmentUserResource.getLastName()))) {
                isValidated = false;
                departmentUserResource.setErrLastName("*");
            }

            if (!validate.isEmptyString(departmentUserResource.getAddress1())) {
                if (!validate.isValidDefaultTextLength(departmentUserResource.getAddress1())) {
                    isValidated = false;
                    departmentUserResource.setErrAddress1("*");
                }
            }

            if (!validate.isEmptyString(departmentUserResource.getAddress2())) {
                if (!validate.isValidDefaultTextLength(departmentUserResource.getAddress2())) {
                    isValidated = false;
                    departmentUserResource.setErrAddress2("*");
                }
            }

//            if (departmentUserResource.getSelectedRoles() == null || departmentUserResource.getSelectedRoles().length == 0) {
//                departmentUserResource.setSelectedRoles(new String[0]);
//                isValidated = false;
//                departmentUserResource.setErrRole("Role is not selected");
//            } else {
//                for (String role : departmentUserResource.getSelectedRoles()) {
//                    if (role.equalsIgnoreCase("7")) {
//                        isValidated = true;
//                        departmentUserResource.setErrRole("");
//                        break;
//                    } else {
//                        isValidated = false;
//                        departmentUserResource.setErrRole("Please select DepartmentUser Role too.");
//                    }
//                }
//            }
            if (departmentUserResource.getSelectedRoles() == null) {
                departmentUserResource.setSelectedRoles(new String[0]);
            }

            if (!(validate.isValidRADIUS_Timeout(departmentUserResource.getSessionTimeout()))) {
                isValidated = false;
                departmentUserResource.setErrSessionTimeout("Incorrect");
            }
            if (!(validate.isValidCalendarDate(departmentUserResource.getAccountExpiration()))) {
                isValidated = false;
                departmentUserResource.setErrAccountExpiration("Incorrect");
            }

            if (departmentUserResource.getEmail().trim().equals("") || !(validate.isValidEmail(departmentUserResource.getEmail())) || (!validate.isValidDefaultTextLength(departmentUserResource.getEmail()))) {
                isValidated = false;
                departmentUserResource.setErrEmail("Incorrect");
            }

            if (!departmentUserResource.getPhoneNo().equals("") && !(validate.isValidPhoneNumber(departmentUserResource.getPhoneNo()))) {
                isValidated = false;
                departmentUserResource.setErrPhoneNo("Incorrect");
            }

            if (!departmentUserResource.getMobileNo().equals("") && !(validate.isValidPhoneNumber(departmentUserResource.getMobileNo()))) {
                isValidated = false;
                departmentUserResource.setErrMobileNo("Incorrect");
            }

            if (validate.isEmptyString(departmentUserResource.getSocialID()) || (!validate.isValidDefaultTextLength(departmentUserResource.getSocialID()))) {
                isValidated = false;
                departmentUserResource.setErrSocialID("Incorrect");
            }

            if (validate.isEmptyString(departmentUserResource.getDepartmentUserField()) || (!validate.isValidDefaultTextLength(departmentUserResource.getDepartmentUserField()))) {
                isValidated = false;
                departmentUserResource.setErrDepartmentUserField("Incorrect");
            }

            if (departmentUserResource.getSelectedRoles() == null || departmentUserResource.getSelectedRoles().length == 0) {
                departmentUserResource.setSelectedRoles(new String[0]);
                isValidated = false;
                departmentUserResource.setErrRole("Role is not selected");
            }

            if (isValidated) {

//                if (userResource.getNeverExpire()) {
//                    userResource.setAccountExpiration("Never Expires");
//                }
                Set<RoleResource> roleResources = new HashSet<RoleResource>();
                for (String r : departmentUserResource.getSelectedRoles()) {
                    roleResources.add(departmentUserService.readRolesByID(Long.parseLong(r)));
                }

                departmentUserResource.setRoles(roleResources);

                //
                DepartmentUserResource readUpdatedProfessorResource = departmentUserService.updateDepartmentUserDetails(id, departmentUserResource);
                DepartmentUserSearchCriteria searchCriteria = new DepartmentUserSearchCriteria();
                searchCriteria.setId(readUpdatedProfessorResource.getUserID());
                searchCriteria.setStatusCode(2); // 1 for inserted
                redirectAttributes.addFlashAttribute("searchCriteria", searchCriteria);
                redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
                return new ModelAndView("redirect:" + "/departmentuserman/departmentuser/search");

            }

            //refilling default values
            RoletListResource roletListResource = departmentUserService.readRoles();
            List<RoleResource> roleResource = roletListResource.getRoles();
            for (RoleResource role : roleResource) {
                for (String selectedRole : departmentUserResource.getSelectedRoles()) {
                    if (Long.valueOf(selectedRole).equals(role.getRoleID())) {
                        role.setSelected(true);
                        break;
                    }
                }
            }
            model.addObject("departmentUserResource", departmentUserResource);
            model.addObject("roleResource", roleResource);
            model.setViewName("departmentuser_edit");
            return model;
        } catch (UnAuthorizedException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (InternalServerException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (BadRequestException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (Exception e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

    @RequestMapping(value = "/departmentuser/admin/change_password", method = {RequestMethod.POST})
    public ModelAndView changePasswordByAdminUserDisplay(@RequestParam(value = "id", required = true) Long id, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            DepartmentUserResource departmentUserResource = departmentUserService.readDepartmentUserByID(id);
            model.addObject("departmentUserResource", departmentUserResource);

            model.setViewName("departmentuser_change_password_by_admin");
            return model;
        } catch (UnAuthorizedException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (InternalServerException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (BadRequestException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (Exception e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

    @RequestMapping(value = "/departmentuser/admin/change_password/submit", method = {RequestMethod.POST})
    public ModelAndView changePasswordByAdminUserProcess(@RequestParam(value = "id", required = true) Long id, @ModelAttribute("departmentUserResource") DepartmentUserResource departmentUserResource, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            DepartmentUserResource readDepartmentUserResource = departmentUserService.readDepartmentUserByID(id);
            departmentUserResource.setUserID(readDepartmentUserResource.getUserID());

            boolean isValidated = true;
            Validate validate = new Validate();

            String encryptedPassword = "";

            //sha password encryption
            CryptUtil cryptUtil = new CryptUtil();
            encryptedPassword = (cryptUtil.asHex(SHA1.SHA1(departmentUserResource.getPassword())));

            if (validate.passwordTMP(departmentUserResource.getConfirmPassword())) {
                if (departmentUserResource.getPassword().compareTo(departmentUserResource.getConfirmPassword()) != 0) {
                    isValidated = false;
                    departmentUserResource.setErrConfirmPassword("Password and confirm password don't match");
                }
            } else {
                isValidated = false;
                departmentUserResource.setErrConfirmPassword("Incorrect & Length also should be in between 5-10 letters");
            }

            if (isValidated) {
                departmentUserResource.setPassword(encryptedPassword);
                DepartmentUserResource readUpdatedDepartmentUserResource = departmentUserService.updateDepartmentUserPassword(id, departmentUserResource);
                DepartmentUserSearchCriteria searchCriteria = new DepartmentUserSearchCriteria();
                searchCriteria.setId(readUpdatedDepartmentUserResource.getUserID());
                searchCriteria.setStatusCode(2); // 1 for inserted
                redirectAttributes.addFlashAttribute("searchCriteria", searchCriteria);
                redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
                return new ModelAndView("redirect:" + "/departmentuserman/departmentuser/search");

            }

            model.addObject("departmentUserResource", departmentUserResource);
            model.setViewName("departmentuser_change_password_by_admin");
            return model;
        } catch (UnAuthorizedException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (InternalServerException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (BadRequestException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (Exception e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

    private boolean isUserAlreadyExist(String userName) throws Exception {
        UserResource userResource = userService.readUserByUsername(userName);
        return userResource != null;
    }

    @RequestMapping(value = "/departmentuser/add/image/upload/submit", method = {RequestMethod.POST})
    public ModelAndView uploadStudentProfileImageInStudentAddProcess(@RequestParam("file") MultipartFile file, @RequestParam(value = "id", required = true) Long id, @RequestParam(value = "newPassword", required = true) String newPassword, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            DepartmentUserResource departmentUserResource = departmentUserService.readDepartmentUserByID(id);

            if (file.isEmpty()) {
                model.addObject("pageError", ApplicationMessageEnum.ERROR_EMPTY_PROFILE_PICTURE_UPLOAD.getMessage());
                departmentUserResource.setPassword(newPassword);
                departmentUserResource.setErrProfileImage("Please Select Valid File.");
//                model.addObject("userResource", userResource);
//                model.setViewName("user_add_success");
//                return model;
            } else {
                final List<String> contentTypes = Arrays.asList("image/png", "image/jpeg");
                String fileContentType = file.getContentType();
                if (contentTypes.contains(fileContentType)) {
                    // You have the correct extension
                    // rest of your code here

                    // Get the file and save it somewhere
                    byte[] bytes = file.getBytes();

                    String extension = FilenameUtils.getExtension(file.getOriginalFilename()); // returns "txt"

                    UploadedFileContent uploadedFileContent = new UploadedFileContent();
                    uploadedFileContent.setBytes(bytes);
                    uploadedFileContent.setFileSize(file.getSize());
                    uploadedFileContent.setFilename(file.getOriginalFilename());
                    uploadedFileContent.setNewFilename(departmentUserResource.getUsername() + "." + extension);
                    uploadedFileContent.setSourceId(departmentUserResource.getUserID());

                    FileInfo fileInfo = userService.uploadUserProfileImageByObject(uploadedFileContent);

                    if (fileInfo != null) {
                        departmentUserResource.setErrProfileImage("");
                        model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_PROFILE_PICTURE_UPLOAD.getMessage());
                    } else {
                        departmentUserResource.setErrProfileImage(ApplicationMessageEnum.ERROR_FAILED_PROFILE_PICTURE_UPLOAD.getMessage());
                        model.addObject("pageError", ApplicationMessageEnum.ERROR_FAILED_PROFILE_PICTURE_UPLOAD.getMessage());
                    }

                } else {
                    departmentUserResource.setErrProfileImage(ApplicationMessageEnum.ERROR_INVALID_PROFILE_PICTURE_UPLOAD.getMessage());
                    model.addObject("pageError", ApplicationMessageEnum.ERROR_INVALID_PROFILE_PICTURE_UPLOAD.getMessage());
                    // Handle error of not correct extension
                }
            }
            departmentUserResource.setPassword(newPassword);
            model.addObject("departmentUserResource", departmentUserResource);
            model.setViewName("departmentuser_add_success");
            return model;
        } catch (UnAuthorizedException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (InternalServerException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (BadRequestException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (Exception e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

    @RequestMapping(value = "/departmentuser/edit/image/upload/submit", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView uploadDepartmentUserProfileImageInUserDepartmentUserProcess(@RequestParam("file") MultipartFile file, @RequestParam(value = "id", required = true) Long id,
            RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            DepartmentUserResource departmentUserResource = departmentUserService.readDepartmentUserByID(id);

            if (file.isEmpty()) {
                model.addObject("pageError", ApplicationMessageEnum.ERROR_EMPTY_PROFILE_PICTURE_UPLOAD.getMessage());
                departmentUserResource.setErrProfileImage("Please Select Valid File.");
            } else {
                final List<String> contentTypes = Arrays.asList("image/png", "image/jpeg");
                String fileContentType = file.getContentType();
                if (contentTypes.contains(fileContentType)) {
                    // You have the correct extension
                    // rest of your code here

                    // Get the file and save it somewhere
                    byte[] bytes = file.getBytes();

                    String extension = FilenameUtils.getExtension(file.getOriginalFilename()); // returns "txt"

                    UploadedFileContent uploadedFileContent = new UploadedFileContent();
                    uploadedFileContent.setBytes(bytes);
                    uploadedFileContent.setFileSize(file.getSize());
                    uploadedFileContent.setFilename(file.getOriginalFilename());
                    uploadedFileContent.setNewFilename(departmentUserResource.getUsername() + "." + extension);
                    uploadedFileContent.setSourceId(departmentUserResource.getUserID());

                    FileInfo fileInfo = userService.uploadUserProfileImageByObject(uploadedFileContent);

                    if (fileInfo != null) {
                        departmentUserResource.setErrProfileImage("");
                        model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_PROFILE_PICTURE_UPLOAD.getMessage());
                    } else {
                        departmentUserResource.setErrProfileImage(ApplicationMessageEnum.ERROR_FAILED_PROFILE_PICTURE_UPLOAD.getMessage());
                        model.addObject("pageError", ApplicationMessageEnum.ERROR_FAILED_PROFILE_PICTURE_UPLOAD.getMessage());
                    }

                } else {
                    departmentUserResource.setErrProfileImage(ApplicationMessageEnum.ERROR_INVALID_PROFILE_PICTURE_UPLOAD.getMessage());
                    model.addObject("pageError", ApplicationMessageEnum.ERROR_INVALID_PROFILE_PICTURE_UPLOAD.getMessage());
                    // Handle error of not correct extension
                }
            }

            model.addObject("departmentUserResource", departmentUserResource);

            //refilling default values
            RoletListResource roletListResource = departmentUserService.readRoles();
            List<RoleResource> roleResource = roletListResource.getRoles();
            for (RoleResource role : roleResource) {
                for (RoleResource userRole : departmentUserResource.getRoles()) {
                    if (role.getRoleID().equals(userRole.getRoleID())) {
                        role.setSelected(true);
                        break;
                    }
                }
            }
            model.addObject("roleResource", roleResource);

            if (departmentUserResource.getAccountExpiration().equalsIgnoreCase("Never Expires")) {
                Locale locale = new Locale("en", "US");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
                long timeInMillis = Calendar.getInstance().getTimeInMillis();
                String initialDeviceDate = simpleDateFormat.format(timeInMillis);
                departmentUserResource.setAccountExpiration(initialDeviceDate);
                departmentUserResource.setNeverExpire(true);
            }

            model.setViewName("departmentuser_edit");
            return model;
        } catch (IOException | InternalServerException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (UnAuthorizedException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (BadRequestException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (Exception e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

    @RequestMapping(value = "/departmentuser/changeenablestatus/submit", method = {RequestMethod.POST})
    public ModelAndView changeEnableStatusOfProfessorProcess(@RequestParam(value = "id", required = true) Long id, final RedirectAttributes redirectAttributes) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            DepartmentUserResource departmentUserResource = departmentUserService.updateDepartmentUserEnabledStatus(id);

            DepartmentUserSearchCriteria searchCriteria = new DepartmentUserSearchCriteria();
            searchCriteria.setId(departmentUserResource.getUserID());//for search all
//            searchCriteria.setStatusCode(2); // 2 for Updated
            redirectAttributes.addFlashAttribute("searchCriteria", searchCriteria);
            redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
            return new ModelAndView("redirect:" + "/departmentuserman/departmentuser/search");

        } catch (UnAuthorizedException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (InternalServerException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (BadRequestException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (Exception e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

}
