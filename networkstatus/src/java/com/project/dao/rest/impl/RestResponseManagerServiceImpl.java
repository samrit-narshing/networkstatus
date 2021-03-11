/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.dao.rest.impl;

import com.project.config.PropertiesConfig;
import com.project.dao.rest.RestResponseManagerService;
import com.project.model.rest.RestResponse;
import com.project.util.rest.DefaultTrustManager;
import com.project.util.rest.SessionCookieManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import javax.annotation.Resource;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author Samrit
 */
@Service("restResponseManagerService")
public class RestResponseManagerServiceImpl implements RestResponseManagerService {

    @Autowired
    @Resource(name = "propertiesConfig")
    private PropertiesConfig propertiesConfig;

    @Override
    public RestResponse getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(String path, String postData) throws Exception {

        CookieHandler.setDefault(SessionCookieManager.getInstance());

        getResponseStringFromWebServerAfterUserAuthentication();

        boolean isSSL = false;
        String responseString = "";
        int responseCode;

        String domainName = propertiesConfig.getProperty("web_server_domain_name");
        String ssl = propertiesConfig.getProperty("web_server_ssl");

        if (ssl != null && ssl.trim().equals("1")) {
            isSSL = true;
        }

        URLConnection conn;
        URL url;
        String link;

        if (isSSL) {
            link = "https://" + domainName + path;
            url = new URL(link);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });

            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
            SSLContext.setDefault(ctx);
            httpsURLConnection.setSSLSocketFactory(ctx.getSocketFactory());
            conn = httpsURLConnection;
        } else {
            link = "http://" + domainName + path;
            url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            conn = httpURLConnection;
        }

        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setConnectTimeout(15000);
        conn.setDoOutput(true);

        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

        wr.write(postData);

        wr.flush();

        if (isSSL) {
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) conn;
            System.out.println("____" + httpsURLConnection.getResponseCode());
            responseCode = httpsURLConnection.getResponseCode();

        } else {
            HttpURLConnection httpURLConnection = (HttpURLConnection) conn;
            System.out.println("____" + httpURLConnection.getResponseCode());
            responseCode = httpURLConnection.getResponseCode();
        }

        if (responseCode == 200 || responseCode == 201 || responseCode == 302) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            responseString = sb.toString();
        }

        RestResponse restResponse = new RestResponse();
        restResponse.setResponseCode(responseCode);
        restResponse.setResponseString(responseString);

        SessionCookieManager.getInstance().clear();
        return restResponse;
    }

