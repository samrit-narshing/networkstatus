package com.project.model.network;

import java.util.ArrayList;
import java.util.List;
import org.springframework.hateoas.ResourceSupport;

/**
 *
 * @author Samrit
 */
public class NodeAlertResource extends ResourceSupport {

    private Long nodeAlertID = 0L;

    private int type = 0;
    private String description = "";

    private List<NodeAlertInfoResource> nodeAlertInfoResources = new ArrayList<>();

    public Long getNodeAlertID() {
        return nodeAlertID;
    }

    public void setNodeAlertID(Long nodeAlertID) {
        this.nodeAlertID = nodeAlertID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<NodeAlertInfoResource> getNodeAlertInfoResources() {
        return nodeAlertInfoResources;
    }

    public void setNodeAlertInfoResources(List<NodeAlertInfoResource> nodeAlertInfoResources) {
        this.nodeAlertInfoResources = nodeAlertInfoResources;
    }

}
