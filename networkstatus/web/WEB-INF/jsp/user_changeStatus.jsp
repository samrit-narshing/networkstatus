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
                <strong>Change Status</strong>
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
                    <legend>Change Status</legend>
                    <form  name="form1" method="post" action="${initParam.PROJECT_ROOT_PATH}/userman/submitChangeStatus">
                        <br/>
                        <p><label for="userName">Username:</label>
                            ${user.userName}
                            &nbsp;&nbsp;
                            <font color="red"><c:out value="${user.errUserName}"/></font>
                            <br />
                        </p>
                        <p><label for="isEnabledText">Status:</label>
                            <label><input type="checkbox" name="isEnabledText" id="isEnabledText" <c:if test="${user.enabled}">checked</c:if> />Enabled</label>
                                &nbsp;&nbsp;
                                <font color="red"><c:out value="${model.errEnabled}"/></font>
                            <br />
                        </p>
                        <p><input name="send" class="formbutton" value="Send" type="submit" /></p>
                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}" />
                        <input name="id" type="hidden" value="${user.id}"/>
                        <input name="userName" type="hidden" value="${user.userName}"/>
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



