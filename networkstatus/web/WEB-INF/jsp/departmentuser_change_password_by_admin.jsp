<%@ include file="/WEB-INF/jsp/includes/tagLibs.jsp" %> 
<%@page session="true"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <%@ include file="/WEB-INF/jsp/includes/headerFirst.jsp" %> 
    <body class="noheader">
        <%@ include file="/WEB-INF/jsp/includes/headerSecond.jsp" %> 
        <div id="body">
            <div id="breadcrumbs">
                <span>You are here:</span>
                <a href="${initParam.PROJECT_ROOT_PATH}/home">Home</a> &raquo;
                <a href="#">User Management</a> &raquo;
                <a href="${initParam.PROJECT_ROOT_PATH}/userman/listUser/1/0"> List Users</a> &raquo;
                <strong>Change Password</strong>
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
                    <legend>Change Parent Password</legend>
                    <form  name="form1" method="post" action="${initParam.PROJECT_ROOT_PATH}/departmentuserman/departmentuser/admin/change_password/submit">
                        <br/>
                        <p><label for="Username:">Username:</label>
                            
                            <input name="username" type="text" value="${departmentUserResource.username}" readonly="true" >
                            <br />
                        </p>
                        <p><label for="password">Password:</label>
                            <input name="password" type="password" value="" >
                                &nbsp;&nbsp;
                                <font color="red"><c:out value="${departmentUserResource.errPassword}"/></font>
                                <br />
                        </p>	
                        <p><label for="confirmPassword">Confirm Password:</label>
                            <input name="confirmPassword" type="password" value="">
                                &nbsp;&nbsp;
                                <font color="red"><c:out value="${departmentUserResource.errConfirmPassword}"/></font>
                                <br />
                        </p>		
                        <p><input name="send" class="formbutton" value="Send" type="submit" /></p>
                        <input name="id" type="hidden" value="${departmentUserResource.userID}"/>
                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}" />
                        
                    </form>
                </fieldset>


                <div style="text-align: center">
                    <a href="${initParam.PROJECT_ROOT_PATH}/departmentuserman/departmentuser/search">
                        <img src="${initParam.PROJECT_ROOT_PATH}/resources/icons/left_arrow_32x32.png" alt="Checked" border="0" width="20" height="20" />
                    </a>
                </div>



                <!--Div Content End-->
                <br/><br/><br/><br/>
            </div>
            <div class="clear"></div>
        </div>
        <%@ include file="/WEB-INF/jsp/includes/footerSecondLast.jsp" %>    
    </body>
    <%@ include file="/WEB-INF/jsp/includes/footerLast.jsp" %>     



