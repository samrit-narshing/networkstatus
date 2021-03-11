package com.project.config.core;

import com.project.config.SecurityConfig;
import javax.servlet.ServletContext;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.multipart.support.MultipartFilter;

public class SpringSecurityInitializer extends AbstractSecurityWebApplicationInitializer {
//
//    public SpringSecurityInitializer() {
//        super(SecurityConfig.class);
//    }

    @Override
    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
        insertFilters(servletContext, new MultipartFilter());
    }
}
