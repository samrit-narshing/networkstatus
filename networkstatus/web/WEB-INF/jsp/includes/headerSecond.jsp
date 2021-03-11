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
    boolean isDepartmentUser = false;

    boolean isStudent = false;
    boolean isParent = false;
    boolean isProfessor = false;
    boolean isMotorist = false;
    boolean isFriend = false;

    boolean isTravelManager = false;

    boolean isTraveler = false;
    boolean isTravelGuide = false;

    boolean isSchoolManager = false;

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    for (GrantedAuthority auth : authentication.getAuthorities()) {
        if ("ROLE_ADMINISTRATOR".equalsIgnoreCase(auth.getAuthority())) {
            isAdmin = true;
        }

        if ("ROLE_USER".equalsIgnoreCase(auth.getAuthority())) {
            isUser = true;
        }

        if ("ROLE_DEPARTMENT_USER".equalsIgnoreCase(auth.getAuthority())) {
            isDepartmentUser = true;
        }

        if ("ROLE_STUDENT".equalsIgnoreCase(auth.getAuthority())) {
            isStudent = true;
        }

        if ("ROLE_PARENT".equalsIgnoreCase(auth.getAuthority())) {
            isParent = true;
        }

        if ("ROLE_PROFESSOR".equalsIgnoreCase(auth.getAuthority())) {
            isProfessor = true;
        }

        if ("ROLE_MOTORIST".equalsIgnoreCase(auth.getAuthority())) {
            isMotorist = true;
        }
        if ("ROLE_FRIEND".equalsIgnoreCase(auth.getAuthority())) {
            isFriend = true;
        }
        if ("ROLE_TRAVEL_ADMINISTRATOR".equalsIgnoreCase(auth.getAuthority())) {
            isTravelManager = true;
        }

        if ("ROLE_TRAVELER".equalsIgnoreCase(auth.getAuthority())) {
            isTraveler = true;
        }

        if ("ROLE_TRAVEL_GUIDE".equalsIgnoreCase(auth.getAuthority())) {
            isTravelGuide = true;
        }

        if ("ROLE_SCHOOL_ADMINISTRATOR".equalsIgnoreCase(auth.getAuthority())) {
            isSchoolManager = true;
        }

    }

    pageContext.setAttribute("isUser", isUser);
    pageContext.setAttribute("isAdmin", isAdmin);
    pageContext.setAttribute("isDepartmentUser", isDepartmentUser);

    pageContext.setAttribute("isSchoolManager", isSchoolManager);
    pageContext.setAttribute("isStudent", isStudent);
    pageContext.setAttribute("isParent", isParent);
    pageContext.setAttribute("isProfessor", isProfessor);
    pageContext.setAttribute("isMotorist", isMotorist);
    pageContext.setAttribute("isFriend", isFriend);

    pageContext.setAttribute("isTravelManager", isTravelManager);
    pageContext.setAttribute("isTraveler", isTraveler);
    pageContext.setAttribute("isTravelGuide", isTravelGuide);

    String webServerDomainName = prop.getProperty("web_server_external_domain_name") != null ? prop.getProperty("web_server_external_domain_name").trim() : "";
    String isSSLWebServer = prop.getProperty("web_server_ssl") != null ? prop.getProperty("web_server_ssl").trim() : "";
    pageContext.setAttribute("webServerDomainName", webServerDomainName);
    pageContext.setAttribute("isSSLWebServer", isSSLWebServer);

    String protocol = isSSLWebServer.equalsIgnoreCase("1") ? "https://" : "http://";
    String webServerDomainURL = protocol + webServerDomainName;
    pageContext.setAttribute("webServerDomainURL", webServerDomainURL);
%>




<div id="container">
    <div id="header">
        <h1><a href="${initParam.PROJECT_ROOT_PATH}/home">${initParam.PROJECT_NAME}</a></h1>
        <h2><i>Network Monitoring System (Version : <%=version%>)</i></h2>


        <!-- For login user -->
        <sec:authorize access="hasAnyRole('ROLE_USER','ROLE_ADMINISTRATOR','ROLE_DEPARTMENT_USER')">
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



    <div id="nav" style="width: 1250px">

        <sec:authorize access="hasAnyRole('ROLE_USER','ROLE_ADMINISTRATOR','ROLE_DEPARTMENT_USER')">
            <c:if test="${pageContext.request.userPrincipal.name != null}">


                <c:choose>
                    <c:when test="${isAdmin}">
                        <%@ include file="/WEB-INF/jsp/includes/navigation.jsp" %> 
                    </c:when>

                    <c:when test="${isUser}">
                        <%@ include file="/WEB-INF/jsp/includes/navigation_operator.jsp" %> 
                    </c:when>

                    <c:when test="${isDepartmentUser}">

                        <%@ include file="/WEB-INF/jsp/includes/navigation_departmentuser.jsp" %> 
                    </c:when>



                    <c:otherwise>
                        <%@ include file="/WEB-INF/jsp/includes/navigation_operator.jsp" %> 
                    </c:otherwise>

                </c:choose>

            </c:if>
        </sec:authorize>


    </div>

