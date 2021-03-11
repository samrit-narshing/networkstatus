/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.dao.network.impl;

import com.project.config.PropertiesConfig;
import com.project.dao.network.NetworkGroupService;
import org.springframework.stereotype.Service;
import com.project.dao.rest.RestResponseManagerService;
import com.project.exception.rest.BadRequestException;
import com.project.exception.rest.InternalServerException;
import com.project.exception.rest.ResourceInUsedException;
import com.project.exception.rest.UnAuthorizedException;
import com.project.model.network.EdgeInNetworkGroupListResource;
import com.project.model.network.EdgeInNetworkGroupResource;
import com.project.model.network.NetworkGroupListResource;
import com.project.model.network.NetworkGroupResource;
import com.project.model.network.NodeAndEdgeListInNetworkGroupResource;
import com.project.model.network.NodeInNetworkGroupListResource;
import com.project.model.network.NodeInNetworkGroupResource;
import com.project.model.rest.RestResponse;
import com.project.util.searchcriteria.NetworkGroupSearchCriteria;

import javax.annotation.Resource;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Samrit
 */
@Service("networkGroupService")
public class NetworkGroupServiceImpl implements NetworkGroupService {

    @Autowired
    @Resource(name = "propertiesConfig")
    private PropertiesConfig propertiesConfig;

    @Autowired
    private RestResponseManagerService restResponseManagerService;

