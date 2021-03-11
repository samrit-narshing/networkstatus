/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.web.controller;

import com.project.config.PropertiesConfig;
import com.project.dao.user.RoleService;
import com.project.exception.rest.BadRequestException;
import com.project.exception.rest.InternalServerException;
import com.project.exception.rest.UnAuthorizedException;
import com.project.model.user.RoleResource;
import com.project.model.user.RoletListResource;
import com.project.model.user.RoleResource;
import com.project.model.user.UsertListResource;
import com.project.util.ApplicationMessageEnum;
import com.project.util.CryptUtil;
import com.project.util.Log4jUtil;
import com.project.util.SHA1;
import com.project.util.Validate;
import com.project.util.searchcriteria.RoleSearchCriteria;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author SAM
 */
@Controller
@RequestMapping(value = "/roleman")
public class RoleManagement_CLIENT_MVC_Controller extends Base_Controller {

    final int maxRecordsToFetch = 20;
    final int maxRecordsToFetchForDeviceUser = 5;

    @Autowired
    private RoleService roleService;

    @Autowired
    @Resource(name = "propertiesConfig")
    private PropertiesConfig propertiesConfig;

    @RequestMapping(value = "/role/add", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView addUserDisplay(HttpServletRequest request, HttpServletResponse response, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            RoleResource roleResource = new RoleResource();
            model.addObject("roleResource", roleResource);

            model.setViewName("role_add");
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

    @RequestMapping(value = "/role/add/submit", method = {RequestMethod.POST})
    public ModelAndView addUserProcess(@ModelAttribute("roleResource") RoleResource roleResource, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");
            model.setViewName("role_add");
            model.addObject("roleResource", roleResource);

            boolean isValidated = true;

            Validate validate = new Validate();

            if (validate.isEmptyString(roleResource.getDescription()) || !validate.isValidDefaultTextLength(roleResource.getDescription())) {
                isValidated = false;
                roleResource.setErrDescription("Incorrect");
            }

            if (validate.userTMP(roleResource.getName())) {
                if (isRoleAlreadyExist(roleResource.getName())) {
                    isValidated = false;
                    roleResource.setErrName("Already exist");
                }

            } else {
                isValidated = false;
                roleResource.setErrName("Incorrect");
            }

            if (isValidated) {

                RoleResource readRoleResource = roleService.createRole(roleResource);
                model.addObject("roleResource", readRoleResource);
                model.setViewName("role_add_success");
                return model;

            }

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

    @RequestMapping(value = "/role/search", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView searchRoleDisplay(@ModelAttribute("searchCriteria") RoleSearchCriteria searchCriteria, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            searchCriteria.setLimitResult(maxRecordsToFetch);

            RoletListResource readRoletListResource = roleService.readRolesBySearchCriteria(searchCriteria);
            model.addObject("roleListResource", readRoletListResource);
            if (searchCriteria.getStatusCode() == null) {

            } else if (searchCriteria.getStatusCode() == 1) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_ADD_RECORD.getMessage());
            } else if (searchCriteria.getStatusCode() == 2) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
            } else if (searchCriteria.getStatusCode() == 3) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
            }

            model.addObject("searchCriteria", searchCriteria);

            model.setViewName("role_list");
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

    @RequestMapping(value = "/role/editRole", method = {RequestMethod.POST})
    public ModelAndView editUserDisplay(@RequestParam(value = "id", required = true) Long id, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            RoleResource roleResource = roleService.readRolesByID(id);
            model.addObject("roleResource", roleResource);
            model.setViewName("role_edit");
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

    @RequestMapping(value = "/role/edit/submit", method = {RequestMethod.POST})
    public ModelAndView editUserProcess(@RequestParam(value = "id", required = true) Long id, @ModelAttribute("roleResource") RoleResource roleResource, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            RoleResource readRoleResource = roleService.readRolesByID(id);
            roleResource.setRoleID(readRoleResource.getRoleID());

            boolean isValidated = true;
            Validate validate = new Validate();

            if (validate.isEmptyString(roleResource.getDescription()) || !validate.isValidDefaultTextLength(roleResource.getDescription())) {
                isValidated = false;
                roleResource.setErrDescription("Incorrect");
            }

            if (isValidated) {
                RoleResource readUpdatedRoleResource = roleService.updateRole(id, roleResource);
                RoleSearchCriteria searchCriteria = new RoleSearchCriteria();
                searchCriteria.setId(readUpdatedRoleResource.getRoleID());
                searchCriteria.setStatusCode(2); // 1 for inserted
                redirectAttributes.addFlashAttribute("searchCriteria", searchCriteria);
                redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
                return new ModelAndView("redirect:" + "/roleman/role/search");

            }

            //refilling default values
            model.addObject("roleResource", roleResource);
            model.setViewName("role_edit");
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

    private boolean isRoleAlreadyExist(String name) throws Exception {
        RoleResource roleResource = roleService.readRoleByName(name);
        return roleResource != null;
    }

    @RequestMapping(value = "/role/changeenablestatus/submit", method = {RequestMethod.POST})
    public ModelAndView changeEnableStatusOfCourseProcess(@RequestParam(value = "id", required = true) Long id, final RedirectAttributes redirectAttributes) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            RoleResource roleResource = roleService.updateRoleEnabledStatus(id);

            RoleSearchCriteria searchCriteria = new RoleSearchCriteria();
            searchCriteria.setId(roleResource.getRoleID());//for search all
//            searchCriteria.setStatusCode(2); // 2 for Updated
            redirectAttributes.addFlashAttribute("searchCriteria", searchCriteria);
            redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
            return new ModelAndView("redirect:" + "/roleman/role/search");

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
