<%@ include file="/WEB-INF/jsp/includes/tagLibs.jsp" %> 
<%@page session="true"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <%@ include file="/WEB-INF/jsp/includes/headerFirst.jsp" %> 
    <body class="noheader" onload='document.loginForm.username.focus();'>
        <%@ include file="/WEB-INF/jsp/includes/headerSecond.jsp" %> 
        <div id="body">
            <div id="breadcrumbs">
                <span>You are here:</span>
                <strong>Home</strong>
            </div>
            <div id="content">
                <!--Div Content Start-->




                <div class="centerbar">
                    <ul>	
                        <li>
                            <h4 class="h4-green"><span>Welcome ${pageContext.request.userPrincipal.name}</span></h4>
                            <ul>
                                <li>
                                    <p style="margin: 0;">

                                        <br/>

                                        <h1>Welcome To ${initParam.PROJECT_NAME}.</h1>
                                        <br/>
                                        <h3> The Network Status Monitoring System </h3>
                                        <br/>
                                        <h5>Status : ${message}</h5>

                                        <sec:authorize access="hasAnyRole('ROLE_USER','ROLE_ADMIN')">
                                            <c:if test="${pageContext.request.userPrincipal.name != null}">
                                                <h5>
                                                    UserName : ${pageContext.request.userPrincipal.name}
                                                </h5>
                                            </c:if>
                                        </sec:authorize>



<!--                                        <form action="${initParam.PROJECT_ROOT_PATH}/userman/user/testDownloadReport" method="post">
                                            
                                            <input type="submit" value="Download Report">
                                            
                                        </form>-->

                                    </p>
                                </li>
                            </ul>
                        </li>

                    </ul> 
                </div>



                <!--Div Content End-->
                <br/><br/><br/><br/>
            </div>
            <div class="clear"></div>
        </div>
        <%@ include file="/WEB-INF/jsp/includes/footerSecondLast.jsp" %>    
    </body>
    <%@ include file="/WEB-INF/jsp/includes/footerLast.jsp" %>       


