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
public class RoleSearchCriteria {

    private Long id = 0L;
    private String searchText = "";
    private Integer pageNo = 1;
    private Integer limitResult = 0;

    private Integer statusCode = 0;
    private String errSearchText = "";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
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

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrSearchText() {
        return errSearchText;
    }

    public void setErrSearchText(String errSearchText) {
        this.errSearchText = errSearchText;
    }

}
