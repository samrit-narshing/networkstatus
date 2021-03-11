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
                User Management &raquo;
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
                    <legend>Change Password</legend>
                    <form name="form1" method="post" action="${initParam.PROJECT_ROOT_PATH}/userman/user/changePassword/submit">
                        <br/>
                        <p><label for="username">Username:</label>
                            <input name="username" type="text" value="${userResource.username}" readonly="true" >
                                <br />
                        </p>	
                        <p><label for="currentPassword">Current Password:</label>
                            <input name="currentPassword" type="password">
                                &nbsp;&nbsp;
                                <font color="red"><c:out value="${userResource.errCurrentPassword}"/></font>
                                <br />
                        </p>	
                        <p><label for="password">New Password:</label>
                            <input name="password" type="password" value="" placeholder="Enter Text Here">
                                &nbsp;&nbsp;
                                <font color="red"><c:out value="${userResource.errPassword}"/></font>
                                <br />
                        </p>	
                        <p><label for="confirmPassword">Confirm Password:</label>
                            <input name="confirmPassword" type="password" value="" placeholder="Enter Text Here"/>
                                &nbsp;&nbsp;
                                <font color="red"><c:out value="${userResource.errConfirmPassword}"/></font>
                                <br />
                        </p>
                        <p><input name="send" class="formbutton" value="Send" type="submit" /></p>
                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}" />
                        <input name="id" type="hidden" value="${userResource.userID}"/>
                        <input type="hidden" name="userID" value="${userResource.userID}" />
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



