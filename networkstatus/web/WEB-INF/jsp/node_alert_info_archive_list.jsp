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
                <a href="#">System Management</a> &raquo;
                <strong>List Node Alert Info Archives</strong>
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



                <fieldset>
                    <legend>Search Node Alert Info Archives</legend>

                    <form name="form1" method="post" action="${initParam.PROJECT_ROOT_PATH}/nodealertinfoman/nodealertinfo/search/submit">
                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}" />

                        <br/>
                        <p><label for="searchText">Search Text:</label>

                            <input type="text" name="searchText" id="searchText" value="${searchCriteria.searchText}"/><br /></p>	
                        <p> <font color="red"><c:out value="${searchCriteria.errSearchText}"/></font> </p>



                        <p>
                            <label for="searchText">Date From: </label>

                            <div class="cssreset">

                                <table class="restore_default_form_element_style">
                                    <tr>

                                        <td><script>DateInput('dateFrom', true, 'YYYY-MM-DD', '${searchCriteria.dateFrom}')</script></td>
                                        <td><label> &nbsp;&nbsp; To: &nbsp;</label></td>
                                        <td><script>DateInput('dateTo', true, 'YYYY-MM-DD', '${searchCriteria.dateTo}')</script></td>

                                    </tr>

                                </table>

                            </div>

                        </p>



                        <p>
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


                            &nbsp;&nbsp;&nbsp; To:



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

                            <br/>
                        </p>


                        <p>
                            <label for="sortBy">Sort by:</label>


                            <select name="sortBy">
                                <option value="0" ${searchCriteria.sortBy == 0 ? 'selected': ''}>Date Desc</option>
                                <option value="1" ${searchCriteria.sortBy == 1 ? 'selected': ''}>Date Asc</option>
                            </select>



                            <br/>


                        </p>



                        <p><input name="send" class="formbutton" value="Search" type="submit" /></p>

                    </form>

                </fieldset>


                <form name="form1" method="post" action="${initParam.PROJECT_ROOT_PATH}/nodealertinfoman/nodealertinfo/delete/all/submit">
                    <input type="hidden" name="${_csrf.parameterName}"
                           value="${_csrf.token}" />

                    <p><input name="send" class="formbutton" value="Delete All Archives" type="submit" style="width: 100%"  onclick="return confirmContiune();" /></p>

                </form>


