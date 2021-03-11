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

                <c:if test="${not empty pageError}">
                    <div id="pageError">${pageError}</div>
                </c:if>
                <c:if test="${not empty pageMessage}">
                    <div id="pageMessage">${pageMessage}</div>
                </c:if>
                <br/>


                <div class="centerbar">
                    <ul>	
                        <li>
                            <h4 class="h4-green"><span>Upload CSV File For Node Entry</span></h4>
                            <ul>
                                <li>
                                    <p style="margin: 0;">

                                        <br/>


                                        <form method="POST" action="${initParam.PROJECT_ROOT_PATH}/nodeman/node/add/upload_csv/upload/submit" enctype="multipart/form-data" >
                                            <p>
                                                <input type="file" name="file" accept=".csv"/><br/>
                                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                            </p>
                                            <p><input type="submit" value="Upload It (Should be less than 1 MB)" class="formbutton" /></p>
                                            <p><font color="red"><c:out value=""/></font> </p>

                                        </form>

                                        <h1>Import Network Info's CSV File.</h1>

                                        <br/>
                                        <c:if test="${not empty pageError}">
                                            <h5>Status : ${pageError}</h5>
                                        </c:if>
                                        <c:if test="${not empty pageMessage}">
                                            <h5>Status : ${pageMessage}</h5>
                                        </c:if>



                                        <c:if test="${not empty pageMessage}">


                                            <form  name="form1" method="post" action="${initParam.PROJECT_ROOT_PATH}/nodeman/node/add/upload_csv/upload/display">
                                                <br/>

                                                <p> <input name="send" class="formbutton" value="Click Here To Proceed Importing Into Database" type="submit"  style="width: 50%"/> </p>

                                                <input type="hidden" name="${_csrf.parameterName}"
                                                       value="${_csrf.token}" />
                                            </form>

                                        </c:if>



                                    </p>


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


