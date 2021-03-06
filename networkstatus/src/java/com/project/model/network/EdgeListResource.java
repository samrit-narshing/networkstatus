package com.project.model.network;

import org.springframework.hateoas.ResourceSupport;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samrit
 */
public class EdgeListResource extends ResourceSupport {

    private List<EdgeResource> edgeResources = new ArrayList<>();
    private Long totalPages = new Long(0);
    private Long totalDocuments = new Long(0);

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalDocuments() {
        return totalDocuments;
    }

    public void setTotalDocuments(Long totalDocuments) {
        this.totalDocuments = totalDocuments;
    }

    public List<EdgeResource> getEdgeResources() {
        return edgeResources;
    }

    public void setEdgeResources(List<EdgeResource> edgeResources) {
        this.edgeResources = edgeResources;
    }

}
