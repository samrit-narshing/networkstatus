/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.web.controller;

import com.project.config.PropertiesConfig;
import com.project.util.Log4jUtil;
import license.LicenseManagement;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Samrit
 */
@Controller
@RequestMapping(value = "/license")
public class LicenseManagement_CLIENT_MVC_Controller extends Base_Controller {

    @Autowired
    @Resource(name = "propertiesConfig")
    private PropertiesConfig propertiesConfig;

    private String message = "<br/>\n"
            + " License Failed. Need new license key!.<br>\n"
            + " <br/>\n"
            + " Please register with the new valid license file to use this copy of SimC-Logger.<br/>\n"
            + " Please Contact Triumph Secure Singapore Pte.Ltd for the process of registration with the MAC address and Host Id of this machine.<br/>\n"
            + " <br/>\n"
            + " <b> "
            + " <b> Mac Address : </b>"
            + LicenseManagement.getAnyInterfaceMacAddressExceptBridge()
            + " </b> "
            + " <br/>\n"
            + " <b> "
            + "Host Id : "
            + LicenseManagement.getHostId()
            + " </b> "
            + " <br/>\n"
            + " <br/>\n"
            + " Thank you.\n";

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView licenseConfigDisplay(final RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");
            boolean isExpired = LicenseManagement.isExpired(request);

            String expiredMessage = "<font color=\"red\">"
                    + " Expiry Date :" + LicenseManagement.getExpireyDate(request)
                    + " <br/>\n"
                    + " Expired :" + (isExpired == true ? "YES" : "NO")
                    + " <br/>\n"
                    + message
                    + " </font>";

            model.addObject("pageMessage", expiredMessage);

            model.setViewName("licenseConfig");
            return model;
        } catch (Exception e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", "Error in page." + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

    @RequestMapping(value = "/submitUploadLicenseKey", method = RequestMethod.POST)
    public ModelAndView uploadLicenseKey(@RequestParam("file") MultipartFile file, final RedirectAttributes redirectAttributes, HttpServletResponse response, HttpServletRequest request) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");

            if (!file.isEmpty()) {
                boolean isExpired = LicenseManagement.isExpired(request);
                boolean isRegisterd = LicenseManagement.registerLicenseWithUploadedFile(file, request);
                if (isRegisterd) {
                    model.addObject("pageMessage", "File '" + file.getOriginalFilename() + "' Uploaded Successfully." + " Exiprary Date :" + LicenseManagement.getExpireyDate(request) + " Expired :" + isExpired);
                    model.setViewName("/home");
                } else {

                    String expiredMessage = "<font color=\"red\">"
                            + " Expiry Date :" + LicenseManagement.getExpireyDate(request)
                            + " <br/>\n"
                            + " Expired :" + (isExpired == true ? "YES" : "NO")
                            + " <br/>\n"
                            + message
                            + " <br/>\n"
                            + " <br/>\n"
                            + " Process cannot be completed. The provided license is already Expired or Invalid."
                            + " <br/>\n"
                            + " </font>";

                    model.addObject("pageError", expiredMessage);

                    model.setViewName("licenseConfig");
                }
                return model;
            } else {
                String expiredMessage = "<font color=\"red\">"
                        + " Expiry Date :" + LicenseManagement.getExpireyDate(request)
                        + " <br/>\n"
                        + message
                        + " <br/>\n"
                        + " <br/>\n"
                        + "Process cannot be completed. Invalid File Being Uploaded."
                        + " <br/>\n"
                        + " </font>";

                model.addObject("pageError", expiredMessage);
                model.setViewName("licenseConfig");
            }

            return model;

        } catch (Exception e) {
            writeLogMessage(e);
            Log4jUtil.fatal(e);
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("pageError", "Error in page." + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

}
