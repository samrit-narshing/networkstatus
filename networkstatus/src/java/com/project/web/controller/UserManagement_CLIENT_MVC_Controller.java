/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.web.controller;

import com.project.config.PropertiesConfig;
import com.project.dao.user.DeviceUserService;
import com.project.dao.user.UserService;
import com.project.exception.rest.BadRequestException;
import com.project.exception.rest.InternalServerException;
import com.project.exception.rest.UnAuthorizedException;
import com.project.model.user.RoleResource;
import com.project.model.user.RoletListResource;
import com.project.model.user.UserResource;
import com.project.model.user.UsertListResource;
import com.project.model.util.EmailData;
import com.project.model.util.FileInfo;
import com.project.model.util.UploadedFileContent;
import com.project.util.ApplicationMessageEnum;
import com.project.util.CryptUtil;
import com.project.util.Log4jUtil;
import com.project.util.SHA1;
import com.project.util.Validate;
import com.project.util.searchcriteria.UserSearchCriteria;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
@RequestMapping(value = "/userman")
public class UserManagement_CLIENT_MVC_Controller extends Base_Controller {

    final int maxRecordsToFetch = 20;
    final int maxRecordsToFetchForDeviceUser = 5;

    @Autowired
    private UserService userService;

    @Autowired
    private DeviceUserService deviceUserService;

    @Autowired
    @Resource(name = "propertiesConfig")
    private PropertiesConfig propertiesConfig;