<!--                <form name="form1" method="post" action="${initParam.PROJECT_ROOT_PATH}/nodealertinfoman/nodealertinfo/dummyexecption/submit">
                    <input type="hidden" name="${_csrf.parameterName}"
                           value="${_csrf.token}" />

                    <p><input name="send" class="formbutton" value="Insert Dummy Archive " type="submit" style="width: 100%"  onclick="return confirmContiune();" /></p>

                </form>-->

                <h3>Node Alert Info Archives</h3>

                <table width="100%">
                    <tbody>

                        <tr>
                            <th align="center">Resolved By</th>
                            <th align="center">Node Name</th>
 
                            <th align="center">Type</th>
                            <th align="center">Entry Date</th>
                            <th align="center">Description</th>
                            <th align="center">Commands</th>

                        </tr>


                        <c:forEach items="${nodeAlertInfoArchiveListResource.nodeAlertInfoArchiveResources}"
                                   var="node_alert_info_archive"
                                   varStatus="status"
                                   begin="0"
                                   end="1999"
                                   step="1">


                            <tr>
                                <td align="center"><c:out value="${node_alert_info_archive.loggedUsername}" /></td>
                                <td align="center"><c:out value="${node_alert_info_archive.nodeName}" /></td>

                       

                                <td align="center" style="width: 100px">
                                    <div style="overflow-y:auto;overflow-x:auto;height:200px;width:100px;font-size: 10px;">
                                        ${node_alert_info_archive.type}
                                    </div>
                                </td>





                                <td align="center" style="width: 100px">
                                    <div style="overflow-y:auto;overflow-x:auto;height:200px;width:100px;font-size: 10px;">
                                        <jsp:useBean id="entryDateObject" class="java.util.Date" />
                                        <jsp:setProperty name="entryDateObject" property="time" value="${node_alert_info_archive.entryDate*1000}" />
                                        <fmt:formatDate value="${entryDateObject}" pattern="yyyy/MM/dd hh:mm a" />
                                    </div>
                                </td>



                                <td align="center" style="width: 500px">
                                    <div style="overflow-y:auto;overflow-x:auto;height:200px;width:490px;font-size: 10px;">
                                        ${node_alert_info_archive.description}
                                    </div>
                                </td>


                                <td align="center">


                                    &nbsp;
                                    <a href="javascript:formSubmitById('deleteFormId_${node_alert_info_archive.tableId}')" onclick="return messageforadmin('${node_alert_info_archive.tableId}');"  title="Remove Log Message">
                                        <img src="${initParam.PROJECT_ROOT_PATH}/resources/icons/trash_32x32.png" alt="Checked" border="0" width="20" height="20" />
                                    </a>
                                    &nbsp;

                                    <form action="${initParam.PROJECT_ROOT_PATH}/nodealertinfoman/nodealertinfo/delete/submit" method="post" id="deleteFormId_${node_alert_info_archive.tableId}">
                                        <input type="hidden" name="${_csrf.parameterName}"
                                               value="${_csrf.token}" />
                                        <input type="hidden" name="id"
                                               value="${node_alert_info_archive.tableId}" />

                                    </form>


                                </td>
                            </tr>

                        </c:forEach>

                    </tbody>

                </table>

                <!--Pagination-->
                <div id="pagination" style="text-align: center;width: 100%" >
                    <br/>
                    <fmt:parseNumber var="totalPageNoParsed" value="${nodeAlertInfoArchiveListResource.totalPages}" />

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

                    <c:if test="${searchCriteria.pageNo+1<=nodeAlertInfoArchiveListResource.totalPages}">
                        <a href="javascript:formSubmitById('paginationNextForm')" title="Next"> 
                            ${searchCriteria.pageNo+1}
                        </a>
                    </c:if>

                    &nbsp;

                    <c:if test="${searchCriteria.pageNo+2<=nodeAlertInfoArchiveListResource.totalPages}">
                        <a href="javascript:formSubmitById('paginationLastForm')" title="Last"> 
                            <img src="${initParam.PROJECT_ROOT_PATH}/resources/icons/right_arrow_32x32.png" alt="Checked" border="0" width="20" height="10" />
                        </a>
                    </c:if>
                    <br/>
                    <br/>



                    <form action="${initParam.PROJECT_ROOT_PATH}/nodealertinfoman/nodealertinfo/search/submit" method="post" id="paginationPreviousForm">
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

                    <form action="${initParam.PROJECT_ROOT_PATH}/nodealertinfoman/nodealertinfo/search/submit" method="post" id="paginationNextForm">
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


                    <form action="${initParam.PROJECT_ROOT_PATH}/nodealertinfoman/nodealertinfo/search/submit" method="post" id="paginationFirstForm">
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

                    <form action="${initParam.PROJECT_ROOT_PATH}/nodealertinfoman/nodealertinfo/search/submit" method="post" id="paginationLastForm">
                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}" />
                        <input name="searchText" type="hidden" value="${searchCriteria.searchText}"/>
                        <input name="pageNo" type="hidden" value="${nodeAlertInfoArchiveListResource.totalPages}"/>

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







                <!--Div Content End-->
                <br/><br/><br/><br/>
            </div>
            <div class="clear"></div>
        </div>
        <%@ include file="/WEB-INF/jsp/includes/footerSecondLast.jsp" %>    
    </body>
    <%@ include file="/WEB-INF/jsp/includes/footerLast.jsp" %>        


    <%--<%=machineMode%>--%>
