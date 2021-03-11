package com.project.dao.network;

import com.project.dao.util.*;
import com.project.model.network.NodeAlertInfoArchiveListResource;
import com.project.model.network.NodeAlertInfoArchiveResource;
import com.project.util.searchcriteria.NodeAlertInfoArchiveSearchCriteria;

/**
 *
 * @author Samrit
 */
public interface NodeAlertInfoArchiveService {

    public NodeAlertInfoArchiveResource findNodeAlertInfoArchive(Long id) throws Exception;

    public NodeAlertInfoArchiveResource createNodeAlertInfoArchive(NodeAlertInfoArchiveResource data) throws Exception;

    public NodeAlertInfoArchiveListResource createNodeAlertInfoArchives(NodeAlertInfoArchiveListResource data) throws Exception;

    public NodeAlertInfoArchiveListResource findNodeAlertInfoArchiveListBySearchCriteria(NodeAlertInfoArchiveSearchCriteria searchCriteria) throws Exception;

    public NodeAlertInfoArchiveResource deleteNodeAlertInfoArchive(Long id) throws Exception;

    public int deleteAllNodeAlertInfoArchive() throws Exception;

}
