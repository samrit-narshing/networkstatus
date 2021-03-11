<%@ include file="/WEB-INF/jsp/includes/tagLibs.jsp" %>
<%--<%@page contentType="text/html" pageEncoding="UTF-8"%>--%>
<%@page session="true"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <%@ include file="/WEB-INF/jsp/includes/headerFirst.jsp" %> 


    <style type="text/css">
        fieldset p label {
            float: left;
            width: 220px;
        }
    </style>

    <script>
        window.onload = function () {
            document.getElementById("autoid").click();
        };
    </script>

    <body class="noheader" onload='document.loginForm.username.focus();'>
        <input type="hidden" value="SamritNarshingAmatya@samrit_narshing@hotmail.com9841249759">
            <%@ include file="/WEB-INF/jsp/includes/headerSecond_license.jsp" %> 
            <div id="body">
                <div id="breadcrumbs">
                    <span>You are here:</span>
                    <strong>Upload License</strong>
                </div>
                <div id="content">
                    <!--Div Content Start-->

                    <c:if test="${not empty pageError}">
                        <div id="pageError">${pageError}</div>
                    </c:if>
                    <c:if test="${not empty pageMessage}">
                        <div id="pageMessage">${pageMessage}</div>
                    </c:if>
                    <br/>
                    <!--                    <h2>Form</h2>-->
                    <fieldset>
                        <legend> Upload License </legend>
                        <br/>

                        <h1>Please Wait !</h1>
                        <br/><br/>
                        <a href="${initParam.PROJECT_ROOT_PATH}/license/" id="autoid">Redirecting To License Registration Page. (If link is not forwarded then please click here)</a>
                    </fieldset>

                    <!--Div Content End-->
                    <br/><br/><br/><br/>
                </div>
                <div class="clear"></div>
            </div>
            <%@ include file="/WEB-INF/jsp/includes/footerSecondLast.jsp" %>    
    </body>
    <%@ include file="/WEB-INF/jsp/includes/footerLast.jsp" %>       
