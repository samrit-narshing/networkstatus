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




                <div class="centerbar">
                    <ul>	
                        <li>
                            <h4 class="h4-green"><span>User Data Entry Status</span></h4>
                            <ul>
                                <li>
                                    <p style="margin: 0;">

                                        <br/>

                                        <p>
                                            <div align="center">
                                                <div class="profileimage">
                                                    <img src="${webServerDomainURL}/rest/web/filehandler/download/image/profile/${userResource.username}" alt="Smiley face" height="300" >
                                                </div>
                                            </div>
                                        </p>

                                        <form method="POST" action="${initParam.PROJECT_ROOT_PATH}/userman/user/add/image/upload/submit" enctype="multipart/form-data">
                                            <p>
                                                <input type="file" name="file" /><br/>
                                                <input type="hidden" name="id"
                                                       value="${userResource.userID}" />
                                                <input type="hidden" name="newPassword"
                                                       value="${userResource.password}" />
                                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                            </p>
                                            <p><input type="submit" value="Upload It (Should be less than 1 MB)" class="formbutton" /></p>
                                            <p><font color="red"><c:out value="${userResource.errProfileImage}"/></font> </p>

                                        </form>

                                        <h1>User Added Successfully.</h1>

                                        <br/>
                                        <h5>Status : Success</h5>

                                        <h5>
                                            UserName : ${userResource.username}
                                        </h5>
                                        <h5>
                                            Password : ${userResource.password}
                                        </h5>

                                        <h5>
                                            Email : ${userResource.email}
                                        </h5>


                                        <p>
                                            <h3><a href="${initParam.PROJECT_ROOT_PATH}/userman/user/add"> Add New User</a> &nbsp; &nbsp; &nbsp;

                                                <a href="javascript:formSubmitById('emailFormId_${userResource.userID}')" onclick="return messageforadmin('${userResource.userID}');"  title="Email User">
                                                    Send Email
                                                </a>


<!--                                                <form action="${initParam.PROJECT_ROOT_PATH}/userman/user/email/submit" method="post" id="emailFormId_${userResource.userID}">
                                                    <input type="hidden" name="${_csrf.parameterName}"
                                                           value="${_csrf.token}" />
                                                    <input type="hidden" name="id"
                                                           value="${professorResource.userID}" />
                                                    <input type="hidden" name="password"
                                                           value="${professorResource.password}" />
                                                </form>-->

                                                <form action="${initParam.PROJECT_ROOT_PATH}/userman/user/send_email/submit" method="post" id="emailFormId_${userResource.userID}">
                                                    <input type="hidden" name="${_csrf.parameterName}"
                                                           value="${_csrf.token}" />
                                                    <input type="hidden" name="id"
                                                           value="${userResource.userID}" />
                                                    <input type="hidden" name="password"
                                                           value="${userResource.password}" />

                                                    <input type="hidden" name="url" id ='url' value=""
                                                           />

                                                </form>

                                                <script>
                                                    var loginURL = window.location.origin + "${initParam.PROJECT_ROOT_PATH}/login";
                                                    document.getElementById('url').value = loginURL;

                                                </script>

<!--                                                <a href="${initParam.PROJECT_ROOT_PATH}/professorman/professor/add"> Send Email</a></p>-->

                                            </h3>

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


