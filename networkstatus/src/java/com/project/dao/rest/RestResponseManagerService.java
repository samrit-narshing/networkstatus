/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.dao.rest;

import com.project.model.rest.RestResponse;

/**
 *
 * @author Samrit
 */
public interface RestResponseManagerService {

    public RestResponse getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(String path, String postData) throws Exception;

    public RestResponse getResponseStringFromWebServerWithPostJSONData(String path, String postData) throws Exception;

    public RestResponse getResponseStringFromWebServerWithPostPLAINData(String path, String postData) throws Exception;

//    public RestResponse getResponseStringFromWebServerAfterUserAuthentication() throws Exception;
}
