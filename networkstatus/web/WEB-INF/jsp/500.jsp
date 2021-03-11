<%@ include file="/WEB-INF/jsp/includes/tagLibs.jsp" %> 
<%@page session="true"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <%@ include file="/WEB-INF/jsp/includes/headerFirst.jsp" %> 
    <body class="noheader" onload='document.loginForm.username.focus();'>
        <%@ include file="/WEB-INF/jsp/includes/headerSecond.jsp" %> 
        <div id="body">
            <div id="breadcrumbs">
                <a href="${initParam.PROJECT_ROOT_PATH}/home">Home</a> &raquo;
                <strong>Internal Error</strong>
            </div>






            <div class="centerbar">
                <ul>	
                    <li>
                        <h4 class="h4-green"><span>ERROR 500</span></h4>
                        <ul>
                            <li>
                                <p style="margin: 0;">

                                    <br/>
                                    <h1>HTTP Status 500 - Internal Error</h1>
                                    <br/>
                                    <c:choose>
                                        <c:when test="${empty username}">
                                            <h5>The requested resource is not found!</h5>
                                        </c:when>
                                        <c:otherwise>
                                            <h5>UserName : ${username} <br/>The requested resource is not found!</h5>
                                        </c:otherwise>
                                    </c:choose>

                                </p>
                            </li>
                        </ul>
                    </li>

                </ul> 
            </div>





            <div class="clear"></div>
        </div>
        <%@ include file="/WEB-INF/jsp/includes/footerSecondLast.jsp" %>    
    </body>
    <%@ include file="/WEB-INF/jsp/includes/footerLast.jsp" %>       

