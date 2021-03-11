package com.project.dao.network;

import com.project.model.network.EdgeInNetworkGroupListResource;
import com.project.model.network.EdgeInNetworkGroupResource;
import com.project.model.network.NetworkGroupListResource;
import com.project.model.network.NetworkGroupResource;
import com.project.model.network.NodeAndEdgeListInNetworkGroupResource;
import com.project.model.network.NodeInNetworkGroupListResource;
import com.project.model.network.NodeInNetworkGroupResource;
import com.project.util.searchcriteria.NetworkGroupSearchCriteria;

/**
 *
 * @author Samrit
 */
public interface NetworkGroupService {

    public NetworkGroupResource createNetworkGroup(NetworkGroupResource travelGroup) throws Exception;

    public NetworkGroupResource readNetworkGroupResourceByCode(String code) throws Exception;

    public NetworkGroupListResource readNetworkGroupResourceBySearchCriteria(NetworkGroupSearchCriteria searchCriteria) throws Exception;

    public NetworkGroupResource deleteNetworkGroupByID(Long id) throws Exception;

    public NetworkGroupResource readNetworkGroupByID(Long id) throws Exception;

    public NetworkGroupResource updateNetworkGroup(Long id, NetworkGroupResource travelGroup) throws Exception;

    public NetworkGroupResource addAndUpdateNodes(Long id, NodeInNetworkGroupListResource nodeInNetworkGroupListResource) throws Exception;

    public NetworkGroupResource addAndUpdateNodesAndEdges(Long id, NodeAndEdgeListInNetworkGroupResource nodeAndEdgeListInNetworkGroupResource) throws Exception;

    public NetworkGroupResource deleteNodesInNetworkGroup(Long networkGroupID, NodeInNetworkGroupListResource nodeInNetworkGroupListResource) throws Exception;

    public NetworkGroupResource addAndUpdateEdges(Long id, EdgeInNetworkGroupListResource edgeInNetworkGroupListResource) throws Exception;

    public NetworkGroupResource deleteEdgesInNetworkGroup(Long networkGroupID, EdgeInNetworkGroupListResource edgeInNetworkGroupListResource) throws Exception;

    public NetworkGroupResource updateEdgesInNetworkGroup(Long networkGroupID, EdgeInNetworkGroupListResource edgeInNetworkGroupListResource) throws Exception;

    public EdgeInNetworkGroupResource readEdgeInNetworkGroupResourceByID(Long id) throws Exception;

    public NodeInNetworkGroupResource readNodeInNetworkGroupResourceByID(Long id) throws Exception;

    public NetworkGroupResource readNetworkGroupIfNodesAreAlreadyExists(Long networkGroupID, Long nodeInNetworkGroupId1, Long nodeInNetworkGroupId2) throws Exception;

    public NetworkGroupResource updateNodeInNetworkGroupResourcesForCordinates(Long networkGroupId, NodeInNetworkGroupListResource nodeInNetworkGroupListResource) throws Exception;

    public NetworkGroupResource clearAndUpdateNetworkGroupForAddEdges_NEW(Long id) throws Exception;
    
    public NetworkGroupResource readNetworkGroupByNodeInNetworkGroupResourceId(Long nodeInNetworkGroupId) throws Exception;

    //    public NetworkGroupListResource readNetworkGroupResourceForUser() throws Exception;
    //
    //    public NetworkGroupResource updateNetworkGroupForAddNodes(Long id, NodeInNetworkGroupResource data) throws Exception;
    //
    //    public NetworkGroupResource updateNetworkGroupForEditNodes(Long id, NodeInNetworkGroupResource travelGroupResource) throws Exception;
    //
    //    public NetworkGroupResource deleteNodeInNetworkGroupByID(Long id, Long travelerInNetworkGroupID) throws Exception;
    //
    //    public NetworkGroupResource updateNetworkGroupForAddEdge(Long id, EdgeInNetworkGroupResource data) throws Exception;
    //
    //    public NetworkGroupResource deleteEdgeInNetworkGroupByID(Long id, Long travelGuideInNetworkGroupID) throws Exception;
    //
    //    public NetworkGroupResource updateNetworkGroupForEditEdge(Long id, EdgeInNetworkGroupResource travelGroupResource) throws Exception;
    //
    //    public NodeInNetworkGroupListResource readNodeInNetworkGroupResourceForNodeBySearchCriteria(NodeInNetworkGroupSearchCriteria searchCriteria) throws Exception;
    //
    //    public EdgeInNetworkGroupListResource readEdgeInNetworkGroupResourceForNodeBySearchCriteria(EdgeInNetworkGroupSearchCriteria searchCriteria) throws Exception;
    //
    //    public NodeInNetworkGroupListResource readNodeInNetworkGroupResourceForEdgeBySearchCriteria(NodeInNetworkGroupSearchCriteria searchCriteria) throws Exception;
    //
    //    public EdgeInNetworkGroupListResource readEdgeInNetworkGroupResourceForEdgeBySearchCriteria(EdgeInNetworkGroupSearchCriteria searchCriteria) throws Exception;
}
