/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author Samrit
 */
public class InterceptorConfig extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>prehandle<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

//        if (license.LicenseManagement.isExpired(request)) {
//            response.sendRedirect(request.getContextPath() + "/redirectLicenseConfig.jsp");
//        } 
        
        if (new RestServiceForInterceptorConfig().readUserByUsername(username).isPasswordExpire()) {
            response.sendRedirect(request.getContextPath() + "/redirect_change_password_config.jsp");
        }

        return true;
    }

}
