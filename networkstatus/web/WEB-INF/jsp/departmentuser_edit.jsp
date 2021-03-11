<%@ include file="/WEB-INF/jsp/includes/tagLibs.jsp" %> 
<%@page session="true"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <%@ include file="/WEB-INF/jsp/includes/headerFirst.jsp" %> 
    <body class="noheader">
        <%@ include file="/WEB-INF/jsp/includes/headerSecond.jsp" %> 
        <style type="text/css">
            fieldset p label {
                float: left;
                width: 200px;
            }
        </style>
        <div id="body">
            <div id="breadcrumbs">
                <span>You are here:</span>
                <a href="${initParam.PROJECT_ROOT_PATH}/home">Home</a> &raquo;
                <a href="#">Department User Management</a> &raquo;
                <strong>Edit Department User</strong>
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
                    <legend>Edit Department User</legend>

                    <!--align="center"-->
                    <br/>
                    <p><label for="firstName">Profile Picture :</label>
                        <div class="profileimage">
                            <img src="${webServerDomainURL}/rest/web/filehandler/download/image/profile/${departmentUserResource.username}" alt="Smiley face" height="300" >
                        </div>
                    </p>

                    <form method="POST" action="${initParam.PROJECT_ROOT_PATH}/departmentuserman/departmentuser/edit/image/upload/submit" enctype="multipart/form-data">
                        <p><label for="firstName">Change Profile Picture :</label>
                            <input type="file" name="file" /><br/>
                            <input type="hidden" name="id"
                                   value="${departmentUserResource.userID}" />
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        </p>
                        <p><input type="submit" value="Upload It (Should be less than 1 MB)" class="formbutton" /></p>
                    </form>

                    <form  name="form1" method="post" action="${initParam.PROJECT_ROOT_PATH}/departmentuserman/departmentuser/edit/submit">
                        <br/>

                        <p><label for="firstName">Full Name :</label>
                            <input name="firstName" type="text" value="${departmentUserResource.firstName}" size="15"/>

                            <font color="red"><c:out value="${departmentUserResource.errFirstName}"/></font> 
                            &nbsp;&nbsp;
                            <input name="middleName" type="text" value="${departmentUserResource.middleName}" size="15"/>

                            <font color="red"><c:out value="${departmentUserResource.errMiddleName}"/></font> 
                            &nbsp;&nbsp;
                            <input name="lastName" type="text" value="${departmentUserResource.lastName}" size="15"/>

                            <font color="red"><c:out value="${departmentUserResource.errLastName}"/></font> 
                            &nbsp;&nbsp;

                            <br />
                        </p>


                        <p><label for="username">Username:</label>
                            <input name="username" type="text" value="${departmentUserResource.username}" readonly="true">
                                &nbsp;&nbsp;
                                <font color="red"><c:out value="${departmentUserResource.errUsername}"/></font> 
                                <br />
                        </p>	


                        <p><label for="address1">Address 1: </label>
                            <input name="address1" id="address1" value="${departmentUserResource.address1}"  size="40">
                                <br />
                        </p>

                        <c:if test="${not empty departmentUserResource.errAddress1}">        
                            <p><label> &nbsp;&nbsp; </label>
                                <font color="red"><c:out value="${departmentUserResource.errAddress1}"/></font> 
                                <br />
                            </p>
                        </c:if> 


                        <p><label for="address2">Address 2: </label>
                            <input name="address2" id="address2" value="${departmentUserResource.address2}"  size="40">
                                <br />
                        </p>

                        <c:if test="${not empty departmentUserResource.errAddress2}">        
                            <p><label> &nbsp;&nbsp; </label>
                                <font color="red"><c:out value="${departmentUserResource.errAddress2}"/></font> 
                                <br />
                            </p>
                        </c:if> 





                        <p><label for="phoneNo">Phone No: </label>
                            <input name="phoneNo" id="phoneno" value="${departmentUserResource.phoneNo}"  size="40">
                                <br />
                        </p>

                        <c:if test="${not empty departmentUserResource.errPhoneNo}">        
                            <p><label> &nbsp;&nbsp; </label>
                                <font color="red"><c:out value="${departmentUserResource.errPhoneNo}"/></font> 
                                <br />
                            </p>
                        </c:if> 

                        <p><label for="mobileNo">Mobile No: </label>
                            <input name="mobileNo" id="address2" value="${departmentUserResource.mobileNo}"  size="40">
                                <br />
                        </p>

                        <c:if test="${not empty departmentUserResource.errMobileNo}">        
                            <p><label> &nbsp;&nbsp; </label>
                                <font color="red"><c:out value="${departmentUserResource.errMobileNo}"/></font> 
                                <br />
                            </p>
                        </c:if> 

                        <p><label for="email">Email: </label>
                            <input name="email" id="email" value="${departmentUserResource.email}"  size="40">
                                <br />
                        </p>

                        <c:if test="${not empty departmentUserResource.errEmail}">        
                            <p><label> &nbsp;&nbsp; </label>
                                <font color="red"><c:out value="${departmentUserResource.errEmail}"/></font> 
                                <br />
                            </p>
                        </c:if> 



                        <p><label for="socialID">Social ID: </label>
                            <input name="socialID" id="socialID" value="${departmentUserResource.socialID}"  size="40">
                                &nbsp;&nbsp;
                                <font color="red"><c:out value="${departmentUserResource.errSocialID}"/></font>

                                <br />
                        </p>

                        <p><label for="departmentUserField">Department User Field: </label>
                            <input name="departmentUserField" id="departmentUserField" value="${departmentUserResource.departmentUserField}"  size="40">
                                &nbsp;&nbsp;
                                <font color="red"><c:out value="${departmentUserResource.errDepartmentUserField}"/></font> 
                                <br />
                        </p>


                        <p>
                            <label for="gender">Gender </label>

                            <select name="gender" size="1" >
                                <option value="person-male" ${departmentUserResource.gender == 'person-male' ? 'selected': ''}> Male </option>
                                <option value="person-female" ${departmentUserResource.gender == 'person-female' ? 'selected': ''}> Female </option>
                            </select>

                            <br/>
                        </p>


                        <p><label for="enabled">Status:</label>
                            <label><input type="checkbox" name="enabled" id="enabled" <c:if test="${departmentUserResource.enabled}">checked</c:if> /> Enabled</label>

                                <br />
                            </p>	


                            <p><label for="role">Roles:</label>

                            <c:forEach var="role" items="${roleResource}" varStatus="loop">

                                <c:if test="${loop.index!=0}"> <label> &nbsp;&nbsp; </label> </c:if>
                                <input type="checkbox" name="selectedRoles" value="${role.roleID}" <c:if test="${role.selected}">checked</c:if>/> ${role.description}


                                <c:if test="${loop.index==0}"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red"><c:out value="${departmentUserResource.errRole}"/></font>  </c:if>

                                    <br />
                            </c:forEach>

                            <!--                            &nbsp;&nbsp;
                                                        <font color="red"><c:out value="${departmentUserResource.errRole}"/></font>
                                                        <br />-->
                        </p>




                        <p><label for="sessionTimeout">Session Timeout:</label>
                            <input name="sessionTimeout" type="text" value="${departmentUserResource.sessionTimeout}">
                                &nbsp;&nbsp;
                                <font color="red"><c:out value="${departmentUserResource.errSessionTimeout}"/></font>
                                <br />
                        </p>

                        <p><label for="accountExpiration">Account Expiration:</label>

                            <div class="cssreset">
                                <table class="restore_default_form_element_style">
                                    <tr>
                                        <td><script>DateInput('accountExpiration', true, 'YYYY-MM-DD', '${departmentUserResource.accountExpiration}');</script></td>
                                    </tr>

                                </table>
                            </div>


                        </p>

                        <p><label for="neverExpire">Never Expires:</label>
                            <label> <input type="checkbox" name="neverExpire" id="neverExpire" <c:if test="${departmentUserResource.neverExpire}">checked</c:if> /> </label>
                                <br />
                            </p>


                            <p><input name="send" class="formbutton" value="Send" type="submit" /></p>

                            <input name="id" type="hidden" value="${departmentUserResource.userID}"/>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
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


