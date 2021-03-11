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
                <a href="#"> Role Management</a> &raquo;
                <strong>Edit Role</strong>
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
                    <legend>Edit System Role</legend>

                    <!--align="center"-->
                    <br/>

                    <form  name="form1" method="post" action="${initParam.PROJECT_ROOT_PATH}/roleman/role/edit/submit">
                        <br/>

                            <p><label for="name">Name </label>
                            <input name="name" type="text" value="${roleResource.name}" readonly="true">
                               
                                <br />
                        </p>	
                        
                        <p><label for="description">Description: </label>
                            <input name="description" type="text" value="${roleResource.description}">
                                &nbsp;&nbsp;
                                <font color="red"><c:out value="${roleResource.errDescription}"/></font> 
                                <br />
                        </p>	

                        <p><label for="enabled">Status:</label>
                            <label><input type="checkbox" name="enabled" id="enabled" <c:if test="${roleResource.enabled}">checked</c:if> /> Enabled</label>

                                <br />
                            </p>	

                            <p><input name="send" class="formbutton" value="Send" type="submit" /></p>

                            <input name="id" type="hidden" value="${roleResource.roleID}"/>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    </form>
                </fieldset>

                <div style="text-align: center">
                    <a href="${initParam.PROJECT_ROOT_PATH}/roleman/role/search">
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


