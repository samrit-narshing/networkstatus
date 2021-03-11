/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.dao.util.impl;

import com.project.config.PropertiesConfig;
import org.springframework.stereotype.Service;
import com.project.dao.rest.RestResponseManagerService;
import com.project.dao.util.SchedulerService;
import com.project.exception.rest.InternalServerException;
import com.project.exception.rest.UnAuthorizedException;
import com.project.model.rest.RestResponse;
import com.project.model.util.SchedulerTaskResource;
import com.project.util.Global;
import com.test.ChartDataTwoMain;
import javax.annotation.Resource;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author samri_g64pbd3
 */
@Service("schedulerService")
public class SchedulerServiceImpl implements SchedulerService {

    @Autowired
    @Resource(name = "propertiesConfig")
    private PropertiesConfig propertiesConfig;

    @Autowired
    private RestResponseManagerService restResponseManagerService;

    @Override
    public SchedulerTaskResource readSchedulerTask() throws Exception {
        String path = "/rest/web/scheduler/find_or_create/name/" + Global.SCHEDULE_NODE_SYNC;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                SchedulerTaskResource schedulerTaskResource = mapper.readValue(responseString, SchedulerTaskResource.class);
                return schedulerTaskResource;
            case 404:
                return null;
            case 401:
                throw new UnAuthorizedException();
//            case 404:
//                throw new BadRequestException();
            case 500:
                throw new InternalServerException();
            default:
                throw new Exception("Response code : " + restResponse.getResponseCode());
        }
    }

    @Override
    public SchedulerTaskResource updateSchedulerTaskStatus(int status) throws Exception {
        String path = "/rest/web/scheduler/update_status/name/" + Global.SCHEDULE_NODE_SYNC + "/" + status;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                SchedulerTaskResource schedulerTaskResource = mapper.readValue(responseString, SchedulerTaskResource.class);
                return schedulerTaskResource;
            case 404:
                return null;
            case 401:
                throw new UnAuthorizedException();
//            case 404:
//                throw new BadRequestException();
            case 500:
                throw new InternalServerException();
            default:
                throw new Exception("Response code : " + restResponse.getResponseCode());
        }
    }

    @Override
    public ChartDataTwoMain parseJSONURL() throws Exception {
        String path = "/rest/web/scheduler/parse/jsonurl";
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                ChartDataTwoMain schedulerTaskResource = mapper.readValue(responseString, ChartDataTwoMain.class);
                return schedulerTaskResource;
            case 404:
                return null;
            case 401:
                throw new UnAuthorizedException();
//            case 404:
//                throw new BadRequestException();
            case 500:
                throw new InternalServerException();
            default:
                throw new Exception("Response code : " + restResponse.getResponseCode());
        }
    }

}
