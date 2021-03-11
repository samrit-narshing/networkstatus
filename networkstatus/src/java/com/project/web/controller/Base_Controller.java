/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.web.controller;

import com.project.config.PropertiesConfig;
import com.project.dao.util.ApplicationLogMessageService;
import com.project.model.util.ApplicationLogMessageResource;
import com.project.util.DateConverter;
import com.project.util.Log4jUtil;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
/**
 *
 * @author SAM
 */
public class Base_Controller {

    @Autowired
    @Resource(name = "propertiesConfig")
    private PropertiesConfig propertiesConfig;

    @Autowired
    private ApplicationLogMessageService applicationLogMessageService;

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
            e.printStackTrace();
        }

    }

    public void writeLogMessage(String message) {
        try {
            // For System Log Messages
            ApplicationLogMessageResource logMessageResource = new ApplicationLogMessageResource();
            logMessageResource.setLogMessage(message.toString());
            logMessageResource.setLogMessageBlop((message).getBytes());
            logMessageResource.setEntryDate(DateConverter.getCurrentConvertedDateAndTimeInUnixDate());
            applicationLogMessageService.createApplicationLogMessage(logMessageResource);
            // End For System Log Messages
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
