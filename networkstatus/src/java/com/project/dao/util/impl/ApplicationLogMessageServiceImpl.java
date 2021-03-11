/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.dao.util.impl;

import com.project.config.PropertiesConfig;
import com.project.dao.rest.RestResponseManagerService;
import com.project.dao.util.ApplicationLogMessageService;
import com.project.exception.rest.BadRequestException;
import com.project.exception.rest.InternalServerException;
import com.project.exception.rest.UnAuthorizedException;
import com.project.model.rest.RestResponse;
import com.project.model.util.ApplicationLogMessageListResource;
import com.project.model.util.ApplicationLogMessageResource;
import com.project.util.OperatingSystem;
import com.project.util.searchcriteria.ApplicationLogMessageSearchCriteria;
import javax.annotation.Resource;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author SAM
 */
//@Component
@Service("applicationLogMessageService")
public class ApplicationLogMessageServiceImpl implements ApplicationLogMessageService {

    @Autowired
    JdbcTemplate jdbcTemplate;
    private String query = null;

    @Autowired
    @Resource(name = "propertiesConfig")
    private PropertiesConfig propertiesConfig;

    @Autowired
    private RestResponseManagerService restResponseManagerService;

    private final String UNIQUE_WEBSERVICE_APPLICATION_ID = "LALITPUR@MANGALBAZAR#5536666";

    //consung*******************************************************
    @Override
    public ApplicationLogMessageResource createApplicationLogMessage(ApplicationLogMessageResource data) throws Exception {
        String rolesListPath = "/rest/web/application_log_message/create";
        data.setDeviceType("PC");
        data.setDeviceModelName(OperatingSystem.getOsName() + " " + OperatingSystem.getOsVersion() + " " + OperatingSystem.getOsArchitecture());
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(data);
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(rolesListPath, jsonInString);

        switch (restResponse.getResponseCode()) {
            case 200:
            case 201: {
                String responseString = restResponse.getResponseString();
                ApplicationLogMessageResource responseUserResource = mapper.readValue(responseString, ApplicationLogMessageResource.class);
                return responseUserResource;
            }
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
    public ApplicationLogMessageListResource createApplicationLogMessages(ApplicationLogMessageListResource data) throws Exception {
        String rolesListPath = "/rest/web/application_log_message/create_in_bluck";

        for (ApplicationLogMessageResource resource : data.getApplicationLogMessageResources()) {
            resource.setDeviceType("PC");
            resource.setDeviceModelName(OperatingSystem.getOsName() + " " + OperatingSystem.getOsVersion() + " " + OperatingSystem.getOsArchitecture());
        }

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(data);
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(rolesListPath, jsonInString);

        switch (restResponse.getResponseCode()) {
            case 200:
            case 201: {
                String responseString = restResponse.getResponseString();
                ApplicationLogMessageListResource responseUserResource = mapper.readValue(responseString, ApplicationLogMessageListResource.class);
                return responseUserResource;
            }
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
    public ApplicationLogMessageResource findApplicationLogMessage(Long id) throws Exception {
        String path = "/rest/web/application_log_message/find/" + id;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                ApplicationLogMessageResource resource = mapper.readValue(responseString, ApplicationLogMessageResource.class);
                return resource;
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
    public ApplicationLogMessageListResource findApplicationLogMessageListBySearchCriteria(ApplicationLogMessageSearchCriteria searchCriteria) throws Exception {

//        testingCrosssite();
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(searchCriteria);
        String path = "/rest/web/application_log_message/search";
        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);

        switch (restResponse.getResponseCode()) {
            case 200:
            case 201: {
                String responseString = restResponse.getResponseString();
                ApplicationLogMessageListResource listResource = mapper.readValue(responseString, ApplicationLogMessageListResource.class);
                return listResource;
            }
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
    public ApplicationLogMessageResource deleteApplicationLogMessage(Long id) throws Exception {
        String path = "/rest/web/application_log_message/delete/" + id;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                ApplicationLogMessageResource userResource = mapper.readValue(responseString, ApplicationLogMessageResource.class);
                return userResource;
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
    public int deleteAllApplicationLogMessage() throws Exception {
        String path = "/rest/web/application_log_message/delete/all";
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

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
