/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.web.controller;

import com.project.dao.util.SchedulerService;
import com.project.exception.rest.BadRequestException;
import com.project.exception.rest.InternalServerException;
import com.project.exception.rest.UnAuthorizedException;
import com.project.model.util.SchedulerTaskResource;
import com.project.util.ApplicationMessageEnum;
import com.project.util.Log4jUtil;
import com.test.ChartDataTwoMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author samri_g64pbd3
 */
@Controller
@RequestMapping(value = "/schedulerman")
public class SchedulerManagement_CLIENT_MVC_Controller extends Base_Controller {

    @Autowired
    SchedulerService schedulerService;

    @RequestMapping(value = "/schedule/display", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView synchLocationToGeoLocationDisplay(final RedirectAttributes redirectAttributes) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            SchedulerTaskResource taskResource = schedulerService.readSchedulerTask();

            model.addObject("taskResource", taskResource);
            model.setViewName("scheduler");
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
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }

    }

    @RequestMapping(value = "/schedule/update_schedule_status/submit", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView updateScheduleStatus(@RequestParam(value = "enabled", required = false) boolean enabled, final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            SchedulerTaskResource taskResource = schedulerService.updateSchedulerTaskStatus(enabled ? 1 : 0);
            model.addObject("pageMessage", "Update Successful.");

            model.addObject("taskResource", taskResource);
            model.setViewName("scheduler");
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
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }

    }

    @RequestMapping(value = "/schedule/parseJSONURL/submit", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView updateParseJSON(final RedirectAttributes redirectAttributes) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            SchedulerTaskResource taskResource = schedulerService.readSchedulerTask();

            ChartDataTwoMain chartDataTwoMain = schedulerService.parseJSONURL();
            model.addObject("pageError", "Fetched Successful.");
            model.addObject("pageMessage", "No of Nodes Read From API :- " + chartDataTwoMain.getNodes().size() + " ; No of Edges Read From API :- " + chartDataTwoMain.getEdges().size());

//            model.addObject("pageMessage", "Update Successful.");
            model.addObject("taskResource", taskResource);
            model.setViewName("scheduler");
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
            redirectAttributes.addFlashAttribute("pageError", ApplicationMessageEnum.ERROR_PAGE + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }

    }

}
