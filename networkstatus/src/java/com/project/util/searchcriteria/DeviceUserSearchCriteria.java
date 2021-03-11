/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.util.searchcriteria;

/**
 *
 * @author Samrit
 */
public class DeviceUserSearchCriteria {

    private Long id = 0L;
    private String username = "";
    private Integer pageNo = 1;
    private Integer limitResult = 0;
    private Integer statusCode = 0;

    private String errUsername = "";

    public String getErrUsername() {
        return errUsername;
    }

    public void setErrUsername(String errUsername) {
        this.errUsername = errUsername;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getLimitResult() {
        return limitResult;
    }

    public void setLimitResult(Integer limitResult) {
        this.limitResult = limitResult;
    }

}
