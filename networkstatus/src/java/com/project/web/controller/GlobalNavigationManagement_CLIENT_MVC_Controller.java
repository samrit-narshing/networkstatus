package com.project.web.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GlobalNavigationManagement_CLIENT_MVC_Controller {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("pageError", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("pageMessage", "You've been logged out successfully.");
        }
        model.setViewName("login");

        return model;

    }

    //for 403 access denied page
    @RequestMapping(value = "/403", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView accesssDenied() {

        ModelAndView model = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //check if user is null or session is time out
        if (auth == null) {
            model.setViewName("/login?logout");
        } else {
            //check if user is login
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) auth.getPrincipal();
                System.out.println(userDetail);

                model.addObject("username", userDetail.getUsername());

            }

            model.setViewName("403");
        }
        return model;

    }

    //for 404 page not found 
    @RequestMapping(value = "/404", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView pageNotFound() {

        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //check if user is null or session is time out
        if (auth == null) {
            model.setViewName("/login?logout");
        } else {
            //check if user is login
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) auth.getPrincipal();
                System.out.println(userDetail);

                model.addObject("username", userDetail.getUsername());

            }
            model.setViewName("404");
        }
        return model;

    }

    //for 500 Internal Error 
    @RequestMapping(value = "/500", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView internalError() {

        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //check if user is null or session is time out
        if (auth == null) {
            model.setViewName("/login?logout");
        } else {
            //check if user is login
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) auth.getPrincipal();
                System.out.println(userDetail);

                model.addObject("username", userDetail.getUsername());
            }
            model.setViewName("500");
        }
        return model;

    }

    //for 500 MaxFileSizeUploaded 
    @RequestMapping(value = "/500_uploadSizeError", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView fileSizeLimitUploaded() {

        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //check if user is null or session is time out
        if (auth == null) {
            model.setViewName("/login?logout");
        } else {
            //check if user is login
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) auth.getPrincipal();
                System.out.println(userDetail);

                model.addObject("username", userDetail.getUsername());

            }
            model.setViewName("500_uploadSizeError");
        }
        return model;

    }

    @RequestMapping(value = {"/", "/welcome**"}, method = RequestMethod.GET)
    public ModelAndView defaultPage() {

        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security Login Form - Database Authentication");
        model.addObject("message", "This is default page!");
        model.setViewName("hello");
        return model;

    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView adminPage() {

        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security Login Form - Database Authentication");
        model.addObject("message", "This page is for ROLE_ADMIN only!");
        model.setViewName("admin");

        return model;

    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView homePage() {

        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security - Authenticated ");
        model.addObject("message", "Logged On!");
        model.setViewName("home");

        return model;

    }

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public ModelAndView resultPage() {

        ModelAndView model = new ModelAndView();
//        model.addObject("title", "Spring Security - Authenticated ");
//        model.addObject("message", "Logged On!");
        model.setViewName("result");

        return model;

    }

    @RequestMapping(value = "/result_publicuser", method = RequestMethod.GET)
    public ModelAndView resultPublicUserPage() {

        ModelAndView model = new ModelAndView();
//        model.addObject("title", "Spring Security - Authenticated ");
//        model.addObject("message", "Logged On!");
        model.setViewName("result_publicuser");

        return model;

    }

    @RequestMapping(value = "/home_1", method = RequestMethod.GET)
    public ModelAndView homePage_1() {

        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security - Authenticated ");
        model.addObject("message", "Logged On!");
        model.setViewName("home_1");

        return model;

    }
}
