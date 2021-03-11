/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.dao.system.impl;

import com.project.config.PropertiesConfig;
import com.project.dao.rest.RestResponseManagerService;
import com.project.dao.system.ControlPanelConfigService;
import com.project.exception.rest.BadRequestException;
import com.project.exception.rest.InternalServerException;
import com.project.exception.rest.UnAuthorizedException;
import com.project.model.rest.RestResponse;
import com.project.model.system.ControlPanel;
import javax.annotation.Resource;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Samrit
 */
@Service("controlPanelConfigService")
public class ControlPanelConfigImpl implements ControlPanelConfigService {

    @Autowired
    @Resource(name = "propertiesConfig")
    private PropertiesConfig propertiesConfig;

    @Autowired
    private RestResponseManagerService restResponseManagerService;

    @Override
    public int callGarbageCollector() {
        System.out.println("Calling JVM Garbage Collector .");
        Runtime.getRuntime().gc();
        System.gc();
        System.runFinalization();
        return 1;
    }

    @Override
    public ControlPanel readControlPanel() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        String path = "/rest/web/info/control_panel/unsecure/read";
        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);

        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                ControlPanel controlPanel = mapper.readValue(responseString, ControlPanel.class);
                return controlPanel;
            case 401:
                throw new UnAuthorizedException();
            case 404:
                throw new BadRequestException();
            case 500:
                throw new InternalServerException();
            default:
                throw new Exception("Response code : " + restResponse.getResponseCode());
        }
    }

    @Override
    public int callGarbageCollectorForWebServer() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        String path = "/rest/web/info/control_panel/unsecure/call_garbagecollection";
        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                return 1;
            case 401:
                throw new UnAuthorizedException();
            case 404:
                throw new BadRequestException();
            case 500:
                throw new InternalServerException();
            default:
                throw new Exception("Response code : " + restResponse.getResponseCode());
        }
    }

    @Override
    public int resetAllDataFromWebServer() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        String path = "/rest/web/info/control_panel/secure/reset/data/all";
        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                return 1;
            case 401:
                throw new UnAuthorizedException();
            case 404:
                throw new BadRequestException();
            case 500:
                throw new InternalServerException();
            default:
                throw new Exception("Response code : " + restResponse.getResponseCode());
        }
    }

}
