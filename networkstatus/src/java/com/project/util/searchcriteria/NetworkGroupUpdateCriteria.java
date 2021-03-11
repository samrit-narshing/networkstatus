/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.util.searchcriteria;

/**
 *
 * @author samri_g64pbd3
 */
public class NetworkGroupUpdateCriteria {

    private Long networkGroupID = 0L;
    private String networkGroupCode = "";

    public Long getNetworkGroupID() {
        return networkGroupID;
    }

    public void setNetworkGroupID(Long networkGroupID) {
        this.networkGroupID = networkGroupID;
    }

    public String getNetworkGroupCode() {
        return networkGroupCode;
    }

    public void setNetworkGroupCode(String networkGroupCode) {
        this.networkGroupCode = networkGroupCode;
    }

}