    @RequestMapping(value = "/user/add", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView addUserDisplay(HttpServletRequest request, HttpServletResponse response, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            Locale locale = new Locale("en", "US");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
            long timeInMillis = Calendar.getInstance().getTimeInMillis();
            String initialDeviceDate = simpleDateFormat.format(timeInMillis);

            UserResource userResource = new UserResource();
            userResource.setAccountExpiration(initialDeviceDate);
            userResource.setSessionTimeout("28800");
            model.addObject("userResource", userResource);

            RoletListResource readRoletListResource = userService.readRoles();
            List<RoleResource> roleResource = readRoletListResource.getRoles();
            model.addObject("roleResource", roleResource);

//            javax.swing.JOptionPane.showMessageDialog(null, "SDDSDSD");
//        // Calling GC To Freeup HeapSize
////        controlPanelConfigService.callGarbageCollector();
////        ModelAndView model = new ModelAndView();
////        model.addObject("title", "Spring Security - Authenticated ");
////        model.addObject("message", "Logged On!");
//
//        List<EmailData> barModels = new ArrayList<>();
//
//        for (int i = 0; i < 20; i++) {
//            EmailData emailData = new EmailData();
//            emailData.setId(new Integer(i).longValue());
//            emailData.setMessageBody("message" + i);
//            emailData.setPassword("password" + i);
//            barModels.add(emailData);
//        }
//
////        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(new ReportBeanFactory().getSyslogTimeSeriesModelDataSourceList(barModels));
//        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(barModels);
//        String reportType = "pdf";
//
////        if (eventSummaryReportSearch.getReportType() != null && eventSummaryReportSearch.getReportType().equalsIgnoreCase("rtf")) {
////            reportType = "doc";
////        }
//        Map parameters = new HashMap();
//
//        String reportsDirPath = request.getRealPath("/WEB-INF/");
//        reportsDirPath = reportsDirPath + "/reports//";
//        parameters.put("SUBREPORT_DIR", reportsDirPath);
//        System.out.println(reportsDirPath);
////            parameters.put("REPORT_TITLE", reportTitle);
////            parameters.put("REPORT_FOOTER_1", reportFooter1);
////            parameters.put("REPORT_FOOTER_2", reportFooter2);
////            parameters.put("SUBREPORT_CHART_TITLE", subReportChartTitle);
////            parameters.put("REPORT_GROUP_NAME", reportGroupName);
////            parameters.put("REPORT_GROUP_COUNT", reportGrountCount);
//
//        String reportTypeMain = "";
//        if (reportType.equals("pdf")) {
//            reportTypeMain = "application/pdf";
//        } else if (reportType.equals("xls")) {
//            reportTypeMain = "application/vnd.ms-excel";
//        } else if (reportType.equals("doc")) {
//            reportTypeMain = "application/rtf";
//        }
//
//        new JasperReportModule().jasperReport("report1", reportTypeMain, ds, parameters, request, response, "TimeSeries");
            model.setViewName("user_add");
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

    @RequestMapping(value = "/user/add/submit", method = {RequestMethod.POST})
    public ModelAndView addUserProcess(@ModelAttribute("userResource") UserResource userResource, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");
            model.setViewName("user_add");
            model.addObject("userResource", userResource);

            String encryptedPassword = "";

            //sha password encryption
            CryptUtil cryptUtil = new CryptUtil();
            encryptedPassword = (cryptUtil.asHex(SHA1.SHA1(userResource.getPassword())));

            boolean isValidated = true;

            Validate validate = new Validate();

            if (validate.isEmptyString(userResource.getFirstName()) || (!validate.isValidDefaultTextLength(userResource.getFirstName()))) {
                isValidated = false;
                userResource.setErrFirstName("*");
            }
            if (!validate.isEmptyString(userResource.getMiddleName())) {
                if (!validate.isValidDefaultTextLength(userResource.getMiddleName())) {
                    isValidated = false;
                    userResource.setErrMiddleName("*");
                }
            }
            if (validate.isEmptyString(userResource.getLastName()) || (!validate.isValidDefaultTextLength(userResource.getLastName()))) {
                isValidated = false;
                userResource.setErrLastName("*");
            }

            if (!validate.isEmptyString(userResource.getAddress1())) {
                if (!validate.isValidDefaultTextLength(userResource.getAddress1())) {
                    isValidated = false;
                    userResource.setErrAddress1("*");
                }
            }

            if (!validate.isEmptyString(userResource.getAddress2())) {
                if (!validate.isValidDefaultTextLength(userResource.getAddress2())) {
                    isValidated = false;
                    userResource.setErrAddress2("*");
                }
            }

            if (validate.userTMP(userResource.getUsername())) {
                if (isUserAlreadyExist(userResource.getUsername())) {
                    isValidated = false;
                    userResource.setErrUsername("Already exist");
                }
                if (userResource.getUsername().trim().startsWith("pr-") || userResource.getUsername().trim().startsWith("st-") || userResource.getUsername().trim().startsWith("tc-") || userResource.getUsername().trim().startsWith("mt-") || userResource.getUsername().trim().startsWith("scm-") || userResource.getUsername().trim().startsWith("scop-") || userResource.getUsername().trim().startsWith("trm-") || userResource.getUsername().trim().startsWith("tr-") || userResource.getUsername().trim().startsWith("trg-")) {
                    isValidated = false;
                    userResource.setErrUsername("Invalid Username.(Reserved Username)");
                }
            } else {
                isValidated = false;
                userResource.setErrUsername("Incorrect");
            }

            if (validate.passwordTMP(userResource.getConfirmPassword())) {
                if (userResource.getPassword().compareTo(userResource.getConfirmPassword()) != 0) {
                    isValidated = false;
                    userResource.setErrConfirmPassword("Password and confirm password don't match");
                }
            } else {
                isValidated = false;
                userResource.setErrConfirmPassword("Incorrect & Length also should be in between 5-10 letters");
            }

            if (userResource.getSelectedRoles() == null || userResource.getSelectedRoles().length == 0) {
                userResource.setSelectedRoles(new String[0]);
                isValidated = false;
                userResource.setErrRole("Role is not selected");
            } else {

                boolean containsValidRole = false;
                for (String role : userResource.getSelectedRoles()) {
                    if (role.equalsIgnoreCase("1") || role.equalsIgnoreCase("2")) {
                        containsValidRole = true;
                        break;
                    }
                }

                if (!containsValidRole) {
                    isValidated = false;
                    userResource.setErrRole("Please select Admin or User Role too.");
                } else {
                    userResource.setErrRole("");
                }

//                for (String role : userResource.getSelectedRoles()) {
//                    if (role.equalsIgnoreCase("1") || role.equalsIgnoreCase("2")) {
////                        isValidated = true;
//                        userResource.setErrRole("");
//                        break;
//                    } else {
//                        isValidated = false;
//                        userResource.setErrRole("Please select Admin or User Role too.");
//                    }
//                }
            }

            if (!(validate.isValidRADIUS_Timeout(userResource.getSessionTimeout()))) {
                isValidated = false;
                userResource.setErrSessionTimeout("Incorrect");
            }
            if (!(validate.isValidCalendarDate(userResource.getAccountExpiration()))) {
                isValidated = false;
                userResource.setErrAccountExpiration("Incorrect");
            }

            if (userResource.getEmail().trim().equals("") || !(validate.isValidEmail(userResource.getEmail())) || (!validate.isValidDefaultTextLength(userResource.getEmail()))) {
                isValidated = false;
                userResource.setErrEmail("Incorrect");
            }
            if (!userResource.getPhoneNo().equals("") && !(validate.isValidPhoneNumber(userResource.getPhoneNo()))) {
                isValidated = false;
                userResource.setErrPhoneNo("Incorrect");
            }

            if (!userResource.getMobileNo().equals("") && !(validate.isValidPhoneNumber(userResource.getMobileNo()))) {
                isValidated = false;
                userResource.setErrMobileNo("Incorrect");
            }

            if (isValidated) {
                userResource.setPassword(encryptedPassword);

//                if (userResource.getNeverExpire()) {
//                    userResource.setAccountExpiration("Never Expires");
//                }
                Set<RoleResource> roleResources = new HashSet<RoleResource>();
                for (String r : userResource.getSelectedRoles()) {
                    roleResources.add(userService.readRolesByID(Long.parseLong(r)));
                }

                userResource.setRoles(roleResources);

                UserResource readUserResource = userService.createUser(userResource);

                readUserResource.setPassword("***********");
                model.addObject("userResource", readUserResource);
                model.setViewName("user_add_success");
                return model;

//                UserSearchCriteria searchCriteria = new UserSearchCriteria();
//                searchCriteria.setId(readUserResource.getUserID());
//                searchCriteria.setStatusCode(1); // 1 for inserted
//                redirectAttributes.addFlashAttribute("searchCriteria", searchCriteria);
//                redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_ADD_RECORD.getMessage());
//                return new ModelAndView("redirect:" + "/userman/user/search");
            }

            //refilling default values
            RoletListResource roletListResource = userService.readRoles();
            List<RoleResource> roleResource = roletListResource.getRoles();
            for (RoleResource role : roleResource) {
                for (String selectedRole : userResource.getSelectedRoles()) {
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

    @RequestMapping(value = "/user/search", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView searchUserDisplay(@ModelAttribute("searchCriteria") UserSearchCriteria searchCriteria, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            searchCriteria.setLimitResult(maxRecordsToFetch);

            UsertListResource readUsertListResource = userService.readUsersBySearchCriteria(searchCriteria);
            model.addObject("usertListResource", readUsertListResource);
            if (searchCriteria.getStatusCode() == null) {

            } else if (searchCriteria.getStatusCode() == 1) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_ADD_RECORD.getMessage());
            } else if (searchCriteria.getStatusCode() == 2) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
            } else if (searchCriteria.getStatusCode() == 3) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
            }

            model.addObject("searchCriteria", searchCriteria);

            model.setViewName("user_list");
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

    @RequestMapping(value = "/user/delete/submit", method = {RequestMethod.POST})
    public ModelAndView deleteUserProcess(@RequestParam(value = "id", required = true) Long id, final RedirectAttributes redirectAttributes) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            UserResource readUserResource = userService.deleteUserByID(id);

            UserSearchCriteria searchCriteria = new UserSearchCriteria();
            searchCriteria.setId(0L);//for search all
            searchCriteria.setStatusCode(3); // 3 for deleted
            redirectAttributes.addFlashAttribute("searchCriteria", searchCriteria);
            redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
            return new ModelAndView("redirect:" + "/userman/user/search");

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

    @RequestMapping(value = "/user/editUser", method = {RequestMethod.POST})
    public ModelAndView editUserDisplay(@RequestParam(value = "id", required = true) Long id, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            UserResource userResource = userService.readUserByID(id);
            model.addObject("userResource", userResource);

            //refilling default values
            RoletListResource roletListResource = userService.readRoles();
            List<RoleResource> roleResource = roletListResource.getRoles();
            for (RoleResource role : roleResource) {
                for (RoleResource userRole : userResource.getRoles()) {
                    if (role.getRoleID().equals(userRole.getRoleID())) {
                        role.setSelected(true);
                        break;
                    }
                }
            }
            model.addObject("roleResource", roleResource);

            if (userResource.getAccountExpiration().equalsIgnoreCase("Never Expires")) {
                Locale locale = new Locale("en", "US");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
                long timeInMillis = Calendar.getInstance().getTimeInMillis();
                String initialDeviceDate = simpleDateFormat.format(timeInMillis);
                userResource.setAccountExpiration(initialDeviceDate);
                userResource.setNeverExpire(true);
            }

            model.setViewName("user_edit");
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

    @RequestMapping(value = "/user/edit/submit", method = {RequestMethod.POST})
    public ModelAndView editUserProcess(@RequestParam(value = "id", required = true) Long id, @ModelAttribute("userResource") UserResource userResource, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            UserResource readUserResource = userService.readUserByID(id);
            userResource.setUserID(readUserResource.getUserID());

            boolean isValidated = true;
            Validate validate = new Validate();

            if (validate.isEmptyString(userResource.getFirstName()) || (!validate.isValidDefaultTextLength(userResource.getFirstName()))) {
                isValidated = false;
                userResource.setErrFirstName("*");
            }

            if (!validate.isEmptyString(userResource.getMiddleName())) {
                if (!validate.isValidDefaultTextLength(userResource.getMiddleName())) {
                    isValidated = false;
                    userResource.setErrMiddleName("*");
                }
            }

            if (validate.isEmptyString(userResource.getLastName()) || (!validate.isValidDefaultTextLength(userResource.getLastName()))) {
                isValidated = false;
                userResource.setErrLastName("*");
            }

            if (!validate.isEmptyString(userResource.getAddress1())) {
                if (!validate.isValidDefaultTextLength(userResource.getAddress1())) {
                    isValidated = false;
                    userResource.setErrAddress1("*");
                }
            }

            if (!validate.isEmptyString(userResource.getAddress2())) {
                if (!validate.isValidDefaultTextLength(userResource.getAddress2())) {
                    isValidated = false;
                    userResource.setErrAddress2("*");
                }
            }

            if (userResource.getSelectedRoles() == null || userResource.getSelectedRoles().length == 0) {
                userResource.setSelectedRoles(new String[0]);
                isValidated = false;
                userResource.setErrRole("Role is not selected");
            } else {

                boolean containsValidRole = false;
                for (String role : userResource.getSelectedRoles()) {
                    if (role.equalsIgnoreCase("1") || role.equalsIgnoreCase("2")) {
                        containsValidRole = true;
                        break;
                    }
                }

                if (!containsValidRole) {
                    isValidated = false;
                    userResource.setErrRole("Please select Admin or User Role too.");
                } else {
                    userResource.setErrRole("");
                }

//                for (String role : userResource.getSelectedRoles()) {
//                    if (role.equalsIgnoreCase("1") || role.equalsIgnoreCase("2")) {
////                        isValidated = true;
//                        userResource.setErrRole("");
//                        break;
//                    } else {
//                        isValidated = false;
//                        userResource.setErrRole("Please select Admin or User Role too.");
//                    }
//                }
            }

            if (!(validate.isValidRADIUS_Timeout(userResource.getSessionTimeout()))) {
                isValidated = false;
                userResource.setErrSessionTimeout("Incorrect");
            }
            if (!(validate.isValidCalendarDate(userResource.getAccountExpiration()))) {
                isValidated = false;
                userResource.setErrAccountExpiration("Incorrect");
            }

            if (userResource.getEmail().trim().equals("") || !(validate.isValidEmail(userResource.getEmail())) || (!validate.isValidDefaultTextLength(userResource.getEmail()))) {
                isValidated = false;
                userResource.setErrEmail("Incorrect");
            }

            if (!userResource.getPhoneNo().equals("") && !(validate.isValidPhoneNumber(userResource.getPhoneNo()))) {
                isValidated = false;
                userResource.setErrPhoneNo("Incorrect");
            }

            if (!userResource.getMobileNo().equals("") && !(validate.isValidPhoneNumber(userResource.getMobileNo()))) {
                isValidated = false;
                userResource.setErrMobileNo("Incorrect");
            }

            if (isValidated) {

//                if (userResource.getNeverExpire()) {
//                    userResource.setAccountExpiration("Never Expires");
//                }
                Set<RoleResource> roleResources = new HashSet<RoleResource>();
                for (String r : userResource.getSelectedRoles()) {
                    roleResources.add(userService.readRolesByID(Long.parseLong(r)));
                }

                userResource.setRoles(roleResources);

                //
                UserResource readUpdatedUserResource = userService.updateUserDetails(id, userResource);
                UserSearchCriteria searchCriteria = new UserSearchCriteria();
                searchCriteria.setId(readUpdatedUserResource.getUserID());
                searchCriteria.setStatusCode(2); // 1 for inserted
                redirectAttributes.addFlashAttribute("searchCriteria", searchCriteria);
                redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
                return new ModelAndView("redirect:" + "/userman/user/search");

            }

            //refilling default values
            RoletListResource roletListResource = userService.readRoles();
            List<RoleResource> roleResource = roletListResource.getRoles();
            for (RoleResource role : roleResource) {
                for (String selectedRole : userResource.getSelectedRoles()) {
                    if (Long.valueOf(selectedRole).equals(role.getRoleID())) {
                        role.setSelected(true);
                        break;
                    }
                }
            }
            model.addObject("userResource", userResource);
            model.addObject("roleResource", roleResource);
            model.setViewName("user_edit");
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

    @RequestMapping(value = "/user/admin/changePassword", method = {RequestMethod.POST})
    public ModelAndView changePasswordByAdminUserDisplay(@RequestParam(value = "id", required = true) Long id, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            UserResource userResource = userService.readUserByID(id);
            model.addObject("userResource", userResource);

            model.setViewName("user_changePaswordByAdmin");
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

    @RequestMapping(value = "/user/admin/changePassword/submit", method = {RequestMethod.POST})
    public ModelAndView changePasswordByAdminUserProcess(@RequestParam(value = "id", required = true) Long id, @ModelAttribute("userResource") UserResource userResource, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            UserResource readUserResource = userService.readUserByID(id);
            userResource.setUserID(readUserResource.getUserID());

            boolean isValidated = true;
            Validate validate = new Validate();

            String encryptedPassword = "";

            //sha password encryption
            CryptUtil cryptUtil = new CryptUtil();
            encryptedPassword = (cryptUtil.asHex(SHA1.SHA1(userResource.getPassword())));

            if (validate.passwordTMP(userResource.getConfirmPassword())) {
                if (userResource.getPassword().compareTo(userResource.getConfirmPassword()) != 0) {
                    isValidated = false;
                    userResource.setErrConfirmPassword("Password and confirm password don't match");
                }
            } else {
                isValidated = false;
                userResource.setErrConfirmPassword("Incorrect & Length also should be in between 5-10 letters");
            }

            if (isValidated) {
                userResource.setPassword(encryptedPassword);
                UserResource readUpdatedUserResource = userService.updateUserPassword(id, userResource);

                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if (principal instanceof UserDetails) {

                    UserDetails details = (UserDetails) principal;

                    if (userResource.getUsername().trim().equalsIgnoreCase(details.getUsername().trim())) {

                        SecurityContextHolder.getContext().setAuthentication(null);
                        redirectAttributes.addFlashAttribute("pageInfo", "Password changed successfully. Please re-login with new password.");
                        return new ModelAndView("redirect:" + "/login?logout");

                    } else {
                        UserSearchCriteria searchCriteria = new UserSearchCriteria();
                        searchCriteria.setId(readUpdatedUserResource.getUserID());
                        searchCriteria.setStatusCode(2); // 1 for inserted
                        redirectAttributes.addFlashAttribute("searchCriteria", searchCriteria);
                        redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
                        return new ModelAndView("redirect:" + "/userman/user/search");
                    }

                } else {
                    UserSearchCriteria searchCriteria = new UserSearchCriteria();
                    searchCriteria.setId(readUpdatedUserResource.getUserID());
                    searchCriteria.setStatusCode(2); // 1 for inserted
                    redirectAttributes.addFlashAttribute("searchCriteria", searchCriteria);
                    redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
                    return new ModelAndView("redirect:" + "/userman/user/search");
                }

            }

            model.addObject("userResource", userResource);
            model.setViewName("user_changePaswordByAdmin");
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

    @RequestMapping(value = "/user/changePassword", method = {RequestMethod.POST})
    public ModelAndView changePasswordByUserDisplay(@RequestParam(value = "userName", required = false) String userName, final RedirectAttributes redirectAttributes) {
        try {
            System.out.println("KPPPPPPPPLL");
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");
            UserResource userResource = userService.readUserByUsername(userName);
            model.addObject("userResource", userResource);
            model.setViewName("user_changePasword");
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

    @RequestMapping(value = "/user/changePassword/submit", method = {RequestMethod.POST})
    public ModelAndView changePasswordProcess(@ModelAttribute("userResource") UserResource userResource, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            String encryptedPassword = "";
            String encryptedOldPassword = "";

            //sha password encryption
            CryptUtil cryptUtil = new CryptUtil();
            encryptedOldPassword = (cryptUtil.asHex(SHA1.SHA1(userResource.getCurrentPassword())));
            encryptedPassword = (cryptUtil.asHex(SHA1.SHA1(userResource.getPassword())));
            boolean isValidated = true;

            Validate validate = new Validate();

            UserResource readUserResource = userService.readUserByUsername(userResource.getUsername());

            UserResource readUserResourceForPassword = userService.readUserByUsernameAndPassword(userResource.getUsername(), encryptedOldPassword);

            if (readUserResourceForPassword == null) {
                isValidated = false;
                userResource.setErrCurrentPassword("Incorrect");
            }
            if (validate.passwordTMP(userResource.getPassword())) {
                if (userResource.getPassword().compareTo(userResource.getConfirmPassword()) != 0) {
                    isValidated = false;
                    userResource.setErrConfirmPassword("Password and confirm password don't match");
                }
            } else {
                isValidated = false;
                userResource.setErrConfirmPassword("Incorrect & Length also should be in between 5-10 letters");
            }

            if (isValidated) {
                userResource.setPassword(encryptedPassword);
                UserResource readUpdatedUserResource = userService.updateUserPassword(readUserResource.getUserID(), userResource);

//                redirectAttributes.addFlashAttribute("pageMessage", "Password changed successfully.");
//                return new ModelAndView("redirect:" + "/result");
                SecurityContextHolder.getContext().setAuthentication(null);
                redirectAttributes.addFlashAttribute("pageInfo", "Password changed successfully. Please re-login with new password.");
                return new ModelAndView("redirect:" + "/login?logout");

            } else {
                model.addObject("pageError", "Process cannot be completed.");
            }

            model.setViewName("user_changePasword");
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

    @RequestMapping(value = "/user/password_expired/changePassword", method = {RequestMethod.GET})
    public ModelAndView changeExpiredPasswordByUserDisplay(final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated  ");
            model.addObject("message", "Logged On!");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            UserResource userResource = userService.readUserByUsername(username);
            model.addObject("userResource", userResource);
            model.setViewName("user_changeExpiredPasword");
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

    @RequestMapping(value = "/user/password_expired/changePassword/submit", method = {RequestMethod.POST})
    public ModelAndView changeExpiredPasswordProcess(@ModelAttribute("userResource") UserResource userResource, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            String encryptedPassword = "";
            String encryptedOldPassword = "";

            //sha password encryption
            CryptUtil cryptUtil = new CryptUtil();
            encryptedOldPassword = (cryptUtil.asHex(SHA1.SHA1(userResource.getCurrentPassword())));
            encryptedPassword = (cryptUtil.asHex(SHA1.SHA1(userResource.getPassword())));
            boolean isValidated = true;

            Validate validate = new Validate();

            UserResource readUserResource = userService.readUserByUsername(userResource.getUsername());

            UserResource readUserResourceForPassword = userService.readUserByUsernameAndPassword(userResource.getUsername(), encryptedOldPassword);

            if (readUserResourceForPassword == null) {
                isValidated = false;
                userResource.setErrCurrentPassword("Incorrect");
            }
            if (validate.passwordTMP(userResource.getPassword())) {
                if (userResource.getPassword().compareTo(userResource.getConfirmPassword()) != 0) {
                    isValidated = false;
                    userResource.setErrConfirmPassword("Password and confirm password don't match");
                }
            } else {
                isValidated = false;
                userResource.setErrConfirmPassword("Incorrect & Length also should be in between 5-10 letters");
            }

            if (isValidated) {
                userResource.setPassword(encryptedPassword);
                userResource.setPasswordExpire(false);
                UserResource readUpdatedUserResource = userService.updateUserExpiredPassword(readUserResource.getUserID(), userResource);

//                redirectAttributes.addFlashAttribute("pageMessage", "Password changed successfully.");
//                return new ModelAndView("redirect:" + "/result");
                SecurityContextHolder.getContext().setAuthentication(null);
                redirectAttributes.addFlashAttribute("pageInfo", "Password changed successfully. Please re-login with new password.");
                return new ModelAndView("redirect:" + "/login?logout");
            } else {
                model.addObject("pageError", "Process cannot be completed.");
            }

            model.setViewName("user_changeExpiredPasword");
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

    @RequestMapping(value = "/user/email/submit", method = {RequestMethod.POST})
    public ModelAndView emailUserProcess(@RequestParam(value = "id", required = true) Long id, @RequestParam(value = "password", required = true) String password, final RedirectAttributes redirectAttributes) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            UserResource readUserResource = userService.emailUserByID(id, password);

//            UserSearchCriteria searchCriteria = new UserSearchCriteria();
//            searchCriteria.setId(0L);//for search all
//            searchCriteria.setStatusCode(3); // 3 for deleted
//            redirectAttributes.addFlashAttribute("searchCriteria", searchCriteria);
            redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_EMAIL_SENT.getMessage() + " To " + readUserResource.getUsername());
            return new ModelAndView("redirect:" + "/result");

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

    @RequestMapping(value = "/user/send_email/submit", method = {RequestMethod.POST})
    public ModelAndView emailToUserProcess(@ModelAttribute("emailData") EmailData emailData, final RedirectAttributes redirectAttributes) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

//            UserResource readUserResource = userService.emailUserByID(emailData.getId(), emailData.getPassword());
            UserResource readUserResource = userService.sendEmailToUser(emailData);

//            UserSearchCriteria searchCriteria = new UserSearchCriteria();
//            searchCriteria.setId(0L);//for search all
//            searchCriteria.setStatusCode(3); // 3 for deleted
//            redirectAttributes.addFlashAttribute("searchCriteria", searchCriteria);
            redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_EMAIL_SENT.getMessage() + " To " + readUserResource.getUsername());
            return new ModelAndView("redirect:" + "/result");

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

    @RequestMapping(value = "/user/add/image/upload/submit", method = {RequestMethod.POST})
    public ModelAndView uploadUserProfileImageInUserAddProcess(@RequestParam("file") MultipartFile file, @RequestParam(value = "id", required = true) Long id, @RequestParam(value = "newPassword", required = true) String newPassword, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            UserResource userResource = userService.readUserByID(id);

            if (file.isEmpty()) {
                model.addObject("pageError", ApplicationMessageEnum.ERROR_EMPTY_PROFILE_PICTURE_UPLOAD.getMessage());
                userResource.setPassword(newPassword);
                userResource.setErrProfileImage("Please Select Valid File.");
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
                    uploadedFileContent.setNewFilename(userResource.getUsername() + "." + extension);
                    uploadedFileContent.setSourceId(userResource.getUserID());

                    FileInfo fileInfo = userService.uploadUserProfileImageByObject(uploadedFileContent);

                    if (fileInfo != null) {
                        userResource.setErrProfileImage("");
                        model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_PROFILE_PICTURE_UPLOAD.getMessage());
                    } else {
                        userResource.setErrProfileImage(ApplicationMessageEnum.ERROR_FAILED_PROFILE_PICTURE_UPLOAD.getMessage());
                        model.addObject("pageError", ApplicationMessageEnum.ERROR_FAILED_PROFILE_PICTURE_UPLOAD.getMessage());
                    }

                } else {
                    userResource.setErrProfileImage(ApplicationMessageEnum.ERROR_INVALID_PROFILE_PICTURE_UPLOAD.getMessage());
                    model.addObject("pageError", ApplicationMessageEnum.ERROR_INVALID_PROFILE_PICTURE_UPLOAD.getMessage());
                    // Handle error of not correct extension
                }
            }
            userResource.setPassword(newPassword);
            model.addObject("userResource", userResource);
            model.setViewName("user_add_success");
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

    @RequestMapping(value = "/user/edit/image/upload/submit", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView uploadUserProfileImageInUserEditProcess(@RequestParam("file") MultipartFile file, @RequestParam(value = "id", required = true) Long id,
            RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            UserResource userResource = userService.readUserByID(id);

            if (file.isEmpty()) {
                model.addObject("pageError", ApplicationMessageEnum.ERROR_EMPTY_PROFILE_PICTURE_UPLOAD.getMessage());
                userResource.setErrProfileImage("Please Select Valid File.");
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
                    uploadedFileContent.setNewFilename(userResource.getUsername() + "." + extension);
                    uploadedFileContent.setSourceId(userResource.getUserID());

                    FileInfo fileInfo = userService.uploadUserProfileImageByObject(uploadedFileContent);

                    if (fileInfo != null) {
                        userResource.setErrProfileImage("");
                        model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_PROFILE_PICTURE_UPLOAD.getMessage());
                    } else {
                        userResource.setErrProfileImage(ApplicationMessageEnum.ERROR_FAILED_PROFILE_PICTURE_UPLOAD.getMessage());
                        model.addObject("pageError", ApplicationMessageEnum.ERROR_FAILED_PROFILE_PICTURE_UPLOAD.getMessage());
                    }

                } else {
                    userResource.setErrProfileImage(ApplicationMessageEnum.ERROR_INVALID_PROFILE_PICTURE_UPLOAD.getMessage());
                    model.addObject("pageError", ApplicationMessageEnum.ERROR_INVALID_PROFILE_PICTURE_UPLOAD.getMessage());
                    // Handle error of not correct extension
                }
            }

            model.addObject("userResource", userResource);

            //refilling default values
            RoletListResource roletListResource = userService.readRoles();
            List<RoleResource> roleResource = roletListResource.getRoles();
            for (RoleResource role : roleResource) {
                for (RoleResource userRole : userResource.getRoles()) {
                    if (role.getRoleID().equals(userRole.getRoleID())) {
                        role.setSelected(true);
                        break;
                    }
                }
            }
            model.addObject("roleResource", roleResource);

            if (userResource.getAccountExpiration().equalsIgnoreCase("Never Expires")) {
                Locale locale = new Locale("en", "US");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
                long timeInMillis = Calendar.getInstance().getTimeInMillis();
                String initialDeviceDate = simpleDateFormat.format(timeInMillis);
                userResource.setAccountExpiration(initialDeviceDate);
                userResource.setNeverExpire(true);
            }
            model.setViewName("user_edit");
            return model;
        } catch (IOException e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (UnAuthorizedException e) {
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

    @RequestMapping(value = "/user/changeenablestatus/submit", method = {RequestMethod.POST})
    public ModelAndView changeEnableStatusOfCourseProcess(@RequestParam(value = "id", required = true) Long id, final RedirectAttributes redirectAttributes) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            UserResource userResource = userService.updateUserEnabledStatus(id);

            UserSearchCriteria searchCriteria = new UserSearchCriteria();
            searchCriteria.setId(userResource.getUserID());//for search all
//            searchCriteria.setStatusCode(2); // 2 for Updated
            redirectAttributes.addFlashAttribute("searchCriteria", searchCriteria);
            redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
            return new ModelAndView("redirect:" + "/userman/user/search");

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
