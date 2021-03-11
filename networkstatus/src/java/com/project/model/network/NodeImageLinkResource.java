package com.project.model.network;

import org.springframework.hateoas.ResourceSupport;

/**
 *
 * @author Samrit
 */
public class NodeImageLinkResource extends ResourceSupport {

    private Long nodeImageLinkID = 0L;
    private String src = "";

    public Long getNodeImageLinkID() {
        return nodeImageLinkID;
    }

    public void setNodeImageLinkID(Long nodeImageLinkID) {
        this.nodeImageLinkID = nodeImageLinkID;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

}
