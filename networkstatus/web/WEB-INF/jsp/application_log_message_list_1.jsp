<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <%@ include file="/WEB-INF/jsp/includes/headerFirst_1.jsp" %> 

    <body>
        <style type="text/css">
            fieldset p label  {
                float: left;
                width: 200px;
            }


        </style>
        <%@ include file="/WEB-INF/jsp/includes/headerSecond_1.jsp" %> 

        <section class="title">
            <div class="container">
                <div class="row-fluid">
                    <div class="span6">
                        <h1>List Application Log Messages</h1>
                    </div>
                    <div class="span6">
                        <ul class="breadcrumb pull-right">
                            <li><a href="#">Settings</a> <span class="divider">/</span></li>
                            <li class="active">List Application Log Messages</li>
                        </ul>
                    </div>
                </div>
            </div>
        </section>
        <!-- / .title -->   

        <section class="container">


            <br/>

            <script type="text/javascript">
                function confirmRemove()
                {
                    var agree = confirm("Are you sure you want to Remove?");
                    if (agree)
                        return true;
                    else
                        return false;
                }


                function confirmContiune()
                {
                    var agree = confirm("Are you sure you want to contiune?");
                    if (agree)
                        return true;
                    else
                        return false;
                }


                function messageforadmin(uname)
                {
                    //                if (uname == 'admin')
                    //                {
                    //                    alert("Warning!!! This action is restricted for this user.");
                    //
                    //                    return false;
                    //                }
                    //                else
                    {
                        return confirmRemove();
                    }
                }


            </script>










            <div class="row-fluid" style="width: 100%">

                <h3>List Application Log Messages</h3>

                <div class="span12" >

                    <!--Div Content Start-->

                    <c:if test="${not empty pageMessage}">
                        <div class="status alert alert-success" style="display: block">${pageMessage}</div>
                    </c:if>

                    <c:if test="${not empty pageError}">
                        <div class="status alert alert-error" style="display: block">${pageError}</div>

                    </c:if>

                    <br/>

                </div>




                <div class="span11">
                    <h4>Search Form</h4>
                    <br>
                    <div class="status alert alert-success" style="display: none"></div>
                    <form name="form1" method="post" action="${initParam.PROJECT_ROOT_PATH}/applicationlogman/applicationlog_1/search/submit">
                        <div class="row-fluid">
                            <div class="span8">

                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

                                <br/>
                                <label for="searchText">Search Text:</label>

                                <input type="text" name="searchText" id="searchText" placeholder="Your Search Text" value="${searchCriteria.searchText}"/><br />
                                <font color="red"><c:out value="${searchCriteria.errSearchText}"/></font>



                                <label for="searchText">Date From: </label>
                                <script>DateInput('dateFrom', true, 'YYYY-MM-DD', '${searchCriteria.dateFrom}')</script>
                                <label> Date To:</label></td>
                                <script>DateInput('dateTo', true, 'YYYY-MM-DD', '${searchCriteria.dateTo}')</script>



                                <label for="searchText">Hour From: </label>

                                <select name="hourFrom" size="1" >
                                    <c:forEach var="j" begin="0" end="23" varStatus="status">
                                        <option value="${j}"  ${searchCriteria.hourFrom == j ? 'selected': ''}> ${j} </option>
                                    </c:forEach>
                                </select>
                                :
                                <select name="minuteFrom" size="1" >
                                    <c:forEach var="l" begin="0" end="59" varStatus="status">
                                        <option value="${l}"  ${searchCriteria.minuteFrom == l ? 'selected': ''}> ${l} </option>
                                    </c:forEach>
                                </select>


                                <label for="searchText">Hour To: </label>



                                <select name="hourTo" size="1" >
                                    <c:forEach var="j" begin="0" end="23" varStatus="status">
                                        <option value="${j}"  ${searchCriteria.hourTo == j ? 'selected': ''}> ${j} </option>
                                    </c:forEach>
                                </select>
                                :
                                <select name="minuteTo" size="1" >
                                    <c:forEach var="l" begin="0" end="59" varStatus="status">
                                        <option value="${l}"  ${searchCriteria.minuteTo == l ? 'selected': ''}> ${l} </option>
                                    </c:forEach>
                                </select>


                                <label for="sortBy">Sort by:</label>


                                <select name="sortBy">
                                    <option value="0" ${searchCriteria.sortBy == 0 ? 'selected': ''}>Date Desc</option>
                                    <option value="1" ${searchCriteria.sortBy == 1 ? 'selected': ''}>Date Asc</option>
                                </select>




                                <!--                                            <label>Full Name</label>
                                                                            <input type="text" class="input-block-level" required="required" placeholder="Your Full Name" id="name" name="name">
                                                                            <label>Email Address</label>
                                                                            <input type="text" class="input-block-level" required="required" placeholder="Your Email Address" id="email" name="email">
                                                                            <label>Telephone</label>
                                                                            <input type="text" class="input-block-level" required="required" placeholder="Your Telephone" id="phone" name="phone">-->
                            </div>
                            <!--                        <div class="span7">
                                                        <label>Message</label>
                                                        <textarea name="message" id="message" required="required" class="input-block-level" rows="8"></textarea>
                                                    </div>-->
                        </div>
                        <button type="submit" class="btn btn-primary btn-large pull-right">Send Request</button>
                    </form>


                </div>

                <div class="span12">    
                    <br>
                    <form name="form1" method="post" action="${initParam.PROJECT_ROOT_PATH}/applicationlogman/applicationlog_1/delete/all/submit">
                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}" />


                        <input name="send" class="btn btn-primary btn-large pull-right" value="Delete All Log Message" type="submit" style="width: 100%"  onclick="return confirmContiune();" />
                    </form>
                </div>      


                <div class="span12">    
                    <br>
                    <form name="form1" method="post" action="${initParam.PROJECT_ROOT_PATH}/applicationlogman/applicationlog_1/dummyexecption/submit">
                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}" />


                        <input name="send" class="btn btn-primary btn-large pull-right" value="Send Dummy Exception" type="submit" style="width: 100%"  onclick="return confirmContiune();" />
                    </form>
                </div>   

                <div class="span12"> 
                    <br><br>
                    <h3>Application Log Messages</h3>

                    <table width="100%">
                        <tbody>

                            <tr>
                                <th align="center">Sender</th>
                                <th align="center">User</th>
                                <th align="center">Device Type</th>
                                <th align="center">Device Model</th>
                                <th align="center">Entry Date</th>
                                <th align="center">Log Message</th>
                                <th align="center">Commands</th>


                            </tr>


                            <c:forEach items="${applicationLogMessageListResource.applicationLogMessageResources}"
                                       var="application_log_message"
                                       varStatus="status"
                                       begin="0"
                                       end="1999"
                                       step="1">


                                <tr>
                                    <td align="center"><c:out value="${application_log_message.senderUsername}" /></td>
                                    <td align="center"><c:out value="${application_log_message.loggedUsername}" /></td>

                                    <td align="center"><c:out value="${application_log_message.deviceType}" /></td>

                                    <td align="center" style="width: 100px">
                                        <div style="overflow-y:auto;overflow-x:auto;height:200px;width:100px;font-size: 10px;">
                                            ${application_log_message.deviceModelName}
                                        </div>
                                    </td>





                                    <td align="center" style="width: 100px">
                                        <div style="overflow-y:auto;overflow-x:auto;height:200px;width:100px;font-size: 10px;">
                                            <jsp:useBean id="entryDateObject" class="java.util.Date" />
                                            <jsp:setProperty name="entryDateObject" property="time" value="${application_log_message.entryDate*1000}" />
                                            <fmt:formatDate value="${entryDateObject}" pattern="yyyy/MM/dd hh:mm a" />
                                        </div>
                                    </td>



                                    <td align="center" style="width: 500px">
                                        <div style="overflow-y:auto;overflow-x:auto;height:200px;width:490px;font-size: 10px;">
                                            ${application_log_message.logMessage}
                                        </div>
                                    </td>


                                    <td align="center">


                                        &nbsp;
                                        <a href="javascript:formSubmitById('deleteFormId_${application_log_message.tableId}')" onclick="return messageforadmin('${application_log_message.tableId}');"  title="Remove Log Message">
                                            <img src="${initParam.PROJECT_ROOT_PATH}/resources/icons/trash_32x32.png" alt="Checked" border="0" width="20" height="20" />
                                        </a>
                                        &nbsp;

                                        <form action="${initParam.PROJECT_ROOT_PATH}/applicationlogman/applicationlog_1/delete/submit" method="post" id="deleteFormId_${application_log_message.tableId}">
                                            <input type="hidden" name="${_csrf.parameterName}"
                                                   value="${_csrf.token}" />
                                            <input type="hidden" name="id"
                                                   value="${application_log_message.tableId}" />

                                        </form>


                                    </td>
                                </tr>

                            </c:forEach>

                        </tbody>

                    </table>

                    <!--Pagination-->
                    <div id="pagination" style="text-align: center;width: 100%" >
                        <br/>
                        <fmt:parseNumber var="totalPageNoParsed" value="${applicationLogMessageListResource.totalPages}" />

                        <c:if test="${searchCriteria.pageNo-2>0}">
                            <a href="javascript:formSubmitById('paginationFirstForm')" title="First"> 
                                <img src="${initParam.PROJECT_ROOT_PATH}/resources/icons/left_arrow_32x32.png" alt="Checked" border="0" width="20" height="10" />
                            </a>
                        </c:if>
                        &nbsp;
                        <c:if test="${searchCriteria.pageNo-1>0}">
                            <a href="javascript:formSubmitById('paginationPreviousForm')" title="Previous"> 
                                ${searchCriteria.pageNo-1}
                            </a>
                        </c:if>

                        &nbsp;
                        ${searchCriteria.pageNo}
                        &nbsp;

                        <c:if test="${searchCriteria.pageNo+1<=applicationLogMessageListResource.totalPages}">
                            <a href="javascript:formSubmitById('paginationNextForm')" title="Next"> 
                                ${searchCriteria.pageNo+1}
                            </a>
                        </c:if>

                        &nbsp;

                        <c:if test="${searchCriteria.pageNo+2<=applicationLogMessageListResource.totalPages}">
                            <a href="javascript:formSubmitById('paginationLastForm')" title="Last"> 
                                <img src="${initParam.PROJECT_ROOT_PATH}/resources/icons/right_arrow_32x32.png" alt="Checked" border="0" width="20" height="10" />
                            </a>
                        </c:if>
                        <br/>
                        <br/>



                        <form action="${initParam.PROJECT_ROOT_PATH}/applicationlogman/applicationlog_1/search/submit" method="post" id="paginationPreviousForm">
                            <input type="hidden" name="${_csrf.parameterName}"
                                   value="${_csrf.token}" />
                            <input name="name" type="hidden" value="${searchCriteria.searchText}"/>
                            <input name="pageNo" type="hidden" value="${searchCriteria.pageNo-1}"/>

                            <input name="dateFrom" type="hidden" value="${searchCriteria.dateFrom}"/>
                            <input name="dateTo" type="hidden" value="${searchCriteria.dateTo}"/>
                            <input name="hourFrom" type="hidden" value="${searchCriteria.hourFrom}"/>
                            <input name="minuteFrom" type="hidden" value="${searchCriteria.minuteFrom}"/>
                            <input name="hourTo" type="hidden" value="${searchCriteria.hourTo}"/>
                            <input name="minuteTo" type="hidden" value="${searchCriteria.minuteTo}"/>
                            <input name="sortBy" type="hidden" value="${searchCriteria.sortBy}"/>
                        </form>

                        <form action="${initParam.PROJECT_ROOT_PATH}/applicationlogman/applicationlog_1/search/submit" method="post" id="paginationNextForm">
                            <input type="hidden" name="${_csrf.parameterName}"
                                   value="${_csrf.token}" />
                            <input name="name" type="hidden" value="${searchCriteria.searchText}"/>
                            <input name="pageNo" type="hidden" value="${searchCriteria.pageNo+1}"/>
                            <input name="dateFrom" type="hidden" value="${searchCriteria.dateFrom}"/>
                            <input name="dateTo" type="hidden" value="${searchCriteria.dateTo}"/>
                            <input name="hourFrom" type="hidden" value="${searchCriteria.hourFrom}"/>
                            <input name="minuteFrom" type="hidden" value="${searchCriteria.minuteFrom}"/>
                            <input name="hourTo" type="hidden" value="${searchCriteria.hourTo}"/>
                            <input name="minuteTo" type="hidden" value="${searchCriteria.minuteTo}"/>
                            <input name="sortBy" type="hidden" value="${searchCriteria.sortBy}"/>
                        </form>


                        <form action="${initParam.PROJECT_ROOT_PATH}/applicationlogman/applicationlog_1/search/submit" method="post" id="paginationFirstForm">
                            <input type="hidden" name="${_csrf.parameterName}"
                                   value="${_csrf.token}" />
                            <input name="name" type="hidden" value="${searchCriteria.searchText}"/>
                            <input name="pageNo" type="hidden" value="1"/>

                            <input name="dateFrom" type="hidden" value="${searchCriteria.dateFrom}"/>
                            <input name="dateTo" type="hidden" value="${searchCriteria.dateTo}"/>
                            <input name="hourFrom" type="hidden" value="${searchCriteria.hourFrom}"/>
                            <input name="minuteFrom" type="hidden" value="${searchCriteria.minuteFrom}"/>
                            <input name="hourTo" type="hidden" value="${searchCriteria.hourTo}"/>
                            <input name="minuteTo" type="hidden" value="${searchCriteria.minuteTo}"/>
                            <input name="sortBy" type="hidden" value="${searchCriteria.sortBy}"/>
                        </form>

                        <form action="${initParam.PROJECT_ROOT_PATH}/applicationlogman/applicationlog_1/search/submit" method="post" id="paginationLastForm">
                            <input type="hidden" name="${_csrf.parameterName}"
                                   value="${_csrf.token}" />
                            <input name="searchText" type="hidden" value="${searchCriteria.searchText}"/>
                            <input name="pageNo" type="hidden" value="${applicationLogMessageListResource.totalPages}"/>

                            <input name="dateFrom" type="hidden" value="${searchCriteria.dateFrom}"/>
                            <input name="dateTo" type="hidden" value="${searchCriteria.dateTo}"/>
                            <input name="hourFrom" type="hidden" value="${searchCriteria.hourFrom}"/>
                            <input name="minuteFrom" type="hidden" value="${searchCriteria.minuteFrom}"/>
                            <input name="hourTo" type="hidden" value="${searchCriteria.hourTo}"/>
                            <input name="minuteTo" type="hidden" value="${searchCriteria.minuteTo}"/>
                            <input name="sortBy" type="hidden" value="${searchCriteria.sortBy}"/>

                        </form>


                    </div>
                    <!-- End Of Pagination-->


                </div>
            </div>
            <hr>

        </section>




        <%@ include file="/WEB-INF/jsp/includes/footerSecondLast_1.jsp" %>    

    </body>
    <%@ include file="/WEB-INF/jsp/includes/footerLast_1.jsp" %>      
