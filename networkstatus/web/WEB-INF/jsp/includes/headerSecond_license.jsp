<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@page import="java.util.Properties" %>

<%
    Properties prop = new Properties();
    prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
    String version = prop.getProperty("app_version") != null ? prop.getProperty("app_version") : "";
%>




<div id="container">
    <div id="header">
        <h1><a href="${initParam.PROJECT_ROOT_PATH}/home">${initParam.PROJECT_NAME}</a></h1>
        <h2><i>Version : (<%=version%>)</i></h2>
                   

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



    <div id="nav">

        <sec:authorize access="hasAnyRole('ROLE_USER','ROLE_ADMINISTRATOR')">
            <c:if test="${pageContext.request.userPrincipal.name != null}">


                


            </c:if>
        </sec:authorize>

    </div>

