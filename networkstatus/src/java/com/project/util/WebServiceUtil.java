/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.util;

import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author samri
 */
public class WebServiceUtil {

    public String domainName = "";
    String ssl = "";

    public WebServiceUtil() {
        try {
            Properties prop = new Properties();
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream stream = loader.getResourceAsStream("config.properties");
            prop.load(stream);
            domainName = (String) prop.get("web_server_external_domain_name");
            ssl = (String) prop.get("web_server_ssl");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDomainURL() {

        boolean isSSL = false;

        if (ssl != null && ssl.trim().equals("1")) {
            isSSL = true;
        }

        String link;

        if (isSSL) {
            link = "https://" + domainName;
        } else {
            link = "http://" + domainName;
        }

        return link;

    }

}
