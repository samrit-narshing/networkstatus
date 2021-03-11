package com.project.model.network;

import org.springframework.hateoas.ResourceSupport;

/**
 *
 * @author Samrit
 */
public class EdgeResource extends ResourceSupport {

    private Long edgeID = 0L;

    private NodeResource fromNodeResource = new NodeResource();

    private NodeResource toNodeResource = new NodeResource();

    private String label = "";

    private String title = "";

    private boolean dashes = false;

    private int edge_length = 0;

    private int edge_value = 0;

    private String arrows = "";

    private boolean enabled = false;

    private String errLabel = "";

    private String errNode = "";

    private String errTitle = "";

    private String errFromNode = "";

    private String errToNode = "";

    public String getErrFromNode() {
        return errFromNode;
    }

    public void setErrFromNode(String errFromNode) {
        this.errFromNode = errFromNode;
    }

    public String getErrToNode() {
        return errToNode;
    }

    public void setErrToNode(String errToNode) {
        this.errToNode = errToNode;
    }

    public String getErrNode() {
        return errNode;
    }

    public void setErrNode(String errNode) {
        this.errNode = errNode;
    }

    public String getErrLabel() {
        return errLabel;
    }

    public void setErrLabel(String errLabel) {
        this.errLabel = errLabel;
    }

    public Long getEdgeID() {
        return edgeID;
    }

    public void setEdgeID(Long edgeID) {
        this.edgeID = edgeID;
    }

    public NodeResource getFromNodeResource() {
        return fromNodeResource;
    }

    public void setFromNodeResource(NodeResource fromNodeResource) {
        this.fromNodeResource = fromNodeResource;
    }

    public NodeResource getToNodeResource() {
        return toNodeResource;
    }

    public void setToNodeResource(NodeResource toNodeResource) {
        this.toNodeResource = toNodeResource;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isDashes() {
        return dashes;
    }

    public void setDashes(boolean dashes) {
        this.dashes = dashes;
    }

    public int getEdge_length() {
        return edge_length;
    }

    public void setEdge_length(int edge_length) {
        this.edge_length = edge_length;
    }

    public int getEdge_value() {
        return edge_value;
    }

    public void setEdge_value(int edge_value) {
        this.edge_value = edge_value;
    }

    public String getArrows() {
        return arrows;
    }

    public void setArrows(String arrows) {
        this.arrows = arrows;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getErrTitle() {
        return errTitle;
    }

    public void setErrTitle(String errTitle) {
        this.errTitle = errTitle;
    }

}
