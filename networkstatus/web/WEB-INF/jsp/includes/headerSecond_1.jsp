<%@page import="org.springframework.security.core.Authentication"%>
<%@page import="org.springframework.security.core.GrantedAuthority"%>
<%@page import="java.util.List"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@page import="java.util.Properties" %>

<%
    Properties prop = new Properties();
    prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
    String version = prop.getProperty("app_version") != null ? prop.getProperty("app_version") : "";

    String machineMode = prop.getProperty("machine_mode") != null ? prop.getProperty("machine_mode").trim() : "small";
    pageContext.setAttribute("machineMode", machineMode);
    pageContext.setAttribute("bigBox", "big");
    pageContext.setAttribute("smallBox", "small");

    boolean isAdmin = false;
    boolean isUser = false;

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    for (GrantedAuthority auth : authentication.getAuthorities()) {
        if ("ROLE_ADMINISTRATOR".equalsIgnoreCase(auth.getAuthority())) {
            isAdmin = true;
        }

        if ("ROLE_USER".equalsIgnoreCase(auth.getAuthority())) {
            isUser = true;
        }
    }

    pageContext.setAttribute("isUser", isUser);
    pageContext.setAttribute("isAdmin", isAdmin);

    String webServerDomainName = prop.getProperty("web_server_external_domain_name") != null ? prop.getProperty("web_server_external_domain_name").trim() : "";
    String isSSLWebServer = prop.getProperty("web_server_ssl") != null ? prop.getProperty("web_server_ssl").trim() : "";
    pageContext.setAttribute("webServerDomainName", webServerDomainName);
    pageContext.setAttribute("isSSLWebServer", isSSLWebServer);

    String protocol = isSSLWebServer.equalsIgnoreCase("1") ? "https://" : "http://";
    String webServerDomainURL = protocol + webServerDomainName;
    pageContext.setAttribute("webServerDomainURL", webServerDomainURL);
%>


<div class="container">
    <div class="row-fluid">
        <div class="span6">
        </div>
        <div class="span6">
            <div id="header" class="pull-right">
                <!-- For login user -->
                <sec:authorize access="hasAnyRole('ROLE_USER','ROLE_ADMINISTRATOR')">
                    <c:if test="${pageContext.request.userPrincipal.name != null}">
                        <div class="logoutDiv">
                            Welcome : ${pageContext.request.userPrincipal.name} | <a
                                href="javascript:formSubmitById('logoutForm')"> Logout</a>
                            <c:url value="/logout" var="logoutUrl" />
                            <form action="${logoutUrl}" method="post" id="logoutForm">
                                <input type="hidden" name="${_csrf.parameterName}"
                                       value="${_csrf.token}" />
                            </form>
                        </div>
                    </c:if>
                </sec:authorize>
                <div class="clear"></div>
            </div>
        </div>
    </div>
</div>




<sec:authorize access="hasAnyRole('ROLE_USER','ROLE_ADMINISTRATOR')">
    <c:if test="${pageContext.request.userPrincipal.name != null}">


        <c:choose>
            <c:when test="${isAdmin}">
                <%@ include file="/WEB-INF/jsp/includes/navigation_1.jsp" %> 
            </c:when>

            <c:when test="${isUser}">
                <%@ include file="/WEB-INF/jsp/includes/navigation_1.jsp" %> 
            </c:when>

        </c:choose>

    </c:if>
</sec:authorize>




