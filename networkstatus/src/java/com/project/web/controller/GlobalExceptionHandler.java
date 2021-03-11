/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.web.controller;

import com.project.exception.rest.ResourceNotFoundException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

//    // private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
//    @ExceptionHandler(SQLException.class)
//    public String handleSQLException(HttpServletRequest request, Exception ex) {
//        //  logger.info("SQLException Occured:: URL="+request.getRequestURL());
//        return "database_error";
//    }
//    @ExceptionHandler(Exception.class)
//    public String exception(Exception e) {
//        e.printStackTrace();
//        return "errorhhh";
//    }
    @ExceptionHandler(Exception.class)
    public ModelAndView GlobalException(HttpServletRequest request, Exception ex) {
//        logger.error("Requested URL="+request.getRequestURL());
//        logger.error("Exception Raised="+ex);
//         
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", ex);
        modelAndView.addObject("url", request.getRequestURL());

        modelAndView.setViewName("error");
     //   return new ModelAndView("redirect:" + "/login?logout");
        return modelAndView;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundException() {
        return "meters/notfound";
    }
}