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
                <a href="#">System User Management</a> &raquo;
                <strong>Add New System User</strong>
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
                    <legend>Add New System User</legend>
                    <form  name="form1" method="post" action="${initParam.PROJECT_ROOT_PATH}/userman/user/add/submit">
                        <br/>

                        <p><label for="firstName">Full Name :</label>
                            <input name="firstName" type="text" value="${userResource.firstName}" size="15"/>

                            <font color="red"><c:out value="${userResource.errFirstName}"/></font> 
                            &nbsp;&nbsp;
                            <input name="middleName" type="text" value="${userResource.middleName}" size="15"/>

                            <font color="red"><c:out value="${userResource.errMiddleName}"/></font> 
                            &nbsp;&nbsp;
                            <input name="lastName" type="text" value="${userResource.lastName}" size="15"/>

                            <font color="red"><c:out value="${userResource.errLastName}"/></font> 
                            &nbsp;&nbsp;

                            <br />
                        </p>	

                        <p><label for="username">Username:</label>
                            <input name="username" type="text" value="${userResource.username}" size="40">
                                &nbsp;&nbsp;
                                <font color="red"><c:out value="${userResource.errUsername}"/></font> 
                                <br />
                        </p>	
                        <p><label for="password">Password:</label>
                            <input name="password" type="password" value="" size="40">
                                &nbsp;&nbsp;
                                <font color="red"><c:out value="${userResource.errPassword}"/></font>
                                <br />
                        </p>	
                        <p><label for="confirmPassword">Confirm Password:</label>
                            <input name="confirmPassword" type="password" value="" size="40">
                                &nbsp;&nbsp;
                                <font color="red"><c:out value="${userResource.errConfirmPassword}"/></font>
                                <br />
                        </p>




                        <p><label for="address1">Address 1: </label>
                            <input name="address1" id="address1" value="${userResource.address1}"  size="40">
                                &nbsp;&nbsp;
                                <font color="red"><c:out value="${userResource.errAddress1}"/></font>
                                <br />
                        </p>


                        <p><label for="address2">Address 2: </label>
                            <input name="address2" id="address2" value="${userResource.address2}"  size="40">
                                &nbsp;&nbsp;
                                <font color="red"><c:out value="${userResource.errAddress2}"/></font>
                                <br />
                        </p>



                        <p><label for="phoneNo">Phone No: </label>
                            <input name="phoneNo" id="phoneno" value="${userResource.phoneNo}"  size="40">
                                &nbsp;&nbsp;
                                <font color="red"><c:out value="${userResource.errPhoneNo}"/></font>
                                <br />
                        </p>


                        <p><label for="mobileNo">Mobile No: </label>
                            <input name="mobileNo" id="address2" value="${userResource.mobileNo}"  size="40">
                                &nbsp;&nbsp;
                                <font color="red"><c:out value="${userResource.errMobileNo}"/></font>
                                <br />
                        </p>



                        <p><label for="email">Email: </label>
                            <input name="email" id="email" value="${userResource.email}"  size="40">
                                &nbsp;&nbsp;
                                <font color="red"><c:out value="${userResource.errEmail}"/></font>
                                <br />
                        </p>


                        <p>
                            <label for="gender">Gender </label>

                            <select name="gender" size="1" >
                                <option value="person-male" ${userResource.gender == 'person-male' ? 'selected': ''}> Male </option>
                                <option value="person-female" ${userResource.gender == 'person-female' ? 'selected': ''}> Female </option>
                            </select>

                            <br/>
                        </p>


                        <p><label for="enabled">Status:</label>
                            <label><input type="checkbox" name="enabled" id="enabled" <c:if test="${userResource.enabled}">checked</c:if> /> Enabled</label>
                                <br/>
                            </p>	


                            <p><label for="role">Roles:</label>

                            <c:forEach var="role" items="${roleResource}" varStatus="loop">

                                <c:if test="${loop.index!=0}"> <label> &nbsp;&nbsp; </label> </c:if>
                                <input type="checkbox" name="selectedRoles" value="${role.roleID}" <c:if test="${role.selected}">checked</c:if>/> ${role.description}


                                <c:if test="${loop.index==0}"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red"><c:out value="${userResource.errRole}"/></font>  </c:if>

                                    <br />
                            </c:forEach>

                            <!--                            &nbsp;&nbsp;
                                                        <font color="red"><c:out value="${userResource.errRole}"/></font>
                                                        <br />-->
                        </p>




                        <p><label for="sessionTimeout">Session Timeout:</label>
                            <input name="sessionTimeout" type="text" value="${userResource.sessionTimeout}">
                                &nbsp;&nbsp;
                                <font color="red"><c:out value="${userResource.errSessionTimeout}"/></font>
                                <br />
                        </p>

                        <p><label for="accountExpiration">Account Expiration:</label>

                            <div class="cssreset">
                                <table class="restore_default_form_element_style">
                                    <tr>
                                        <td><script>DateInput('accountExpiration', true, 'YYYY-MM-DD', '${userResource.accountExpiration}');</script></td>
                                    </tr>

                                </table>
                            </div>


                        </p>

                        <p><label for="neverExpire">Never Expires:</label>
                            <label> <input type="checkbox" name="neverExpire" id="neverExpire" <c:if test="${userResource.neverExpire}">checked</c:if> /> </label>
                                <br />
                            </p>

                            <p><label for="passwordExpire">Request To Change Password:</label>
                                <label> <input type="checkbox" name="passwordExpire" id="passwordExpire" <c:if test="${userResource.passwordExpire}">checked</c:if> /> </label>
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