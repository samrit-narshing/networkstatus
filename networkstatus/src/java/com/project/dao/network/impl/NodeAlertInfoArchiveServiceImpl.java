/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.dao.network.impl;

import com.project.config.PropertiesConfig;
import com.project.dao.network.NodeAlertInfoArchiveService;
import com.project.dao.rest.RestResponseManagerService;
import com.project.exception.rest.BadRequestException;
import com.project.exception.rest.InternalServerException;
import com.project.exception.rest.UnAuthorizedException;
import com.project.model.network.NodeAlertInfoArchiveListResource;
import com.project.model.network.NodeAlertInfoArchiveResource;
import com.project.model.rest.RestResponse;
import com.project.util.searchcriteria.NodeAlertInfoArchiveSearchCriteria;
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
@Service("nodeAlertInfoArchiveService")
public class NodeAlertInfoArchiveServiceImpl implements NodeAlertInfoArchiveService {

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
    public NodeAlertInfoArchiveResource createNodeAlertInfoArchive(NodeAlertInfoArchiveResource data) throws Exception {
        String rolesListPath = "/rest/web/nodealertinfoarchive/create";
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(data);
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(rolesListPath, jsonInString);

        switch (restResponse.getResponseCode()) {
            case 200:
            case 201: {
                String responseString = restResponse.getResponseString();
                NodeAlertInfoArchiveResource responseUserResource = mapper.readValue(responseString, NodeAlertInfoArchiveResource.class);
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
    public NodeAlertInfoArchiveListResource createNodeAlertInfoArchives(NodeAlertInfoArchiveListResource data) throws Exception {
        String rolesListPath = "/rest/web/nodealertinfoarchive/create_in_bluck";

        for (NodeAlertInfoArchiveResource resource : data.getNodeAlertInfoArchiveResources()) {

        }

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(data);
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(rolesListPath, jsonInString);

        switch (restResponse.getResponseCode()) {
            case 200:
            case 201: {
                String responseString = restResponse.getResponseString();
                NodeAlertInfoArchiveListResource responseUserResource = mapper.readValue(responseString, NodeAlertInfoArchiveListResource.class);
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
    public NodeAlertInfoArchiveResource findNodeAlertInfoArchive(Long id) throws Exception {
        String path = "/rest/web/nodealertinfoarchive/find/" + id;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NodeAlertInfoArchiveResource resource = mapper.readValue(responseString, NodeAlertInfoArchiveResource.class);
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
    public NodeAlertInfoArchiveListResource findNodeAlertInfoArchiveListBySearchCriteria(NodeAlertInfoArchiveSearchCriteria searchCriteria) throws Exception {

//        testingCrosssite();
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(searchCriteria);
        String path = "/rest/web/nodealertinfoarchive/search";
        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);

        switch (restResponse.getResponseCode()) {
            case 200:
            case 201: {
                String responseString = restResponse.getResponseString();
                NodeAlertInfoArchiveListResource listResource = mapper.readValue(responseString, NodeAlertInfoArchiveListResource.class);
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
    public NodeAlertInfoArchiveResource deleteNodeAlertInfoArchive(Long id) throws Exception {
        String path = "/rest/web/nodealertinfoarchive/delete/" + id;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NodeAlertInfoArchiveResource userResource = mapper.readValue(responseString, NodeAlertInfoArchiveResource.class);
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
    public int deleteAllNodeAlertInfoArchive() throws Exception {
        String path = "/rest/web/nodealertinfoarchive/delete/all";
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
