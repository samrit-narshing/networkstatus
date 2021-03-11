package com.project.dao.network;

import com.project.model.network.EdgeListResource;
import com.project.model.network.EdgeResource;
import com.project.util.searchcriteria.EdgeSearchCriteria;

/**
 *
 * @author Samrit
 */
public interface EdgeService {

    public EdgeResource createEdge(EdgeResource edgeResource) throws Exception;

    public EdgeResource readEdgeByCode(String code) throws Exception;

    public EdgeResource readEdgeByNodeIDs(Long nodeID1, Long nodeID2) throws Exception;

//   public EdgeResource readEnabledEdgeByCode(String code) throws Exception;
    public EdgeResource readEdgeByID(Long id) throws Exception;

    public EdgeListResource readAllEdges() throws Exception;

    public EdgeListResource readAllEnabledEdges() throws Exception;

    public EdgeListResource readEdgesBySearchCriteria(EdgeSearchCriteria searchCriteria) throws Exception;

    public EdgeResource updateEdge(Long id, EdgeResource edgeResource) throws Exception;

    public EdgeResource updateEdgeEnabledStatus(Long id) throws Exception;

    public EdgeResource deleteEdgeByID(Long id) throws Exception;

}
