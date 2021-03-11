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
                <a href="#">Location</a> &raquo;
                <strong>Schedulers</strong>
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
                    <legend>Scheduler</legend>



                    <form name="form1" method="post" action="${initParam.PROJECT_ROOT_PATH}/schedulerman/schedule/parseJSONURL/submit">
                         <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}" />

                        <br/>

                        <p> 
                            <label for="searchText">Command : </label>

                            <input name="send" class="formbutton" value="Sync Now" type="submit"/></p>

                    </form>

                    <form  name="form2" method="post" action="${initParam.PROJECT_ROOT_PATH}/schedulerman/schedule/update_schedule_status/submit">

                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}" />


                        <p> 
                            <label for="searchText">Auto Sync : </label>
                            <input type="checkbox" name="enabled" id="enabled" <c:if test="${taskResource.active}">checked</c:if> > Enabled
                                    <input name="send" class="formbutton" value="Send" type="submit" />
                                    <input type="hidden" name="name"
                                           value="${taskResource.name}" />






                        </p>
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