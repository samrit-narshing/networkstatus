<%@ include file="/WEB-INF/jsp/includes/tagLibs.jsp" %> 
<%@page session="true"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <%@ include file="/WEB-INF/jsp/includes/headerFirst.jsp" %> 
    <body class="noheader" onload='document.loginForm.username.focus();'>
        <%@ include file="/WEB-INF/jsp/includes/headerSecond.jsp" %> 
        <div id="body">
            <div id="breadcrumbs">
                <a href="${initParam.PROJECT_ROOT_PATH}/home">Home</a> &raquo;
                <strong>Page Not Found</strong>
            </div>
            <div id="content">
                <!--Div Content Start-->




                <h1>Title : ${title}</h1>
                <h1>Message : ${message}</h1>
                <sec:authorize access="hasRole('ROLE_USER')">
                    <c:if test="${pageContext.request.userPrincipal.name != null}">
                        <h2>
                            Welcome : ${pageContext.request.userPrincipal.name}
                        </h2>
                    </c:if>
                </sec:authorize>





                <!--Div Content End-->
                <br/><br/><br/><br/>
            </div>
            <div class="clear"></div>
        </div>
        <%@ include file="/WEB-INF/jsp/includes/footerSecondLast.jsp" %>    
    </body>
    <%@ include file="/WEB-INF/jsp/includes/footerLast.jsp" %>       
