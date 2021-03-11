package com.project.model.network;

import org.springframework.hateoas.ResourceSupport;

/**
 *
 * @author Samrit
 */
public class NodeResource extends ResourceSupport {

    private Long nodeID = 0L;
    private String description = "";
    private String uniqueID = "";
    private int height = 15;
    private String label = "";
    private String redirectingURLLink = "https://www.google.com";
    private String type = "";
    private boolean enabled = false;
    private NodeImageLinkResource fill = new NodeImageLinkResource();
    private int nodeValue = 0;
    private String title = "";
    private NodeAlertResource alert = new NodeAlertResource();
    private String errTitle = "";
    private String errLabel = "";
    private String errType = "";
    private String errRedirectingURLLink = "";

    private double domXValue = 0.0D;
    private double domYValue = 0.0D;
    private double canvasXValue = 0.0D;
    private double canvasYValue = 0.0D;
    private double zoomScale = 0.0D;

    private String edgesStr = "";
    private String errEdgesStr = "";
    private boolean selected = false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getEdgesStr() {
        return edgesStr;
    }

    public void setEdgesStr(String edgesStr) {
        this.edgesStr = edgesStr;
    }

    public String getErrEdgesStr() {
        return errEdgesStr;
    }

    public void setErrEdgesStr(String errEdgesStr) {
        this.errEdgesStr = errEdgesStr;
    }

    public String getErrType() {
        return errType;
    }

    public void setErrType(String errType) {
        this.errType = errType;
    }

    public double getDomXValue() {
        return domXValue;
    }

    public void setDomXValue(double domXValue) {
        this.domXValue = domXValue;
    }

    public double getDomYValue() {
        return domYValue;
    }

    public void setDomYValue(double domYValue) {
        this.domYValue = domYValue;
    }

    public double getCanvasXValue() {
        return canvasXValue;
    }

    public void setCanvasXValue(double canvasXValue) {
        this.canvasXValue = canvasXValue;
    }

    public double getCanvasYValue() {
        return canvasYValue;
    }

    public void setCanvasYValue(double canvasYValue) {
        this.canvasYValue = canvasYValue;
    }

    public double getZoomScale() {
        return zoomScale;
    }

    public void setZoomScale(double zoomScale) {
        this.zoomScale = zoomScale;
    }

    public String getErrTitle() {
        return errTitle;
    }

    public void setErrTitle(String errTitle) {
        this.errTitle = errTitle;
    }

    public int getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(int nodeValue) {
        this.nodeValue = nodeValue;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NodeImageLinkResource getFill() {
        return fill;
    }

    public void setFill(NodeImageLinkResource fill) {
        this.fill = fill;
    }

    public NodeAlertResource getAlert() {
        return alert;
    }

    public void setAlert(NodeAlertResource alert) {
        this.alert = alert;
    }

    public String getErrLabel() {
        return errLabel;
    }

    public void setErrLabel(String errLabel) {
        this.errLabel = errLabel;
    }

    public String getErrRedirectingURLLink() {
        return errRedirectingURLLink;
    }

    public void setErrRedirectingURLLink(String errRedirectingURLLink) {
        this.errRedirectingURLLink = errRedirectingURLLink;
    }

    public Long getNodeID() {
        return nodeID;
    }

    public void setNodeID(Long nodeID) {
        this.nodeID = nodeID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getRedirectingURLLink() {
        return redirectingURLLink;
    }

    public void setRedirectingURLLink(String redirectingURLLink) {
        this.redirectingURLLink = redirectingURLLink;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
