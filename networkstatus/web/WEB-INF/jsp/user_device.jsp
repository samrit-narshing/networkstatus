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
                <strong>Edit User</strong>
            </div>
            <div id="content">
                <!--Div Content Start-->
  <blockquote>
                        <p>
                            <font color="red">Note : If none of a single device is assigned to the user, then that user will have privileges to access all devices .</font>
                        </p>
                    </blockquote>





                <c:if test="${not empty pageError}">
                    <div id="pageError">${pageError}</div>
                </c:if>
                <c:if test="${not empty pageMessage}">
                    <div id="pageMessage">${pageMessage}</div>
                </c:if>
                <br/>

                <!--                    <h2>Form</h2>-->
                <fieldset>
                    <legend>User Assigned Devices</legend>


                    <br/><br/>
                    <p> <label for="userName">&nbsp;&nbsp;&nbsp;User:</label>
                        <input name="userName" type="text" value="${user.userName}" readonly="true" style="border-width: 0px ">
                            &nbsp;&nbsp;
                            <font color="red"><c:out value="${user.errUserName}"/></font>
                            <br />
                    </p>




                    <table width="100%" id="results">



                        <tbody>

                            <tr>
                                <td style="width: 50%; vertical-align: top">

                                    <form action="${initParam.PROJECT_ROOT_PATH}/userman/submitSearchAvailableDevice" method="post">
                                        <p>
                                            <label for="availableLogDeviceName">Search Available Device : </label>
                                            &nbsp;<input name="availableLogDeviceName" type="text" size="25" value="${availableLogDeviceName}"/>
                                            <input type="hidden" name="userName"
                                                   value="${user.userName}" />
                                            <br/>
                                        </p>

                                        <p><input name="search" class="formbutton" value="Search" type="submit"/> </p>
                                        <input type="hidden" name="${_csrf.parameterName}"
                                               value="${_csrf.token}" />
                                    </form>




                                    <table width="100%" id="availableDeviceResults">

                                        <caption><strong> Available Devices </strong></caption>

                                        <tbody>
                                            <thead>
                                                <tr>
                                                    <th align="center">Device name</th>
                                                    <th align="center">IP address</th>
                                                    <th align="center">Commands</th>
                                                </tr>
                                            </thead>

                                            <c:forEach items="${availableLoggerDevices}"
                                                       var="senderip"
                                                       varStatus="counter"
                                                       >
                                                <tr class="odd" align="center">
                                                    <td><c:out value="${senderip.deviceName}" /></td>
                                                    <td><c:out value="${senderip.ip}" /></td>
                                                    <td align="center">


                                                        &nbsp;
                                                        <a href="javascript:formSubmitById('addAssignedFormId_${senderip.id}')" title="Assign Device"> 
                                                            <img src="${initParam.PROJECT_ROOT_PATH}/resources/icons/round_add_32x32.png" alt="Checked" border="0" width="20" height="20" />
                                                        </a>

                                                        &nbsp;


                                                        <div style="display: none">

                                                            <form action="${initParam.PROJECT_ROOT_PATH}/userman/submitAddAssignedDevice" method="post" id="addAssignedFormId_${senderip.id}">
                                                                <input type="hidden" name="${_csrf.parameterName}"
                                                                       value="${_csrf.token}" />
                                                                <input type="hidden" name="deviceID"
                                                                       value="${senderip.id}" />
                                                                <input type="hidden" name="userName"
                                                                       value="${user.userName}" />
                                                            </form>

                                                        </div>


                                                    </td>
                                                </tr>

                                            </c:forEach>

                                        </tbody>

                                    </table>
                                    <br>
                                        <div id="availableDevicePageNavPosition" align="center"></div>
                                        <script type="text/javascript">
                                            var availableDevicePager = new Pager('availableDeviceResults', 5);
                                            availableDevicePager.init();
                                            availableDevicePager.showPageNav('availableDevicePager', 'availableDevicePageNavPosition');
                                            availableDevicePager.showPage(1);
                                        </script>

                                </td>

                                <td style="width: 50%; vertical-align: top">


                                    <form action="${initParam.PROJECT_ROOT_PATH}/userman/submitSearchAssignedDevice" method="post">
                                        <p>
                                            <label for="logDeviceName">Search Assigned Device : </label>
                                            &nbsp;<input name="assignedLogDeviceName" type="text" size="25" value="${assignedLogDeviceName}"/>
                                            <input type="hidden" name="userName"
                                                   value="${user.userName}" />
                                            <br/>
                                        </p>

                                        <p><input name="search" class="formbutton" value="Search" type="submit"/> </p>
                                        <input type="hidden" name="${_csrf.parameterName}"
                                               value="${_csrf.token}" />
                                    </form>

                                    <table width="100%" id="assignedDeviceResults">

                                        <caption><strong> Assigned Devices </strong></caption>

                                        <tbody>
                                            <thead>
                                                <tr>
                                                    <th align="center">Device name</th>
                                                    <th align="center">IP address</th>
                                                    <th align="center">Commands</th>
                                                </tr>
                                            </thead>

                                            <c:forEach items="${assignedLoggerDevices}"
                                                       var="senderip"
                                                       varStatus="counter"
                                                       >
                                                <tr class="odd" align="center">
                                                    <td><c:out value="${senderip.deviceName}" /></td>
                                                    <td><c:out value="${senderip.ip}" /></td>
                                                    <td align="center">


                                                        &nbsp;
                                                        <a href="javascript:formSubmitById('deleteAssignedFormId_${senderip.id}')" title="Remove Assgined Device"> 
                                                            <img src="${initParam.PROJECT_ROOT_PATH}/resources/icons/round_remove_32x32.png" alt="Checked" border="0" width="20" height="20" />
                                                        </a>

                                                        &nbsp;


                                                        <div style="display: none">

                                                            <form action="${initParam.PROJECT_ROOT_PATH}/userman/submitDeleteAssignedDevice" method="post" id="deleteAssignedFormId_${senderip.id}">
                                                                <input type="hidden" name="${_csrf.parameterName}"
                                                                       value="${_csrf.token}" />
                                                                <input type="hidden" name="deviceID"
                                                                       value="${senderip.id}" />
                                                                <input type="hidden" name="userName"
                                                                       value="${user.userName}" />
                                                            </form>

                                                        </div>


                                                    </td>
                                                </tr>

                                            </c:forEach>

                                        </tbody>

                                    </table>
                                    <br>
                                        <div id="assignedDevicePageNavPosition" align="center"></div>
                                        <script type="text/javascript">
                                            var assignedDevicePager = new Pager('assignedDeviceResults', 5);
                                            assignedDevicePager.init();
                                            assignedDevicePager.showPageNav('assignedDevicePager', 'assignedDevicePageNavPosition');
                                            assignedDevicePager.showPage(1);
                                        </script>
                                </td>
                            </tr>
                        </tbody>

                    </table>


                </fieldset>


                <br/><br/><br/>




                <div style="text-align: center">
                    <a href="${initParam.PROJECT_ROOT_PATH}/userman/listUser/1/*">
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


