/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.web.controller;

import com.project.config.PropertiesConfig;
import com.project.dao.system.ControlPanelConfigService;
import com.project.dao.util.ApplicationLogMessageService;
import com.project.exception.rest.BadRequestException;
import com.project.exception.rest.InternalServerException;
import com.project.exception.rest.UnAuthorizedException;
import com.project.model.system.ControlPanel;
import com.project.model.system.OperatingSystem_CPUStatus;
import com.project.model.system.OperatingSystem_JVM;
import com.project.model.system.OperatingSystem_PhysicalMemory;
import com.project.model.system.OperatingSystem_SwapMemory;
import com.project.model.system.OperatingSystem_ValueHolder;
import com.project.model.util.ApplicationLogMessageResource;
import com.project.util.ApplicationMessageEnum;
import com.project.util.DateConverter;
import com.project.util.Log4jUtil;
import license.LicenseManagement;
import com.project.util.OperatingSystem;
import com.project.util.TerminalManagenment;
import java.security.Principal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Random;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Samrit
 */
@Controller
@RequestMapping(value = "/system")
public class SystemManagement_CLIENT_MVC_Controller extends SimpleFormController {

    @Autowired
    private ControlPanelConfigService controlPanelConfigService;

    @Autowired
    @Resource(name = "propertiesConfig")
    private PropertiesConfig propertiesConfig;

    @Autowired
    private ApplicationLogMessageService applicationLogMessageService;

    public SystemManagement_CLIENT_MVC_Controller() {
    }

    private void initializeData(HttpServletRequest request, ModelAndView modelAndView) throws Exception {

        ControlPanel appServerControlPanel = new ControlPanel();
        appServerControlPanel.setMacAddress(LicenseManagement.getAnyInterfaceMacAddressExceptBridge());
        appServerControlPanel.setHostId(LicenseManagement.getHostId());
//        controlPanel.setLicenseExpiryDate("Unlimited Edition");
        appServerControlPanel.setLicenseExpiryDate(LicenseManagement.getExpireyDate(request));
        appServerControlPanel.setAppVersion(propertiesConfig.getProperty("app_version"));
        modelAndView.addObject("appServerControlPanel", appServerControlPanel);

        ControlPanel webServerControlPanel = controlPanelConfigService.readControlPanel();
//        controlPanel.setMacAddress(LicenseManagement.getAnyInterfaceMacAddressExceptBridge());
//        controlPanel.setHostId(LicenseManagement.getHostId());
////        controlPanel.setLicenseExpiryDate("Unlimited Edition");
//        controlPanel.setLicenseExpiryDate(LicenseManagement.getExpireyDate(request));
//        controlPanel.setAppVersion(propertiesConfig.getProperty("app_version"));
        modelAndView.addObject("webServerControlPanel", webServerControlPanel);

    }

