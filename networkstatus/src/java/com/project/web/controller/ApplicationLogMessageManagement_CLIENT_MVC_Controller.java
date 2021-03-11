/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.web.controller;

import com.project.config.PropertiesConfig;
import com.project.dao.util.ApplicationLogMessageService;
import com.project.exception.rest.BadRequestException;
import com.project.exception.rest.InternalServerException;
import com.project.exception.rest.UnAuthorizedException;
import com.project.model.util.ApplicationLogMessageListResource;
import com.project.model.util.ApplicationLogMessageResource;
import com.project.util.ApplicationMessageEnum;
import com.project.util.Log4jUtil;
import com.project.util.searchcriteria.ApplicationLogMessageSearchCriteria;
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
@RequestMapping(value = "/applicationlogman")
public class ApplicationLogMessageManagement_CLIENT_MVC_Controller extends Base_Controller {

    final int maxRecordsToFetch = 20;

    @Autowired
    private ApplicationLogMessageService applicationLogMessageService;

    @Autowired
    @Resource(name = "propertiesConfig")
    private PropertiesConfig propertiesConfig;

    @RequestMapping(value = "/applicationlog/search", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView searchApplicationLogMessageDisplay(@ModelAttribute("searchCriteria") ApplicationLogMessageSearchCriteria searchCriteria, final RedirectAttributes redirectAttributes) {
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

            ApplicationLogMessageListResource applicationLogMessageListResource = applicationLogMessageService.findApplicationLogMessageListBySearchCriteria(searchCriteria);
//            DeviceUsersListResource deviceUsersListResource = deviceUserService.findDeviceUsersBySearchCriteria(searchCriteria);

            model.addObject("applicationLogMessageListResource", applicationLogMessageListResource);
            if (searchCriteria.getStatusCode() == null) {

            } else if (searchCriteria.getStatusCode() == 1) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_ADD_RECORD.getMessage());
            } else if (searchCriteria.getStatusCode() == 2) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
            } else if (searchCriteria.getStatusCode() == 3) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
            }

            model.addObject("searchCriteria", searchCriteria);

            model.setViewName("application_log_message_list");
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

    @RequestMapping(value = "/applicationlog/search/submit", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView searchApplicationLogMessageDisplayProcess(@ModelAttribute("searchCriteria") ApplicationLogMessageSearchCriteria searchCriteria, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            searchCriteria.setLimitResult(maxRecordsToFetch);

            ApplicationLogMessageListResource applicationLogMessageListResource = applicationLogMessageService.findApplicationLogMessageListBySearchCriteria(searchCriteria);
//            DeviceUsersListResource deviceUsersListResource = deviceUserService.findDeviceUsersBySearchCriteria(searchCriteria);

            model.addObject("applicationLogMessageListResource", applicationLogMessageListResource);
            if (searchCriteria.getStatusCode() == null) {

            } else if (searchCriteria.getStatusCode() == 1) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_ADD_RECORD.getMessage());
            } else if (searchCriteria.getStatusCode() == 2) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
            } else if (searchCriteria.getStatusCode() == 3) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
            }

            model.addObject("searchCriteria", searchCriteria);

            model.setViewName("application_log_message_list");
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

    @RequestMapping(value = "/applicationlog/delete/submit", method = {RequestMethod.POST})
    public ModelAndView deleteApplicationLogMessageProcess(@RequestParam(value = "id", required = true) Long id, final RedirectAttributes redirectAttributes) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");
            ApplicationLogMessageResource applicationLogMessageResource = applicationLogMessageService.deleteApplicationLogMessage(id);
            redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
            return new ModelAndView("redirect:" + "/applicationlogman/applicationlog/search");

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

    @RequestMapping(value = "/applicationlog/delete/all/submit", method = {RequestMethod.POST})
    public ModelAndView deleteAllApplicationLogMessageProcess(final RedirectAttributes redirectAttributes) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            int status = applicationLogMessageService.deleteAllApplicationLogMessage();
            redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
            return new ModelAndView("redirect:" + "/applicationlogman/applicationlog/search");

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

