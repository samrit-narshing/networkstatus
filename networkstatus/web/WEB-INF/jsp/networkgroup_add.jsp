<%@ include file="/WEB-INF/jsp/includes/tagLibs.jsp" %> 
<%@page session="true"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <%@ include file="/WEB-INF/jsp/includes/headerFirst.jsp" %> 

    <style type="text/css">
        fieldset p label {
            float: left;
            width: 200px;
        }
    </style>

    <body class="noheader">
        <%@ include file="/WEB-INF/jsp/includes/headerSecond.jsp" %> 
        <div id="body">
            <div id="breadcrumbs">
                <span>You are here:</span>
                <a href="${initParam.PROJECT_ROOT_PATH}/home">Home</a> &raquo;
                <a href="#">Network Group Management</a> &raquo;
                <strong>Add New Network Group</strong>
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
                    <legend>Add New Network Group</legend>
                    <form  name="form1" method="post" action="${initParam.PROJECT_ROOT_PATH}/networkgroupman/networkgroup/add/submit">
                        <br/>



                        <p><label for="name">Group Name:</label>
                            <input name="name" type="text" value="${networkGroupResource.name}" size="40">
                                &nbsp;&nbsp;
                                <font color="red"><c:out value="${networkGroupResource.errName}"/></font> 
                                <br />
                        </p>	





                        <p><label for="description">Description: </label>
                            <input name="description" id="description" value="${networkGroupResource.description}"  size="40">
                                &nbsp;&nbsp;
                                <font color="red"><c:out value="${networkGroupResource.errDescription}"/></font>
                                <br />
                        </p>



                        <p><input name="send" class="formbutton" value="Send" type="submit" /></p>

                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}" />
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