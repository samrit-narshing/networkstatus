/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.dao.user.impl;

import com.project.config.PropertiesConfig;
import com.project.dao.rest.RestResponseManagerService;
import com.project.dao.user.RoleService;
import com.project.exception.rest.BadRequestException;
import com.project.exception.rest.InternalServerException;
import com.project.exception.rest.UnAuthorizedException;
import com.project.model.rest.RestResponse;
import com.project.model.user.RoleResource;
import com.project.model.user.RoletListResource;
import com.project.util.searchcriteria.RoleSearchCriteria;
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
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    JdbcTemplate jdbcTemplate;
    private String query = null;

    @Autowired
    @Resource(name = "propertiesConfig")
    private PropertiesConfig propertiesConfig;

    @Autowired
    private RestResponseManagerService restResponseManagerService;

    private final String UNIQUE_WEBSERVICE_APPLICATION_ID = "LALITPUR@MANGALBAZAR#5536666";

    @Override
    public RoleResource createRole(RoleResource roleResource) throws Exception {
        String rolesListPath = "/rest/web/role/create";
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(roleResource);
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(rolesListPath, jsonInString);

        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                RoleResource responseRoleResource = mapper.readValue(responseString, RoleResource.class);
                return responseRoleResource;
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
    public RoleResource readRolesByID(Long id) throws Exception {
        String path = "/rest/web/role/find/" + id;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);

        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                RoleResource roleResource = mapper.readValue(responseString, RoleResource.class);
                return roleResource;
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
    public RoletListResource readRolesBySearchCriteria(RoleSearchCriteria searchCriteria) throws Exception {

//        testingCrosssite();
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(searchCriteria);
        String path = "/rest/web/role/list";
        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                RoletListResource roletListResource = mapper.readValue(responseString, RoletListResource.class);
                return roletListResource;
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
    public RoleResource updateRole(Long id, RoleResource roleResource) throws Exception {
        String path = "/rest/web/role/update/" + id;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(roleResource);
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                RoleResource responseRoleResource = mapper.readValue(responseString, RoleResource.class);
                return responseRoleResource;
            case 404:
                return null;
            case 401:
                throw new UnAuthorizedException();
            case 500:
                throw new InternalServerException();
            default:
                throw new Exception("Response code : " + restResponse.getResponseCode());
        }
    }

    @Override
    public RoleResource updateRoleEnabledStatus(Long id) throws Exception {
        String path = "/rest/web/role/update/changeenabledstatus/" + id;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                RoleResource responseResource = mapper.readValue(responseString, RoleResource.class);
                return responseResource;
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
    public RoleResource readRoleByName(String roleName) throws Exception {
        String path = "/rest/web/role/find/name/" + roleName;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        System.out.println("cccccccccccccccc" + restResponse);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                RoleResource roleResource = mapper.readValue(responseString, RoleResource.class);
                return roleResource;
            case 404:
                return null;
            case 401:
                throw new UnAuthorizedException();
            case 500:
                throw new InternalServerException();
            default:
                throw new Exception("Response code : " + restResponse.getResponseCode());
        }
    }

}
