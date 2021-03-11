package com.project.dao.network;

import com.project.model.network.NodeAndEdgeListResource;
import com.project.model.network.NodeListResource;
import com.project.model.network.NodeResource;
import com.project.model.util.FileInfo;
import com.project.model.util.UploadedFileContent;
import com.project.util.searchcriteria.NodeSearchCriteria;

/**
 *
 * @author Samrit
 */
public interface NodeService {

    public NodeResource createNode(NodeResource nodeResource) throws Exception;

    public NodeResource readNodeByLabel(String code) throws Exception;

//   public NodeResource readEnabledNodeByCode(String code) throws Exception;
    public NodeResource readNodeByID(Long id) throws Exception;

    public NodeListResource readAllNodes() throws Exception;

    public NodeListResource readAllEnabledNodes() throws Exception;

    public NodeListResource readAllEnabledNodesExculdingNodesInNetworkGroup(Long networkGroupId) throws Exception;

    public NodeListResource readNodesBySearchCriteria(NodeSearchCriteria searchCriteria) throws Exception;

    public NodeResource updateNode(Long id, NodeResource nodeResource) throws Exception;

    public NodeResource updateNodeEnabledStatus(Long id) throws Exception;

    public NodeResource updateNodeAlert(Long id, NodeResource nodeResource) throws Exception;

    public NodeResource updateNodeAlertToAddNodeAlertInfo(Long id, NodeResource nodeResource) throws Exception;

    public NodeResource updateNodeAlertAsReset(Long id) throws Exception;

    public NodeResource deleteNodeByID(Long id) throws Exception;

    public NodeResource updateNodeCordinates(NodeListResource nodeListResource) throws Exception;

    public FileInfo uploadCSVFileToImportNodesByObject(UploadedFileContent uploadedFileContent) throws Exception;

    public NodeListResource readAllCSVUploadedNodes() throws Exception;

    public NodeListResource createAndUpdateNodes(NodeListResource nodeListResource) throws Exception;

    public NodeAndEdgeListResource createAndUpdateNodesAndEdges(NodeAndEdgeListResource toSentNodeAndEdgeListResource) throws Exception;

}
