/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.web.controller;

import com.project.config.PropertiesConfig;
import com.project.dao.network.NodeAlertInfoArchiveService;
import com.project.exception.rest.BadRequestException;
import com.project.exception.rest.InternalServerException;
import com.project.exception.rest.UnAuthorizedException;
import com.project.model.network.NodeAlertInfoArchiveListResource;
import com.project.model.network.NodeAlertInfoArchiveResource;
import com.project.util.ApplicationMessageEnum;
import com.project.util.Log4jUtil;
import com.project.util.searchcriteria.NodeAlertInfoArchiveSearchCriteria;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import javax.annotation.Resource;
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
@RequestMapping(value = "/nodealertinfoman")
public class NodeAlertInfoArchiveManagement_CLIENT_MVC_Controller extends Base_Controller {

    final int maxRecordsToFetch = 4;

    @Autowired
    private NodeAlertInfoArchiveService nodeAlertInfoArchiveService;

    @Autowired
    @Resource(name = "propertiesConfig")
    private PropertiesConfig propertiesConfig;

    @RequestMapping(value = "/nodealertinfo/search", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView searchNodeAlertInfoArchiveDisplay(@ModelAttribute("searchCriteria") NodeAlertInfoArchiveSearchCriteria searchCriteria, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            Locale locale = new Locale("en", "US");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
            long timeInMillis = Calendar.getInstance().getTimeInMillis();
            String initialDeviceDate = simpleDateFormat.format(Long.valueOf(timeInMillis));
            searchCriteria.setDateFrom(initialDeviceDate);
            searchCriteria.setDateTo(initialDeviceDate);
            searchCriteria.setHourTo(23);
            searchCriteria.setMinuteTo(59);

            searchCriteria.setLimitResult(maxRecordsToFetch);

            NodeAlertInfoArchiveListResource nodeAlertInfoArchiveListResource = nodeAlertInfoArchiveService.findNodeAlertInfoArchiveListBySearchCriteria(searchCriteria);
//            DeviceUsersListResource deviceUsersListResource = deviceUserService.findDeviceUsersBySearchCriteria(searchCriteria);

            model.addObject("nodeAlertInfoArchiveListResource", nodeAlertInfoArchiveListResource);
            if (searchCriteria.getStatusCode() == null) {

            } else if (searchCriteria.getStatusCode() == 1) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_ADD_RECORD.getMessage());
            } else if (searchCriteria.getStatusCode() == 2) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
            } else if (searchCriteria.getStatusCode() == 3) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
            }

            model.addObject("searchCriteria", searchCriteria);

            model.setViewName("node_alert_info_archive_list");
            return model;
        } catch (UnAuthorizedException e) {
            writeLogMessage(e);
            e.printStackTrace();
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (InternalServerException e) {
            writeLogMessage(e);
            e.printStackTrace();
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (BadRequestException e) {
            writeLogMessage(e);
            e.printStackTrace();
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (Exception e) {
            writeLogMessage(e);
            e.printStackTrace();
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

    @RequestMapping(value = "/nodealertinfo/search/submit", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView searchNodeAlertInfoArchiveDisplayProcess(@ModelAttribute("searchCriteria") NodeAlertInfoArchiveSearchCriteria searchCriteria, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            searchCriteria.setLimitResult(maxRecordsToFetch);

            NodeAlertInfoArchiveListResource nodeAlertInfoArchiveListResource = nodeAlertInfoArchiveService.findNodeAlertInfoArchiveListBySearchCriteria(searchCriteria);
//            DeviceUsersListResource deviceUsersListResource = deviceUserService.findDeviceUsersBySearchCriteria(searchCriteria);

            model.addObject("nodeAlertInfoArchiveListResource", nodeAlertInfoArchiveListResource);
            if (searchCriteria.getStatusCode() == null) {

            } else if (searchCriteria.getStatusCode() == 1) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_ADD_RECORD.getMessage());
            } else if (searchCriteria.getStatusCode() == 2) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
            } else if (searchCriteria.getStatusCode() == 3) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
            }

            model.addObject("searchCriteria", searchCriteria);

            model.setViewName("node_alert_info_archive_list");
            return model;
        } catch (UnAuthorizedException e) {
            writeLogMessage(e);
            e.printStackTrace();
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (InternalServerException e) {
            writeLogMessage(e);
            e.printStackTrace();
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (BadRequestException e) {
            writeLogMessage(e);
            e.printStackTrace();
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (Exception e) {
            writeLogMessage(e);
            e.printStackTrace();
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

    @RequestMapping(value = "/nodealertinfo/delete/submit", method = {RequestMethod.POST})
    public ModelAndView deleteNodeAlertInfoArchiveProcess(@RequestParam(value = "id", required = true) Long id, final RedirectAttributes redirectAttributes) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");
            NodeAlertInfoArchiveResource nodeAlertInfoArchiveResource = nodeAlertInfoArchiveService.deleteNodeAlertInfoArchive(id);
            redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
            return new ModelAndView("redirect:" + "/nodealertinfoman/nodealertinfo/search");

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

    @RequestMapping(value = "/nodealertinfo/delete/all/submit", method = {RequestMethod.POST})
    public ModelAndView deleteAllNodeAlertInfoArchiveProcess(final RedirectAttributes redirectAttributes) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            int status = nodeAlertInfoArchiveService.deleteAllNodeAlertInfoArchive();
            redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
            return new ModelAndView("redirect:" + "/nodealertinfoman/nodealertinfo/search");

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

    @RequestMapping(value = "/nodealertinfo/dummyexecption/submit", method = {RequestMethod.POST})
    public ModelAndView sendDummyLogMessagesProcess(final RedirectAttributes redirectAttributes) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            NodeAlertInfoArchiveListResource logMessageListResource = new NodeAlertInfoArchiveListResource();

            NodeAlertInfoArchiveResource logMessageResource = new NodeAlertInfoArchiveResource();
            int randomValue = (int) (Math.random() * 10);
            logMessageResource.setNodeName("Node Name " + randomValue);
            logMessageResource.setNodeType("Node Type " + randomValue);
            logMessageResource.setType(randomValue);
            logMessageResource.setDescription("Desc " + randomValue);

            NodeAlertInfoArchiveResource logMessageResource2 = new NodeAlertInfoArchiveResource();

            int randomValue2 = (int) (Math.random() * 10);
            logMessageResource2.setNodeName("Node Name " + randomValue2);
            logMessageResource2.setNodeType("Node Type " + randomValue2);
            logMessageResource2.setType(randomValue);
            logMessageResource.setDescription("Desc " + randomValue2);

            logMessageListResource.getNodeAlertInfoArchiveResources().add(logMessageResource);
            logMessageListResource.getNodeAlertInfoArchiveResources().add(logMessageResource2);

            nodeAlertInfoArchiveService.createNodeAlertInfoArchives(logMessageListResource);
            redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
            return new ModelAndView("redirect:" + "/nodealertinfoman/nodealertinfo/search");

        } catch (UnAuthorizedException e) {
            writeLogMessage(e);
            e.printStackTrace();
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (InternalServerException e) {
            writeLogMessage(e);
            e.printStackTrace();
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (BadRequestException e) {
            writeLogMessage(e);
            e.printStackTrace();
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        } catch (Exception e) {
            writeLogMessage(e);
            e.printStackTrace();
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

}