    @RequestMapping(value = "/control_panel", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView controlPanelPageDisplay(final HttpServletRequest request, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");
            initializeData(request, model);

            model.setViewName("control_panel");
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
            redirectAttributes.addFlashAttribute("pageError", "Error in page." + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }

    }

    @RequestMapping(value = "/control_panel/ajaxJVMStatus", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    String getCurrentJVMStatus(@RequestParam(value = "name") String name) {
        OperatingSystem information = new OperatingSystem();
        OperatingSystem_JVM jVMModel = information.getJVMInformationModel();
        String jvmStatus = jVMModel.getUsedMemory().intValue() + ";" + jVMModel.getFreeMemory().intValue() + ";" + jVMModel.getTotalMemory().intValue() + ";" + jVMModel.getMaxMemory();
        return jvmStatus;
    }

    @RequestMapping(value = "/control_panel/ajaxPhysicalMemoryStatus", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    String getCurrentPhysicalMemorystatus(@RequestParam(value = "name") String name) {

        OperatingSystem information = new OperatingSystem();
        OperatingSystem_PhysicalMemory physicalMemoryStatsModel = information.getPhysicalRAMStatusInMB();

//        For Testing        
//        OperatingSystem_PhysicalMemory physicalMemoryStatsModel = new OperatingSystem_PhysicalMemory();
//        physicalMemoryStatsModel.setFree(10000 * Math.random());
//        physicalMemoryStatsModel.setTotal(new Double(10000));
        DecimalFormat df = new DecimalFormat("#.##");
        long percent = Math.round((physicalMemoryStatsModel.getUsed() / physicalMemoryStatsModel.getTotal()) * 100);
        System.out.println(percent);

        String physicalRamStatus = percent + ";" + df.format((physicalMemoryStatsModel.getUsed() / 1024)) + "GB" + ";" + df.format((physicalMemoryStatsModel.getTotal() / 1024)) + "GB";
        return physicalRamStatus;

    }

    @RequestMapping(value = "/control_panel/ajaxSwapMemoryStatus", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    String getCurrentSwapMemorystatus(@RequestParam(value = "name") String name) {
        OperatingSystem information = new OperatingSystem();
        OperatingSystem_SwapMemory swapMemoryStatsModel = information.getSWAPRAMStatusInMB();

//        OperatingSystem_SwapMemory swapMemoryStatsModel = new OperatingSystem_SwapMemory();
//        swapMemoryStatsModel.setFree(10000 * Math.random());
//        swapMemoryStatsModel.setTotal(new Double(10000));
        DecimalFormat df = new DecimalFormat("#.##");
        long percent = Math.round((swapMemoryStatsModel.getUsed() / swapMemoryStatsModel.getTotal()) * 100);
        System.out.println(percent);
        return (percent + ";" + df.format((swapMemoryStatsModel.getUsed() / 1024)) + "GB" + ";" + df.format((swapMemoryStatsModel.getTotal() / 1024)) + "GB");

    }

    @RequestMapping(value = "/control_panel/ajaxCPUStatus", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    String getCurrentCPUStatus(@RequestParam(value = "name") String name) {

        OperatingSystem information = new OperatingSystem();
        OperatingSystem_CPUStatus cPUStatsModel = information.getCPUUsageStatus();

//        OperatingSystem_CPUStatus cPUStatsModel = new OperatingSystem_CPUStatus();
//        cPUStatsModel.setIdle(new Double(100 * Math.random()));
        DecimalFormat df = new DecimalFormat("#.##");
        return (df.format(cPUStatsModel.getUsage()) + ";" + df.format(cPUStatsModel.getIdle()) + ";" + df.format(cPUStatsModel.getUsage()));

    }

    @RequestMapping(value = "/ajaxtest", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    String getTime() {

        Random rand = new Random();
        float r = rand.nextFloat() * 100;
        String result = "<br>Next Random # is <b>" + r + "</b>. Generated on <b>" + new Date().toString() + "</b>";
        System.out.println("Debug Message from CrunchifySpringAjaxJQuery Controller.." + new Date().toString());
        return result;
    }

    @RequestMapping(value = "/control_panel/terminal", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView terminalPageDisplay(final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");
            model.setViewName("controlPaneTerminal");
            return model;
        } catch (Exception e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", "Error in page." + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }

    }

    @RequestMapping(value = "/control_panel/getTerminalCommandOutput", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    String getTerminalCommandOutput(@RequestParam(value = "command") String command, @RequestParam(value = "advance") Boolean advance, Principal principal) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String currentDateTime = (dateFormat.format(date));

        final String loggedUserName = principal.getName();
        List<String> commandLines = OperatingSystem_ValueHolder.getOutputStatusTable().get(loggedUserName);

        final String loggedInUserName = principal.getName();
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Current Date : ").append(currentDateTime).append("<br/>");
        stringBuilder.append("Command : ").append(command).append("<br/>");
        stringBuilder.append("Advance Environment: ").append(advance).append("<br/>");
        stringBuilder.append("User Name : ").append(loggedInUserName).append("<br/>");
        stringBuilder.append("<br/>");
        stringBuilder.append("Output : ");
        stringBuilder.append("<br/>");

        for (String line : commandLines) {
            stringBuilder.append(line);
            stringBuilder.append("<br/>");
        }

        return stringBuilder.toString() + "</br>";
    }

    @RequestMapping(value = "/control_panel/executeTerminalCommand", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    String executeTerminalCommand(@RequestParam(value = "command") String command, @RequestParam(value = "advance") Boolean advance, Principal principal) {

        final String loggedUserName = principal.getName();
        List<String> commandLines = new ArrayList<String>();
        OperatingSystem_ValueHolder.getOutputStatusTable().put(loggedUserName, commandLines);

        new TerminalManagenment().runCommand(command, advance, loggedUserName);

        return loggedUserName;
    }

    @RequestMapping(value = "/control_panel/terminateTerminalCommand", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    String executeTerminalCommand(Principal principal) {

        final String loggedUserName = principal.getName();
        OperatingSystem_ValueHolder.getOutputStatusTable().get(loggedUserName).clear();
        OperatingSystem_ValueHolder.getProcessTable().get(loggedUserName).destroy();

        return loggedUserName;
    }

    @RequestMapping(value = "/control_panel/submitForceGarbageCollection", method = {RequestMethod.POST})
    public ModelAndView forceGarbageCollectionService(final HttpServletRequest request, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");
            int status = controlPanelConfigService.callGarbageCollector();
            if (status >= 0) {
                model.addObject("pageMessage", "Force GarbageCollector Request Activated.");
            } else {
                model.addObject("pageError", "Force GarbageCollector Is Failed To Activate.");
            }
            initializeData(request, model);
            model.setViewName("control_panel");
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
            redirectAttributes.addFlashAttribute("pageError", "Error in page." + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

    @RequestMapping(value = "/control_panel/submitForceGarbageCollectionForWebServer", method = {RequestMethod.POST})
    public ModelAndView forceGarbageCollectionServiceForWebServer(final HttpServletRequest request, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");
            int status = controlPanelConfigService.callGarbageCollectorForWebServer();
            if (status >= 0) {
                model.addObject("pageMessage", "Force GarbageCollector Request Activated.");
            } else {
                model.addObject("pageError", "Force GarbageCollector Is Failed To Activate.");
            }
            initializeData(request, model);
            model.setViewName("control_panel");
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
            redirectAttributes.addFlashAttribute("pageError", "Error in page." + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

    public void writeLogMessage(Exception exception) {
        try {
            // For System Log Messages
            ApplicationLogMessageResource logMessageResource = new ApplicationLogMessageResource();
            logMessageResource.setLogMessage(exception.toString());
            logMessageResource.setLogMessageBlop((Log4jUtil.getFormattedMessageFromStackTraceElements(exception)).getBytes());
            logMessageResource.setEntryDate(DateConverter.getCurrentConvertedDateAndTimeInUnixDate());
            applicationLogMessageService.createApplicationLogMessage(logMessageResource);
            // End For System Log Messages
        } catch (Exception e) {
            writeLogMessage(e);
            e.printStackTrace();
        }

    }

    //  New One
    @RequestMapping(value = "/control_panel_1", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView controlPanelPageDisplay_1(final HttpServletRequest request, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");
            initializeData(request, model);

            model.setViewName("control_panel_1");
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
            redirectAttributes.addFlashAttribute("pageError", "Error in page." + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }

    }

    @RequestMapping(value = "/control_panel_1/submitForceGarbageCollection", method = {RequestMethod.POST})
    public ModelAndView forceGarbageCollectionService_1(final HttpServletRequest request, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");
            int status = controlPanelConfigService.callGarbageCollector();
            if (status >= 0) {
                model.addObject("pageMessage", "Force GarbageCollector Request Activated.");
            } else {
                model.addObject("pageError", "Force GarbageCollector Is Failed To Activate.");
            }
            initializeData(request, model);
            model.setViewName("control_panel_1");
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
            redirectAttributes.addFlashAttribute("pageError", "Error in page." + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

    @RequestMapping(value = "/control_panel_1/submitForceGarbageCollectionForWebServer", method = {RequestMethod.POST})
    public ModelAndView forceGarbageCollectionServiceForWebServer_1(final HttpServletRequest request, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");
            int status = controlPanelConfigService.callGarbageCollectorForWebServer();
            if (status >= 0) {
                model.addObject("pageMessage", "Force GarbageCollector Request Activated.");
            } else {
                model.addObject("pageError", "Force GarbageCollector Is Failed To Activate.");
            }
            initializeData(request, model);
            model.setViewName("control_panel_1");
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
            redirectAttributes.addFlashAttribute("pageError", "Error in page." + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

    @RequestMapping(value = "/control_panel/reset_data/submit", method = {RequestMethod.POST})
    public ModelAndView resetAllData(final HttpServletRequest request, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");
            int status = controlPanelConfigService.resetAllDataFromWebServer();
            if (status >= 0) {
                model.addObject("pageMessage", "All Nodes And Edges Data Are Deleted.");
            } else {
                model.addObject("pageError", "All Nodes And Edges Data Are Failed To Delete.");
            }
            initializeData(request, model);
            model.setViewName("control_panel");
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
            redirectAttributes.addFlashAttribute("pageError", "Error in page." + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

}