//    @Override
    private RestResponse getResponseStringFromWebServerAfterUserAuthentication() throws Exception {

//CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
//CookieManager cookieManager = new CookieManager();
//CookieHandler.setDefault(cookieManager);
//CookieHandler.setDefault(SessionCookieManager.getInstance());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();
        String password = auth.getCredentials().toString();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails details = (UserDetails) principal;
            username = details.getUsername();
        }

        boolean isSSL = false;
        String responseString = "";
        int responseCode;

        String domainName = propertiesConfig.getProperty("web_server_domain_name");
        String ssl = propertiesConfig.getProperty("web_server_ssl");

        if (ssl != null && ssl.trim().equals("1")) {
            isSSL = true;
        }

        URLConnection conn;
        URL url;
        String link;

        String postString = "username=" + username + "&password=" + password;

        if (isSSL) {

            link = "https://" + domainName + "/login";
            url = new URL(link);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });

            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
            SSLContext.setDefault(ctx);
            httpsURLConnection.setSSLSocketFactory(ctx.getSocketFactory());
            conn = httpsURLConnection;
        } else {
            link = "http://" + domainName + "/login";
            url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            conn = httpURLConnection;
        }

        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setConnectTimeout(15000);
        conn.setDoOutput(true);

        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(postString);
        wr.flush();

        if (isSSL) {
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) conn;
            System.out.println("____" + httpsURLConnection.getResponseCode());
            responseCode = httpsURLConnection.getResponseCode();

        } else {
            HttpURLConnection httpURLConnection = (HttpURLConnection) conn;
            System.out.println("____" + httpURLConnection.getResponseCode());
            responseCode = httpURLConnection.getResponseCode();

//            String key;
//            System.out.println("Headers-------start-----");
//            for (int i = 1; (key = conn.getHeaderFieldKey(i)) != null; i++) {
//                System.out.println(key + ":" + conn.getHeaderField(i));
//            }
////            System.out.println("Set-Cookie :" + conn.getHeaderField("Set-Cookie"));
//
//            System.out.println("Headers-------end-----");
        }

        if (responseCode == 200 || responseCode == 201 || responseCode == 302) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);

            }
            responseString = sb.toString();
        }

        RestResponse restResponse = new RestResponse();
        restResponse.setResponseCode(responseCode);
        restResponse.setResponseString(responseString);
        return restResponse;
    }

    @Override
    public RestResponse getResponseStringFromWebServerWithPostJSONData(String path, String postData) throws Exception {

        boolean isSSL = false;
        String responseString = "";
        int responseCode;

        String domainName = propertiesConfig.getProperty("web_server_domain_name");
        String ssl = propertiesConfig.getProperty("web_server_ssl");

        if (ssl != null && ssl.trim().equals("1")) {
            isSSL = true;
        }

        URLConnection conn;
        URL url;
        String link;

        if (isSSL) {
            link = "https://" + domainName + path;
            url = new URL(link);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });

            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
            SSLContext.setDefault(ctx);
            httpsURLConnection.setSSLSocketFactory(ctx.getSocketFactory());
            conn = httpsURLConnection;
        } else {
            link = "http://" + domainName + path;
            url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            conn = httpURLConnection;
        }

        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setConnectTimeout(15000);
        conn.setDoOutput(true);

        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

        wr.write(postData);

        wr.flush();

        if (isSSL) {
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) conn;
            System.out.println("____" + httpsURLConnection.getResponseCode());
            responseCode = httpsURLConnection.getResponseCode();

        } else {
            HttpURLConnection httpURLConnection = (HttpURLConnection) conn;
            System.out.println("____" + httpURLConnection.getResponseCode());
            responseCode = httpURLConnection.getResponseCode();
        }

        if (responseCode == 200 || responseCode == 201 || responseCode == 302) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            responseString = sb.toString();
        }

        RestResponse restResponse = new RestResponse();
        restResponse.setResponseCode(responseCode);
        restResponse.setResponseString(responseString);
        return restResponse;
    }

    @Override
    public RestResponse getResponseStringFromWebServerWithPostPLAINData(String path, String postString) throws Exception {

        boolean isSSL = false;
        String responseString = "";
        int responseCode;

        String domainName = propertiesConfig.getProperty("web_server_domain_name");
        String ssl = propertiesConfig.getProperty("web_server_ssl");

        if (ssl != null && ssl.trim().equals("1")) {
            isSSL = true;
        }

        URLConnection conn;
        URL url;
        String link;

//        String postString = "username=" + username + "&password=" + password;

        if (isSSL) {
            link = "https://" + domainName + path;
            url = new URL(link);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });

            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
            SSLContext.setDefault(ctx);
            httpsURLConnection.setSSLSocketFactory(ctx.getSocketFactory());
            conn = httpsURLConnection;
        } else {
            link = "http://" + domainName + path;
            url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            conn = httpURLConnection;
        }

        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setConnectTimeout(15000);
        conn.setDoOutput(true);

        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(postString);
        wr.flush();

        if (isSSL) {
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) conn;
            System.out.println("____" + httpsURLConnection.getResponseCode());
            responseCode = httpsURLConnection.getResponseCode();

        } else {
            HttpURLConnection httpURLConnection = (HttpURLConnection) conn;
            System.out.println("____" + httpURLConnection.getResponseCode());
            responseCode = httpURLConnection.getResponseCode();

//            String key;
//            System.out.println("Headers-------start-----");
//            for (int i = 1; (key = conn.getHeaderFieldKey(i)) != null; i++) {
//                System.out.println(key + ":" + conn.getHeaderField(i));
//            }
////            System.out.println("Set-Cookie :" + conn.getHeaderField("Set-Cookie"));
//
//            System.out.println("Headers-------end-----");
        }

        if (responseCode == 200 || responseCode == 201 || responseCode == 302) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);

            }
            responseString = sb.toString();
        }

        RestResponse restResponse = new RestResponse();
        restResponse.setResponseCode(responseCode);
        restResponse.setResponseString(responseString);
        return restResponse;
    }

}