//    @RequestMapping(value = "/applicationlog/dummyexecption/submit", method = {RequestMethod.POST})
//    public ModelAndView sendDummyLogMessageProcess(final RedirectAttributes redirectAttributes) {
//        try {
//
//            ModelAndView model = new ModelAndView();
//            model.addObject("title", "Spring Security - Authenticated ");
//            model.addObject("message", "Logged On!");
//
//            ApplicationLogMessageResource logMessageResource = new ApplicationLogMessageResource();
//
//            try {
//                int i = 1 / 0;
//            } catch (Exception e) {
//                logMessageResource.setLogMessageBlop(Log4jUtil.getFormattedMessageFromStackTraceElements(e).getBytes());
//            }
//
//            applicationLogMessageService.createApplicationLogMessage(logMessageResource);
//            redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
//            return new ModelAndView("redirect:" + "/applicationlogman/applicationlog/search");
//
//        } catch (UnAuthorizedException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_UNAUTHORIZED_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (InternalServerException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_INTERNAL_WEB_SERVER.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (BadRequestException e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_BAD_REQUEST.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        } catch (Exception e) {
//            Log4jUtil.fatal(e);
//            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE.getMessage() + e.getMessage());
//            return new ModelAndView("redirect:" + "/result");
//        }
//    }
    @RequestMapping(value = "/applicationlog/dummyexecption/submit", method = {RequestMethod.POST})
    public ModelAndView sendDummyLogMessagesProcess(final RedirectAttributes redirectAttributes) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            ApplicationLogMessageListResource logMessageListResource = new ApplicationLogMessageListResource();

            ApplicationLogMessageResource logMessageResource = new ApplicationLogMessageResource();

            try {
                int i = 1 / 0;
            } catch (Exception e) {
                logMessageResource.setLogMessageBlop(Log4jUtil.getFormattedMessageFromStackTraceElements(e).getBytes());
            }

            ApplicationLogMessageResource logMessageResource2 = new ApplicationLogMessageResource();

            try {
                String text = null;
                text.trim();
            } catch (Exception e) {
                logMessageResource2.setLogMessageBlop(Log4jUtil.getFormattedMessageFromStackTraceElements(e).getBytes());
            }

            logMessageListResource.getApplicationLogMessageResources().add(logMessageResource);
            logMessageListResource.getApplicationLogMessageResources().add(logMessageResource2);

            applicationLogMessageService.createApplicationLogMessages(logMessageListResource);
            redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
            return new ModelAndView("redirect:" + "/applicationlogman/applicationlog/search");

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

    // New
    
    
    
     @RequestMapping(value = "/applicationlog_1/search", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView searchApplicationLogMessageDisplay_1(@ModelAttribute("searchCriteria") ApplicationLogMessageSearchCriteria searchCriteria, final RedirectAttributes redirectAttributes) {
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

            ApplicationLogMessageListResource applicationLogMessageListResource = applicationLogMessageService.findApplicationLogMessageListBySearchCriteria(searchCriteria);
//            DeviceUsersListResource deviceUsersListResource = deviceUserService.findDeviceUsersBySearchCriteria(searchCriteria);

            model.addObject("applicationLogMessageListResource", applicationLogMessageListResource);
            if (searchCriteria.getStatusCode() == null) {

            } else if (searchCriteria.getStatusCode() == 1) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_ADD_RECORD.getMessage());
            } else if (searchCriteria.getStatusCode() == 2) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
            } else if (searchCriteria.getStatusCode() == 3) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
            }

            model.addObject("searchCriteria", searchCriteria);

            model.setViewName("application_log_message_list_1");
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

    @RequestMapping(value = "/applicationlog_1/search/submit", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView searchApplicationLogMessageDisplayProcess_1(@ModelAttribute("searchCriteria") ApplicationLogMessageSearchCriteria searchCriteria, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            searchCriteria.setLimitResult(maxRecordsToFetch);

            ApplicationLogMessageListResource applicationLogMessageListResource = applicationLogMessageService.findApplicationLogMessageListBySearchCriteria(searchCriteria);
//            DeviceUsersListResource deviceUsersListResource = deviceUserService.findDeviceUsersBySearchCriteria(searchCriteria);

            model.addObject("applicationLogMessageListResource", applicationLogMessageListResource);
            if (searchCriteria.getStatusCode() == null) {

            } else if (searchCriteria.getStatusCode() == 1) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_ADD_RECORD.getMessage());
            } else if (searchCriteria.getStatusCode() == 2) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_UPDATE_RECORD.getMessage());
            } else if (searchCriteria.getStatusCode() == 3) {
                model.addObject("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
            }

            model.addObject("searchCriteria", searchCriteria);

            model.setViewName("application_log_message_list_1");
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

    @RequestMapping(value = "/applicationlog_1/delete/submit", method = {RequestMethod.POST})
    public ModelAndView deleteApplicationLogMessageProcess_1(@RequestParam(value = "id", required = true) Long id, final RedirectAttributes redirectAttributes) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");
            ApplicationLogMessageResource applicationLogMessageResource = applicationLogMessageService.deleteApplicationLogMessage(id);
            redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
            return new ModelAndView("redirect:" + "/applicationlogman/applicationlog_1/search");

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

    @RequestMapping(value = "/applicationlog_1/delete/all/submit", method = {RequestMethod.POST})
    public ModelAndView deleteAllApplicationLogMessageProcess_1(final RedirectAttributes redirectAttributes) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            int status = applicationLogMessageService.deleteAllApplicationLogMessage();
            redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
            return new ModelAndView("redirect:" + "/applicationlogman/applicationlog_1/search");

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

    @RequestMapping(value = "/applicationlog_1/dummyexecption/submit", method = {RequestMethod.POST})
    public ModelAndView sendDummyLogMessagesProcess_1(final RedirectAttributes redirectAttributes) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            ApplicationLogMessageListResource logMessageListResource = new ApplicationLogMessageListResource();

            ApplicationLogMessageResource logMessageResource = new ApplicationLogMessageResource();

            try {
                int i = 1 / 0;
            } catch (Exception e) {
                logMessageResource.setLogMessageBlop(Log4jUtil.getFormattedMessageFromStackTraceElements(e).getBytes());
            }

            ApplicationLogMessageResource logMessageResource2 = new ApplicationLogMessageResource();

            try {
                String text = null;
                text.trim();
            } catch (Exception e) {
                logMessageResource2.setLogMessageBlop(Log4jUtil.getFormattedMessageFromStackTraceElements(e).getBytes());
            }

            logMessageListResource.getApplicationLogMessageResources().add(logMessageResource);
            logMessageListResource.getApplicationLogMessageResources().add(logMessageResource2);

            applicationLogMessageService.createApplicationLogMessages(logMessageListResource);
            redirectAttributes.addFlashAttribute("pageMessage", ApplicationMessageEnum.SUCCESS_DELETE_RECORD.getMessage());
            return new ModelAndView("redirect:" + "/applicationlogman/applicationlog_1/search");

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
