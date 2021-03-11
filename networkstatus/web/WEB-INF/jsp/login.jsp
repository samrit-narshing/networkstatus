<%@ include file="/WEB-INF/jsp/includes/tagLibs.jsp" %> 
<%@page session="true"%>
<%@page import="java.util.Properties" %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <%@ include file="/WEB-INF/jsp/includes/headerFirst.jsp" %> 
    <body class="noheader" onload='document.loginForm.username.focus();javascript:getTasks();'>
        <%@ include file="/WEB-INF/jsp/includes/headerSecond.jsp" %> 
        <div id="body">

            <input type="hidden" value="SamritNarshingAmatya@samrit_narshing@hotmail.com9841249759">

                <div id="breadcrumbs">
                    <span>You are here:</span>
                    <strong>Login Page</strong>
                </div>


                <script>
                    function getTasks() {
                        var protocol = "";
                        if (${isSSLWebServer} === 1)
                        {
                            protocol = 'https://';
                        } else
                        {
                            protocol = 'http://';
                        }
//                        var serverUrl = protocol + '${webServerDomainName}/rest/web/users/checkServerConnection';
                        var serverUrl = '${webServerDomainURL}/rest/web/users/checkServerConnection';
                        $.get(serverUrl)
                                .success(function (result) {
//                                    alert(result);
                                })
                                .error(function (jqXHR, textStatus, errorThrown) {
                                    if (document.getElementById("pageError") !== null)
                                    {
                                        document.getElementById("pageError").innerHTML = "Please check Web Server connection, It is down or License is exipired ";
                                    }
                                    alert("WebServer is down or License is exipired .Please check webserver's network connection");
                                });

                    }

                </script>



                <div id="content">

                    <!--                <h3>Login Page</h3>-->

                    <c:if test="${not empty pageError}">
                        <div id="pageError">${pageError}</div>
                    </c:if>

                    <c:if test="${not empty pageInfo}">
                        <div id="pageInfo">${pageInfo}</div>
                    </c:if>

                    <c:if test="${not empty pageMessage}">
                        <div id="pageMessage">${pageMessage}</div>
                    </c:if>
                    <br/>
                    <!--                    <h2>Form</h2>-->
                    <fieldset>
                        <legend>Please Login</legend>
                        <form name='loginForm'
                              action="<c:url value='/login' />" method='POST'>

                            <br/>
                            <p><label for="username">User:</label>

                                <input type="text" name="username" id="username" value=""/><br /></p>	

                            <p><label for="password">Password:</label>
                                <input type="password" name="password" id="password" value=""/><br /></p>

                            <p><input name="send" class="formbutton" value="Login" type="submit" /></p>

                            <input type="hidden" name="${_csrf.parameterName}"
                                   value="${_csrf.token}" />

                        </form>
                                   
<!--                                   <br/>
                                   <h3 style="text-align:center; border-bottom: 1px solid rgb(200, 200, 200);border-top: 1px solid rgb(200, 200, 200);"> <a href='${webServerDomainURL}/rest/web/filehandler/download/apk'> Click  Here To Download Android Application.   </a></h3>
                    -->
                    </fieldset>

                    <br/><br/><br/><br/>
                </div>





                <div class="clear"></div>
        </div>
        <%@ include file="/WEB-INF/jsp/includes/footerSecondLast.jsp" %>    
    </body>
    <%@ include file="/WEB-INF/jsp/includes/footerLast.jsp" %>       