    @Override
    public NetworkGroupResource createNetworkGroup(NetworkGroupResource data) throws Exception {
        String resourcePath = "/rest/web/networkgroup/create";
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(data);
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(resourcePath, jsonInString);

        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NetworkGroupResource responseResource = mapper.readValue(responseString, NetworkGroupResource.class);
                return responseResource;
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
    public NetworkGroupResource readNetworkGroupResourceByCode(String code) throws Exception {
        String path = "/rest/web/networkgroup/find/code/" + code;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        System.out.println("cccccccccccccccc" + restResponse);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NetworkGroupResource responseResource = mapper.readValue(responseString, NetworkGroupResource.class);
                return responseResource;
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
    public NetworkGroupListResource readNetworkGroupResourceBySearchCriteria(NetworkGroupSearchCriteria data) throws Exception {
        String resourcePath = "/rest/web/networkgroup/list";
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(data);
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(resourcePath, jsonInString);

        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NetworkGroupListResource responseResource = mapper.readValue(responseString, NetworkGroupListResource.class);
                return responseResource;
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
    public NetworkGroupResource readNetworkGroupByID(Long id) throws Exception {
        String path = "/rest/web/networkgroup/find/" + id;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NetworkGroupResource userResource = mapper.readValue(responseString, NetworkGroupResource.class);
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
    public NetworkGroupResource deleteNetworkGroupByID(Long id) throws Exception {
        String path = "/rest/web/networkgroup/delete/" + id;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NetworkGroupResource networkGroupResource = mapper.readValue(responseString, NetworkGroupResource.class);
                return networkGroupResource;
            case 226:
                throw new ResourceInUsedException();
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
    public NetworkGroupResource updateNetworkGroup(Long id, NetworkGroupResource networkGroupResource) throws Exception {
        String path = "/rest/web/networkgroup/update/" + id;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(networkGroupResource);
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NetworkGroupResource responseParentrResource = mapper.readValue(responseString, NetworkGroupResource.class);
                return responseParentrResource;
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
    public NetworkGroupResource addAndUpdateNodes(Long networkGroupID, NodeInNetworkGroupListResource nodeInNetworkGroupListResource) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(nodeInNetworkGroupListResource);
        String path = "/rest/web/networkgroup/update_new/" + networkGroupID + "/add/nodes";
        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);

        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NetworkGroupResource nodeListResource = mapper.readValue(responseString, NetworkGroupResource.class);
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
    public NetworkGroupResource addAndUpdateNodesAndEdges(Long networkGroupID, NodeAndEdgeListInNetworkGroupResource nodeAndEdgeListInNetworkGroupResource) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(nodeAndEdgeListInNetworkGroupResource);
        String path = "/rest/web/networkgroup/update_new/" + networkGroupID + "/add/nodes_edges";
        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);

        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NetworkGroupResource nodeAndEdgeListResource = mapper.readValue(responseString, NetworkGroupResource.class);
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

    @Override
    public NetworkGroupResource deleteNodesInNetworkGroup(Long networkGroupID, NodeInNetworkGroupListResource nodeInNetworkGroupListResource) throws Exception {

//        String path = "/rest/web/networkgroup/update_new/" + networkGroupID + "/add/nodes_edges";
        String path = "/rest/web/networkgroup/update_new/" + networkGroupID + "/remove/nodes";

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(nodeInNetworkGroupListResource);
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NetworkGroupResource networkGroupResource = mapper.readValue(responseString, NetworkGroupResource.class);
                return networkGroupResource;
            case 226:
                throw new ResourceInUsedException();
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
    public NetworkGroupResource addAndUpdateEdges(Long networkGroupID, EdgeInNetworkGroupListResource edgeInNetworkGroupListResource) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(edgeInNetworkGroupListResource);
        String path = "/rest/web/networkgroup/update_new/" + networkGroupID + "/add/edges";
        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);

        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NetworkGroupResource nodeListResource = mapper.readValue(responseString, NetworkGroupResource.class);
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
    public NetworkGroupResource deleteEdgesInNetworkGroup(Long networkGroupID, EdgeInNetworkGroupListResource edgeInNetworkGroupListResource) throws Exception {

//        String path = "/rest/web/networkgroup/update_new/" + networkGroupID + "/add/nodes_edges";
        String path = "/rest/web/networkgroup/update_new/" + networkGroupID + "/remove/edges";

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(edgeInNetworkGroupListResource);
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NetworkGroupResource networkGroupResource = mapper.readValue(responseString, NetworkGroupResource.class);
                return networkGroupResource;
            case 226:
                throw new ResourceInUsedException();
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
    public NetworkGroupResource updateEdgesInNetworkGroup(Long networkGroupID, EdgeInNetworkGroupListResource edgeInNetworkGroupListResource) throws Exception {

//        String path = "/rest/web/networkgroup/update_new/" + networkGroupID + "/add/nodes_edges";
        String path = "/rest/web/networkgroup/update_new/" + networkGroupID + "/update/edges";

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(edgeInNetworkGroupListResource);
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NetworkGroupResource networkGroupResource = mapper.readValue(responseString, NetworkGroupResource.class);
                return networkGroupResource;
            case 226:
                throw new ResourceInUsedException();
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
    public EdgeInNetworkGroupResource readEdgeInNetworkGroupResourceByID(Long id) throws Exception {
        String path = "/rest/web/networkgroup/edgeinnetworkgroup/find/" + id;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                EdgeInNetworkGroupResource resource = mapper.readValue(responseString, EdgeInNetworkGroupResource.class);
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
    public NodeInNetworkGroupResource readNodeInNetworkGroupResourceByID(Long nodeInNetworkGroupId) throws Exception {
        String path = "/rest/web/networkgroup/nodeinnetworkgroup/find/" + nodeInNetworkGroupId;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NodeInNetworkGroupResource resource = mapper.readValue(responseString, NodeInNetworkGroupResource.class);
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
    public NetworkGroupResource readNetworkGroupByNodeInNetworkGroupResourceId(Long nodeInNetworkGroupId) throws Exception {

        String path = "/rest/web/networkgroup/nodeinnetworkgroup/find/" + nodeInNetworkGroupId + "/networkgroup";
//        String path = "/rest/web/networkgroup/update_new/" + networkGroupID + "/update/edges";

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NetworkGroupResource networkGroupResource = mapper.readValue(responseString, NetworkGroupResource.class);
                return networkGroupResource;
            case 226:
                throw new ResourceInUsedException();
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

///find/{id}/node_id_1/{nodeId1}/node_id_2/{nodeId2}
    @Override
    public NetworkGroupResource readNetworkGroupIfNodesAreAlreadyExists(Long networkGroupID, Long nodeInNetworkGroupId1, Long nodeInNetworkGroupId2) throws Exception {
        String path = "/rest/web/networkgroup/find/" + networkGroupID + "/nodeinnetworkgroupid_1/" + nodeInNetworkGroupId1 + "/nodeinnetworkgroupid_2/" + nodeInNetworkGroupId2;
//        String path = "/rest/web/network/edge/find/nodeid1/" + nodeID1 + "/nodeid2/" + nodeID2;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        System.out.println("cccccccccccccccc" + restResponse);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NetworkGroupResource userResource = mapper.readValue(responseString, NetworkGroupResource.class);
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
    public NetworkGroupResource updateNodeInNetworkGroupResourcesForCordinates(Long networkGroupId, NodeInNetworkGroupListResource nodeInNetworkGroupListResource) throws Exception {

        String path = "/rest/web/networkgroup/update/" + networkGroupId + "/nodeinnetworkgroups/cordinates";
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(nodeInNetworkGroupListResource);
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NetworkGroupResource responseProfessorResource = mapper.readValue(responseString, NetworkGroupResource.class);
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
    public NetworkGroupResource clearAndUpdateNetworkGroupForAddEdges_NEW(Long networkGroupId) throws Exception {
        String path = "/rest/web/networkgroup/update/" + networkGroupId + "/add/edges_from_parent";
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                NetworkGroupResource responseProfessorResource = mapper.readValue(responseString, NetworkGroupResource.class);
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

//    @Override
//    public NetworkGroupListResource readNetworkGroupResourceForUser() throws Exception {
//        String resourcePath = "/rest/web/networkgroup/listallforuser";
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = mapper.writeValueAsString("");
//        System.out.println(jsonInString);
//
//        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(resourcePath, jsonInString);
//
//        switch (restResponse.getResponseCode()) {
//            case 200:
//            case 201:
//                String responseString = restResponse.getResponseString();
//                NetworkGroupListResource responseResource = mapper.readValue(responseString, NetworkGroupListResource.class);
//                return responseResource;
//            case 401:
//                throw new UnAuthorizedException();
//            case 404:
//                throw new BadRequestException();
//            case 500:
//                throw new InternalServerException();
//            default:
//                throw new Exception("Response code : " + restResponse.getResponseCode());
//        }
//    }
//
//    @Override
//    public NetworkGroupResource updateNetworkGroupForAddNodes(Long id, NodeInNetworkGroupResource networkGroupResource) throws Exception {
//        String path = "/rest/web/networkgroup/update/" + id + "/add/nodeId/" + networkGroupResource.getNodeResource().getUserID();
//
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = mapper.writeValueAsString(networkGroupResource);
//        System.out.println(jsonInString);
//        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
//        switch (restResponse.getResponseCode()) {
//            case 200:
//            case 201:
//                String responseString = restResponse.getResponseString();
//                NetworkGroupResource responseParentrResource = mapper.readValue(responseString, NetworkGroupResource.class);
//                return responseParentrResource;
//            case 404:
//                return null;
//            case 401:
//                throw new UnAuthorizedException();
////            case 404:
////                throw new BadRequestException();
//            case 500:
//                throw new InternalServerException();
//            default:
//                throw new Exception("Response code : " + restResponse.getResponseCode());
//        }
//    }
//
//    @Override
//    public NetworkGroupResource deleteNodeInNetworkGroupByID(Long id, Long nodeInNetworkGroupID) throws Exception {
//
//        String path = "/rest/web/networkgroup/update/" + id + "/remove/nodenetworkgroupid/" + nodeInNetworkGroupID;
//
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = mapper.writeValueAsString("");
//        System.out.println(jsonInString);
//
//        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
//        switch (restResponse.getResponseCode()) {
//            case 200:
//            case 201:
//                String responseString = restResponse.getResponseString();
//                NetworkGroupResource networkGroupResource = mapper.readValue(responseString, NetworkGroupResource.class);
//                return networkGroupResource;
//            case 226:
//                throw new ResourceInUsedException();
//            case 404:
//                return null;
//            case 401:
//                throw new UnAuthorizedException();
////            case 404:
////                throw new BadRequestException();
//            case 500:
//                throw new InternalServerException();
//            default:
//                throw new Exception("Response code : " + restResponse.getResponseCode());
//        }
//    }
//
//    @Override
//    public NetworkGroupResource updateNetworkGroupForEditNodes(Long id, NodeInNetworkGroupResource networkGroupResource) throws Exception {
//        String path = "/rest/web/networkgroup/update/" + id + "/update/nodenetworkgroupid/" + networkGroupResource.getNodeInNetworkGroupID();
//
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = mapper.writeValueAsString(networkGroupResource);
//        System.out.println(jsonInString);
//        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
//        switch (restResponse.getResponseCode()) {
//            case 200:
//            case 201:
//                String responseString = restResponse.getResponseString();
//                NetworkGroupResource responseParentrResource = mapper.readValue(responseString, NetworkGroupResource.class);
//                return responseParentrResource;
//            case 404:
//                return null;
//            case 401:
//                throw new UnAuthorizedException();
////            case 404:
////                throw new BadRequestException();
//            case 500:
//                throw new InternalServerException();
//            default:
//                throw new Exception("Response code : " + restResponse.getResponseCode());
//        }
//    }
//
//    @Override
//    public NetworkGroupResource updateNetworkGroupForAddGeoLocation(Long id, GeoLocationInNetworkGroupResource geoLocationInNetworkGroupResource) throws Exception {
//        String path = "/rest/web/networkgroup/update/" + id + "/add/geolocationid/" + geoLocationInNetworkGroupResource.getGeoLocationResource().getTableId();
//
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = mapper.writeValueAsString(geoLocationInNetworkGroupResource);
//        System.out.println(jsonInString);
//        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
//        switch (restResponse.getResponseCode()) {
//            case 200:
//            case 201:
//                String responseString = restResponse.getResponseString();
//                NetworkGroupResource responseParentrResource = mapper.readValue(responseString, NetworkGroupResource.class);
//                return responseParentrResource;
//            case 404:
//                return null;
//            case 401:
//                throw new UnAuthorizedException();
////            case 404:
////                throw new BadRequestException();
//            case 500:
//                throw new InternalServerException();
//            default:
//                throw new Exception("Response code : " + restResponse.getResponseCode());
//        }
//    }
//
//    @Override
//    public NetworkGroupResource deleteGeoLocationInNetworkGroupByID(Long id, Long nodeInNetworkGroupID) throws Exception {
//
//        String path = "/rest/web/networkgroup/update/" + id + "/remove/geolocationnetworkgroupid/" + nodeInNetworkGroupID;
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = mapper.writeValueAsString("");
//        System.out.println(jsonInString);
//
//        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
//        switch (restResponse.getResponseCode()) {
//            case 200:
//            case 201:
//                String responseString = restResponse.getResponseString();
//                NetworkGroupResource networkGroupResource = mapper.readValue(responseString, NetworkGroupResource.class);
//                return networkGroupResource;
//            case 226:
//                throw new ResourceInUsedException();
//            case 404:
//                return null;
//            case 401:
//                throw new UnAuthorizedException();
////            case 404:
////                throw new BadRequestException();
//            case 500:
//                throw new InternalServerException();
//            default:
//                throw new Exception("Response code : " + restResponse.getResponseCode());
//        }
//    }
//
//    @Override
//    public NetworkGroupResource updateNetworkGroupForEditGeoLocation(Long id, GeoLocationInNetworkGroupResource networkGroupResource) throws Exception {
//        String path = "/rest/web/networkgroup/update/" + id + "/update/geolocationnetworkgroupid/" + networkGroupResource.getGeoLocationInNetworkGroupID();
//
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = mapper.writeValueAsString(networkGroupResource);
//        System.out.println(jsonInString);
//        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
//        switch (restResponse.getResponseCode()) {
//            case 200:
//            case 201:
//                String responseString = restResponse.getResponseString();
//                NetworkGroupResource responseParentrResource = mapper.readValue(responseString, NetworkGroupResource.class);
//                return responseParentrResource;
//            case 404:
//                return null;
//            case 401:
//                throw new UnAuthorizedException();
////            case 404:
////                throw new BadRequestException();
//            case 500:
//                throw new InternalServerException();
//            default:
//                throw new Exception("Response code : " + restResponse.getResponseCode());
//        }
//    }
//
//    @Override
//    public NetworkGroupResource updateNetworkGroupForAddTravelGuide(Long id, TravelGuideInNetworkGroupResource travelGuideInNetworkGroupResource) throws Exception {
//        String path = "/rest/web/networkgroup/update/" + id + "/add/edgeid/" + travelGuideInNetworkGroupResource.getTravelGuideResource().getUserID();
//
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = mapper.writeValueAsString(travelGuideInNetworkGroupResource);
//        System.out.println(jsonInString);
//        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
//        switch (restResponse.getResponseCode()) {
//            case 200:
//            case 201:
//                String responseString = restResponse.getResponseString();
//                NetworkGroupResource responseParentrResource = mapper.readValue(responseString, NetworkGroupResource.class);
//                return responseParentrResource;
//            case 404:
//                return null;
//            case 401:
//                throw new UnAuthorizedException();
////            case 404:
////                throw new BadRequestException();
//            case 500:
//                throw new InternalServerException();
//            default:
//                throw new Exception("Response code : " + restResponse.getResponseCode());
//        }
//    }
//
//    @Override
//    public NetworkGroupResource deleteTravelGuideInNetworkGroupByID(Long id, Long travelGuideInNetworkGroupID) throws Exception {
//
//        String path = "/rest/web/networkgroup/update/" + id + "/remove/edgenetworkgroupid/" + travelGuideInNetworkGroupID;
//
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = mapper.writeValueAsString("");
//        System.out.println(jsonInString);
//
//        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
//        switch (restResponse.getResponseCode()) {
//            case 200:
//            case 201:
//                String responseString = restResponse.getResponseString();
//                NetworkGroupResource networkGroupResource = mapper.readValue(responseString, NetworkGroupResource.class);
//                return networkGroupResource;
//            case 226:
//                throw new ResourceInUsedException();
//            case 404:
//                return null;
//            case 401:
//                throw new UnAuthorizedException();
////            case 404:
////                throw new BadRequestException();
//            case 500:
//                throw new InternalServerException();
//            default:
//                throw new Exception("Response code : " + restResponse.getResponseCode());
//        }
//    }
//
//    @Override
//    public NetworkGroupResource updateNetworkGroupForEditTravelGuide(Long id, TravelGuideInNetworkGroupResource networkGroupResource) throws Exception {
//        String path = "/rest/web/networkgroup/update/" + id + "/update/edgenetworkgroupid/" + networkGroupResource.getTravelGuideInNetworkGroupID();
//
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = mapper.writeValueAsString(networkGroupResource);
//        System.out.println(jsonInString);
//        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
//        switch (restResponse.getResponseCode()) {
//            case 200:
//            case 201:
//                String responseString = restResponse.getResponseString();
//                NetworkGroupResource responseParentrResource = mapper.readValue(responseString, NetworkGroupResource.class);
//                return responseParentrResource;
//            case 404:
//                return null;
//            case 401:
//                throw new UnAuthorizedException();
////            case 404:
////                throw new BadRequestException();
//            case 500:
//                throw new InternalServerException();
//            default:
//                throw new Exception("Response code : " + restResponse.getResponseCode());
//        }
//    }
//
//    @Override
//    public NodeInNetworkGroupListResource readNodeInNetworkGroupResourceForNodeBySearchCriteria(NodeInNetworkGroupSearchCriteria searchCriteria) throws Exception {
//        String resourcePath = "/rest/web/networkgroup/nodeinnetworkgroup/listbynode";
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = mapper.writeValueAsString(searchCriteria);
//        System.out.println(jsonInString);
//
//        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(resourcePath, jsonInString);
//
//        switch (restResponse.getResponseCode()) {
//            case 200:
//            case 201:
//                String responseString = restResponse.getResponseString();
//                NodeInNetworkGroupListResource responseResource = mapper.readValue(responseString, NodeInNetworkGroupListResource.class);
//                return responseResource;
//            case 401:
//                throw new UnAuthorizedException();
//            case 404:
//                throw new BadRequestException();
//            case 500:
//                throw new InternalServerException();
//            default:
//                throw new Exception("Response code : " + restResponse.getResponseCode());
//        }
//    }
//
//    @Override
//    public TravelGuideInNetworkGroupListResource readTravelGuideInNetworkGroupResourceForNodeBySearchCriteria(TravelGuideInNetworkGroupSearchCriteria searchCriteria) throws Exception {
//        String resourcePath = "/rest/web/networkgroup/edgeinnetworkgroup/listbynode";
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = mapper.writeValueAsString(searchCriteria);
//        System.out.println(jsonInString);
//
//        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(resourcePath, jsonInString);
//
//        switch (restResponse.getResponseCode()) {
//            case 200:
//            case 201:
//                String responseString = restResponse.getResponseString();
//                TravelGuideInNetworkGroupListResource responseResource = mapper.readValue(responseString, TravelGuideInNetworkGroupListResource.class);
//                return responseResource;
//            case 401:
//                throw new UnAuthorizedException();
//            case 404:
//                throw new BadRequestException();
//            case 500:
//                throw new InternalServerException();
//            default:
//                throw new Exception("Response code : " + restResponse.getResponseCode());
//        }
//    }
//
//    @Override
//    public GeoLocationInNetworkGroupListResource readTravelItineraryInNetworkGroupResourceForNodeBySearchCriteria(GeoLocationInNetworkGroupSearchCriteria searchCriteria) throws Exception {
//        String resourcePath = "/rest/web/networkgroup/geolocationinnetworkgroup/listbynode";
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = mapper.writeValueAsString(searchCriteria);
//        System.out.println(jsonInString);
//
//        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(resourcePath, jsonInString);
//
//        switch (restResponse.getResponseCode()) {
//            case 200:
//            case 201:
//                String responseString = restResponse.getResponseString();
//                GeoLocationInNetworkGroupListResource responseResource = mapper.readValue(responseString, GeoLocationInNetworkGroupListResource.class);
//                return responseResource;
//            case 401:
//                throw new UnAuthorizedException();
//            case 404:
//                throw new BadRequestException();
//            case 500:
//                throw new InternalServerException();
//            default:
//                throw new Exception("Response code : " + restResponse.getResponseCode());
//        }
//    }
//
//    @Override
//    public NodeInNetworkGroupListResource readNodeInNetworkGroupResourceForTravelGuideBySearchCriteria(NodeInNetworkGroupSearchCriteria searchCriteria) throws Exception {
//        String resourcePath = "/rest/web/networkgroup/nodeinnetworkgroup/listbyedge";
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = mapper.writeValueAsString(searchCriteria);
//        System.out.println(jsonInString);
//
//        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(resourcePath, jsonInString);
//
//        switch (restResponse.getResponseCode()) {
//            case 200:
//            case 201:
//                String responseString = restResponse.getResponseString();
//                NodeInNetworkGroupListResource responseResource = mapper.readValue(responseString, NodeInNetworkGroupListResource.class);
//                return responseResource;
//            case 401:
//                throw new UnAuthorizedException();
//            case 404:
//                throw new BadRequestException();
//            case 500:
//                throw new InternalServerException();
//            default:
//                throw new Exception("Response code : " + restResponse.getResponseCode());
//        }
//    }
//
//    @Override
//    public TravelGuideInNetworkGroupListResource readTravelGuideInNetworkGroupResourceForTravelGuideBySearchCriteria(TravelGuideInNetworkGroupSearchCriteria searchCriteria) throws Exception {
//        String resourcePath = "/rest/web/networkgroup/edgeinnetworkgroup/listbyedge";
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = mapper.writeValueAsString(searchCriteria);
//        System.out.println(jsonInString);
//
//        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(resourcePath, jsonInString);
//
//        switch (restResponse.getResponseCode()) {
//            case 200:
//            case 201:
//                String responseString = restResponse.getResponseString();
//                TravelGuideInNetworkGroupListResource responseResource = mapper.readValue(responseString, TravelGuideInNetworkGroupListResource.class);
//                return responseResource;
//            case 401:
//                throw new UnAuthorizedException();
//            case 404:
//                throw new BadRequestException();
//            case 500:
//                throw new InternalServerException();
//            default:
//                throw new Exception("Response code : " + restResponse.getResponseCode());
//        }
//    }
//
//    @Override
//    public GeoLocationInNetworkGroupListResource readTravelItineraryInNetworkGroupResourceForTravelGuideBySearchCriteria(GeoLocationInNetworkGroupSearchCriteria searchCriteria) throws Exception {
//        String resourcePath = "/rest/web/networkgroup/geolocationinnetworkgroup/listbyedge";
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = mapper.writeValueAsString(searchCriteria);
//        System.out.println(jsonInString);
//
//        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(resourcePath, jsonInString);
//
//        switch (restResponse.getResponseCode()) {
//            case 200:
//            case 201:
//                String responseString = restResponse.getResponseString();
//                GeoLocationInNetworkGroupListResource responseResource = mapper.readValue(responseString, GeoLocationInNetworkGroupListResource.class);
//                return responseResource;
//            case 401:
//                throw new UnAuthorizedException();
//            case 404:
//                throw new BadRequestException();
//            case 500:
//                throw new InternalServerException();
//            default:
//                throw new Exception("Response code : " + restResponse.getResponseCode());
//        }
//    }
}
