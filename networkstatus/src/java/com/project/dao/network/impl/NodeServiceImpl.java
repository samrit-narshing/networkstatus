/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.dao.network.impl;

import com.project.config.PropertiesConfig;
import com.project.dao.rest.RestResponseManagerService;
import com.project.dao.network.NodeService;
import com.project.exception.rest.BadRequestException;
import com.project.exception.rest.InternalServerException;
import com.project.exception.rest.UnAuthorizedException;
import com.project.model.network.NodeAndEdgeListResource;
import com.project.model.rest.RestResponse;
import com.project.model.network.NodeListResource;
import com.project.model.network.NodeResource;
import com.project.model.util.FileInfo;
import com.project.model.util.UploadedFileContent;
import com.project.util.searchcriteria.NodeSearchCriteria;
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
@Service("nodeService")
public class NodeServiceImpl implements NodeService {

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
    public NodeResource createNode(NodeResource nodeResource) throws Exception {
        String rolesListPath = "/rest/web/network/node/create";
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(nodeResource);
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(rolesListPath, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NodeResource responseProfessorResource = mapper.readValue(responseString, NodeResource.class);
                return responseProfessorResource;
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
    public NodeResource readNodeByID(Long id) throws Exception {
        String path = "/rest/web/network/node/find/" + id;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NodeResource userResource = mapper.readValue(responseString, NodeResource.class);
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
    public NodeResource readNodeByLabel(String label) throws Exception {
        String path = "/rest/web/network/node/find/label/" + label;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        System.out.println("cccccccccccccccc" + restResponse);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NodeResource userResource = mapper.readValue(responseString, NodeResource.class);
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
    public NodeListResource readNodesBySearchCriteria(NodeSearchCriteria searchCriteria) throws Exception {

//        testingCrosssite();
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(searchCriteria);
        String path = "/rest/web/network/node/list";
        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);

        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NodeListResource nodeListResource = mapper.readValue(responseString, NodeListResource.class);
                return nodeListResource;
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
    public NodeListResource readAllNodes() throws Exception {

//        testingCrosssite();
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        String path = "/rest/web/network/node/listall";
        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);

        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NodeListResource nodeListResource = mapper.readValue(responseString, NodeListResource.class);
                return nodeListResource;
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
    public NodeListResource readAllEnabledNodes() throws Exception {

//        testingCrosssite();
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        String path = "/rest/web/network/node/listallenabled";
        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);

        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NodeListResource nodeListResource = mapper.readValue(responseString, NodeListResource.class);
                return nodeListResource;
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
    public NodeListResource readAllEnabledNodesExculdingNodesInNetworkGroup(Long networkGroupId) throws Exception {

//        testingCrosssite();/listallenabled/networkgroup/{networkGroupId}
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        String path = "/rest/web/network/node/listallenabled/networkgroup/" + networkGroupId;
        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);

        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NodeListResource nodeListResource = mapper.readValue(responseString, NodeListResource.class);
                return nodeListResource;
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
    public NodeResource updateNode(Long id, NodeResource userResource) throws Exception {
        String path = "/rest/web/network/node/update/" + id;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(userResource);
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NodeResource responseProfessorResource = mapper.readValue(responseString, NodeResource.class);
                return responseProfessorResource;
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
    public NodeResource updateNodeEnabledStatus(Long id) throws Exception {
        String path = "/rest/web/network/node/update/changeenabledstatus/" + id;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NodeResource responseProfessorResource = mapper.readValue(responseString, NodeResource.class);
                return responseProfessorResource;
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
    public NodeResource deleteNodeByID(Long id) throws Exception {
        String path = "/rest/web/network/node/delete/" + id;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NodeResource professorResource = mapper.readValue(responseString, NodeResource.class);
                return professorResource;
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
    public NodeResource updateNodeAlert(Long id, NodeResource nodeResource) throws Exception {
        String path = "/rest/web/network/node/update_alert/" + id;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(nodeResource);
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NodeResource responseProfessorResource = mapper.readValue(responseString, NodeResource.class);
                return responseProfessorResource;
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
    public NodeResource updateNodeAlertAsReset(Long id) throws Exception {
        String path = "/rest/web/network/node/update_alert/reset/" + id;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NodeResource responseProfessorResource = mapper.readValue(responseString, NodeResource.class);
                return responseProfessorResource;
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
    public NodeResource updateNodeAlertToAddNodeAlertInfo(Long id, NodeResource nodeResource) throws Exception {
        String path = "/rest/web/network/node/update_alert/alert_info/add/" + id;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(nodeResource);
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NodeResource responseProfessorResource = mapper.readValue(responseString, NodeResource.class);
                return responseProfessorResource;
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
    public NodeResource updateNodeCordinates(NodeListResource nodeListResource) throws Exception {
        String path = "/rest/web/network/node/update/cordinates";
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(nodeListResource);
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NodeResource responseProfessorResource = mapper.readValue(responseString, NodeResource.class);
                return responseProfessorResource;
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
    public FileInfo uploadCSVFileToImportNodesByObject(UploadedFileContent uploadedFileContent) throws Exception {
        String rolesListPath = "/rest/web/filehandler/upload/csv/node/user";
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(uploadedFileContent);
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(rolesListPath, jsonInString);

        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                FileInfo fileInfo = mapper.readValue(responseString, FileInfo.class);
                return fileInfo;
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
    public NodeListResource readAllCSVUploadedNodes() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        String path = "/rest/web/network/node/uploaded/csv/list";
        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);

        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NodeListResource nodeListResource = mapper.readValue(responseString, NodeListResource.class);
                return nodeListResource;
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
    public NodeListResource createAndUpdateNodes(NodeListResource toSentNodeListResource) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(toSentNodeListResource);
        String path = "/rest/web/network/node/uploaded/csv/nodes/add_update";
        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);

        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NodeListResource nodeListResource = mapper.readValue(responseString, NodeListResource.class);
                return nodeListResource;
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
    public NodeAndEdgeListResource createAndUpdateNodesAndEdges(NodeAndEdgeListResource toSentNodeAndEdgeListResource) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(toSentNodeAndEdgeListResource);
        String path = "/rest/web/network/node/uploaded/csv/nodes_edges/add_update";
        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);

        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NodeAndEdgeListResource nodeAndEdgeListResource = mapper.readValue(responseString, NodeAndEdgeListResource.class);
                return nodeAndEdgeListResource;
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
