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

    <body class="noheader">
        <input type="hidden" value="SamritNarshingAmatya@samrit_narshing@hotmail.com9841249759">
        <%--<%@ include file="/WEB-INF/jsp/includes/headerSecond.jsp" %>--%> 
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

                    <form  name="form1" method="post" action="${initParam.PROJECT_ROOT_PATH}/license/submitUploadLicenseKey"  enctype="multipart/form-data">
                        <p>
                            <label for="searchText">Upload Valid License Key : </label>
                            &nbsp;&nbsp;<input name="file" type="file" id="keyfile" style="width: 440px"/>
                            <br/>
                            <p><input name="send" class="formbutton" value="Upload" type="submit" onClick="return confirmSubmit_Upload()"/></p>

                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

                    </form>
                </fieldset>

                <!--Div Content End-->
                <br/><br/><br/><br/>
            </div>
            <div class="clear"></div>
        </div>
        <%@ include file="/WEB-INF/jsp/includes/footerSecondLast.jsp" %>    
    </body>
    <%@ include file="/WEB-INF/jsp/includes/footerLast.jsp" %>       