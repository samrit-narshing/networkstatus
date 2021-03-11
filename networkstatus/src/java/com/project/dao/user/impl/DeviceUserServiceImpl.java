/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.dao.user.impl;

import com.project.config.PropertiesConfig;
import com.project.dao.rest.RestResponseManagerService;
import com.project.dao.user.DeviceUserService;
import com.project.exception.rest.BadRequestException;
import com.project.exception.rest.InternalServerException;
import com.project.exception.rest.UnAuthorizedException;
import com.project.model.rest.RestResponse;
import com.project.model.user.DeviceUserResource;
import com.project.model.user.DeviceUsersListResource;
import com.project.util.searchcriteria.DeviceUserSearchCriteria;
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
@Service("deviceUserService")
public class DeviceUserServiceImpl implements DeviceUserService {

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
    public DeviceUserResource createOrUpdateDeviceUser(DeviceUserResource data) throws Exception {
        String rolesListPath = "/rest/web/device_user/create_or_update";
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(data);
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(rolesListPath, jsonInString);

        switch (restResponse.getResponseCode()) {
            case 200:
            case 201: {
                String responseString = restResponse.getResponseString();
                DeviceUserResource responseUserResource = mapper.readValue(responseString, DeviceUserResource.class);
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
    public DeviceUserResource findDeviceUser(Long id) throws Exception {
        String path = "/rest/web/device_user/find/" + id;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                DeviceUserResource resource = mapper.readValue(responseString, DeviceUserResource.class);
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
    public DeviceUserResource findDeviceUserByUsername(String username) throws Exception {
        String path = "/rest/web/device_user/find/username/" + username;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        System.out.println("cccccccccccccccc" + restResponse);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                DeviceUserResource resource = mapper.readValue(responseString, DeviceUserResource.class);
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
    public DeviceUsersListResource findDeviceUsersBySearchCriteria(DeviceUserSearchCriteria searchCriteria) throws Exception {

//        testingCrosssite();
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(searchCriteria);
        String path = "/rest/web/device_user/list";
        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);

        switch (restResponse.getResponseCode()) {
            case 200:
            case 201: {
                String responseString = restResponse.getResponseString();
                DeviceUsersListResource listResource = mapper.readValue(responseString, DeviceUsersListResource.class);
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
    public DeviceUserResource deleteDeviceUser(Long id) throws Exception {
        String path = "/rest/web/device_user/delete/" + id;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                DeviceUserResource userResource = mapper.readValue(responseString, DeviceUserResource.class);
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
    public int deleteDeviceUserByUsername(String username) throws Exception {
        String path = "/rest/web/device_user/delete/username/{username}";
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
    
    
     @Override
    public int deleteAllDeviceUserAndSignOut() throws Exception {
        String path = "/rest/web/device_user/delete_sigout/all_users";
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
