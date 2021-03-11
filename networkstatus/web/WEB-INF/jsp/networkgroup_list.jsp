<%@ include file="/WEB-INF/jsp/includes/tagLibs.jsp" %> 
<%@page session="true"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <%@ include file="/WEB-INF/jsp/includes/headerFirst.jsp" %> 



    <script type="text/javascript">

        function init()
        {
            fireEvent(document.getElementById('linking'), 'click');
        }





        function fireEvent(obj, evt) {

            var fireOnThis = obj;
            if (document.createEvent) {
                var evObj = document.createEvent('MouseEvents');
                evObj.initEvent(evt, true, false);
                fireOnThis.dispatchEvent(evObj);
            } else if (document.createEventObject) {
                fireOnThis.fireEvent('on' + evt);
            }
        }


    </script>

    <body class="noheader" onload='init();'>
        <%@ include file="/WEB-INF/jsp/includes/headerSecond.jsp" %> 
        <a href="#${pagelink}" id="linking" style="display: none"></a>
        <div id="body">
            <div id="breadcrumbs">
                <span>You are here:</span>
                <a href="${initParam.PROJECT_ROOT_PATH}/home">Home</a> &raquo;
                <a href="#">Network Group Management</a> &raquo;
                <strong>Search Network Group</strong>
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


                    function confirmChangeStatus()
                    {
                        var agree = confirm("Are you sure you want to change Status?");
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
                    <legend>Search Network Group Management Info</legend>

                    <form name="form1" method="post" action="${initParam.PROJECT_ROOT_PATH}/networkgroupman/networkgroup/search/submit">
                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}" />

                        <br/>
                        <p><label for="searchedText">Search Text:</label>

                            <input type="text" name="searchedText" id="name" value="${searchCriteria.searchedText}"/> &nbsp; <br />	
                            <input type="hidden" name="statusCode"  id="statusCode" value="5"/>

                        </p>

                        <p>
                            <label for="searchText">Created Date From: </label>

                            <div class="cssreset">

                                <table class="restore_default_form_element_style">
                                    <tr>

                                        <td><script>DateInput('dateFrom', true, 'YYYY-MM-DD', '${searchCriteria.dateFrom}')</script></td>
                                        <td><label> &nbsp;&nbsp; To: &nbsp;</label></td>
                                        <td><script>DateInput('dateTo', true, 'YYYY-MM-DD', '${searchCriteria.dateTo}')</script></td>
                                        <td><label><input type="checkbox" name="enableCreatedDateSearch" id="enableCreatedDateSearch" <c:if test="${searchCriteria.enableCreatedDateSearch}">checked</c:if> /> </label></td>
                                        </tr>

                                    </table>

                                </div>

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

                <a name="eventsearch"></a>
                <c:choose>
                    <c:when test="${networkGroupListResource.networkGroupResources != null}">
                        <c:choose>
                            <c:when test="${fn:length(networkGroupListResource.networkGroupResources) != 0}">


                                <h4> Total Document Found: &nbsp  <fmt:formatNumber type="number" groupingUsed="true" value="${totalDocuments}" /> </h4>


                                <table width="100%">
                                    <caption><strong>Search Network Group Info Records</strong></caption>
                                    <tbody>

                                        <tr>
                                            <th align="center">Name</th>
                                            <th align="center">Code</th>
                                            <th align="center">Description</th>
                                            <th align="center">Enabled</th>
                                            <th align="center">Entry Date</th>
                                            <th align="center">Commands</th>

                                        </tr>


                                        <c:forEach items="${networkGroupListResource.networkGroupResources}"
                                                   var="obj"
                                                   varStatus="status"
                                                   begin="0"
                                                   end="1999"
                                                   step="1">


                                            <tr>
                                                <td align="center">${obj.name}</td>

                                                <td align="center">${obj.code}</td>

                                                <td align="center">${obj.description}</td>

                                                <td align="center">${obj.enabled}</td>

                                                <td align="center">
                                                    <jsp:useBean id="entryDateObject" class="java.util.Date" />
                                                    <jsp:setProperty name="entryDateObject" property="time" value="${obj.entryDate*1000}" />
                                                    <fmt:formatDate value="${entryDateObject}" pattern="yyyy/MM/dd hh:mm a" />

                                                </td>






                                                <td align="center">


                                                    <a href="javascript:formSubmitById('viewFormId_${obj.networkGroupID}')" title="View Network Group"> 
                                                        <img src="${initParam.PROJECT_ROOT_PATH}/resources/icons/lens_32x32.png" alt="Checked" border="0" width="20" height="20" />
                                                    </a>
                                                    &nbsp;
                                                    <a href="javascript:formSubmitById('editFormId_${obj.networkGroupID}')" title="Edit Network Group"> 
                                                        <img src="${initParam.PROJECT_ROOT_PATH}/resources/icons/edit_32x32.png" alt="Checked" border="0" width="20" height="20" />
                                                    </a>
                                                    &nbsp;
                                                    <a href="javascript:formSubmitById('deleteFormId_${obj.networkGroupID}')" onclick="return messageforadmin('${obj.networkGroupID}');"  title="Remove Network Group">
                                                        <img src="${initParam.PROJECT_ROOT_PATH}/resources/icons/trash_32x32.png" alt="Checked" border="0" width="20" height="20" />
                                                    </a>
                                                    &nbsp;










                                                    <form action="${initParam.PROJECT_ROOT_PATH}/networkgroupman/networkgroup/delete/submit" method="post" id="deleteFormId_${obj.networkGroupID}">
                                                        <input type="hidden" name="${_csrf.parameterName}"
                                                               value="${_csrf.token}" />
                                                        <input type="hidden" name="id"
                                                               value="${obj.networkGroupID}" />
                                                    </form>

                                                    <form action="${initParam.PROJECT_ROOT_PATH}/networkgroupman/networkgroup/edit" method="post" id="editFormId_${obj.networkGroupID}">
                                                        <input type="hidden" name="${_csrf.parameterName}"
                                                               value="${_csrf.token}" />
                                                        <input type="hidden" name="id"
                                                               value="${obj.networkGroupID}" />
                                                    </form>

                                                    <form action="${initParam.PROJECT_ROOT_PATH}/networkman/dynamic_chart/networkgroup/${obj.networkGroupID}/display" method="post" id="viewFormId_${obj.networkGroupID}">
                                                        <input type="hidden" name="${_csrf.parameterName}"
                                                               value="${_csrf.token}" />
                                                    </form>



                                                </td>


                                            </tr>

                                        </c:forEach>

                                    </tbody>

                                </table>






                                <!--Pagination-->
                                <div id="pagination" style="text-align: center;width: 100%" >

                                    <br/>
                                    <fmt:parseNumber var="totalPageNoParsed" value="${totalPageNo}" />

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

                                    <c:if test="${searchCriteria.pageNo+1<=networkGroupListResource.totalPages}">
                                        <a href="javascript:formSubmitById('paginationNextForm')" title="Next"> 
                                            ${searchCriteria.pageNo+1}
                                        </a>
                                    </c:if>

                                    &nbsp;

                                    <c:if test="${searchCriteria.pageNo+2<=networkGroupListResource.totalPages}">
                                        <a href="javascript:formSubmitById('paginationLastForm')" title="Last"> 
                                            <img src="${initParam.PROJECT_ROOT_PATH}/resources/icons/right_arrow_32x32.png" alt="Checked" border="0" width="20" height="10" />
                                        </a>
                                    </c:if>
                                    <br/>
                                    <br/>



                                    <form action="${initParam.PROJECT_ROOT_PATH}/networkgroupman/networkgroup/search/submit" method="post" id="paginationPreviousForm">
                                        <input type="hidden" name="${_csrf.parameterName}"
                                               value="${_csrf.token}" />
                                        <input name="searchedText" type="hidden" value="${searchCriteria.searchedText}"/>
                                        <input name="pageNo" type="hidden" value="${searchCriteria.pageNo-1}"/>


                                        <input name="dateFrom" type="hidden" value="${searchCriteria.dateFrom}"/>
                                        <input name="dateTo" type="hidden" value="${searchCriteria.dateTo}"/>
                                        <input name="sortBy" type="hidden" value="${searchCriteria.sortBy}"/>

                                        <input name="enableCreatedDateSearch" type="hidden" value="${searchCriteria.enableCreatedDateSearch}"/>

                                    </form>

                                    <form action="${initParam.PROJECT_ROOT_PATH}/networkgroupman/networkgroup/search/submit" method="post" id="paginationNextForm">
                                        <input type="hidden" name="${_csrf.parameterName}"
                                               value="${_csrf.token}" />
                                        <input name="searchedText" type="hidden" value="${searchCriteria.searchedText}"/>
                                        <input name="pageNo" type="hidden" value="${searchCriteria.pageNo+1}"/>


                                        <input name="dateFrom" type="hidden" value="${searchCriteria.dateFrom}"/>
                                        <input name="dateTo" type="hidden" value="${searchCriteria.dateTo}"/>
                                        <input name="sortBy" type="hidden" value="${searchCriteria.sortBy}"/>

                                        <input name="enableCreatedDateSearch" type="hidden" value="${searchCriteria.enableCreatedDateSearch}"/>

                                    </form>


                                    <form action="${initParam.PROJECT_ROOT_PATH}/networkgroupman/networkgroup/search/submit" method="post" id="paginationFirstForm">
                                        <input type="hidden" name="${_csrf.parameterName}"
                                               value="${_csrf.token}" />
                                        <input name="searchedText" type="hidden" value="${searchCriteria.searchedText}"/>
                                        <input name="pageNo" type="hidden" value="1"/>


                                        <input name="dateFrom" type="hidden" value="${searchCriteria.dateFrom}"/>
                                        <input name="dateTo" type="hidden" value="${searchCriteria.dateTo}"/>
                                        <input name="sortBy" type="hidden" value="${searchCriteria.sortBy}"/>

                                        <input name="enableCreatedDateSearch" type="hidden" value="${searchCriteria.enableCreatedDateSearch}"/>
                                    </form>

                                    <form action="${initParam.PROJECT_ROOT_PATH}/networkgroupman/networkgroup/search/submit" method="post" id="paginationLastForm">
                                        <input type="hidden" name="${_csrf.parameterName}"
                                               value="${_csrf.token}" />
                                        <input name="searchedText" type="hidden" value="${searchCriteria.searchedText}"/>
                                        <input name="pageNo" type="hidden" value="${networkGroupListResource.totalPages}"/>



                                        <input name="dateFrom" type="hidden" value="${searchCriteria.dateFrom}"/>
                                        <input name="dateTo" type="hidden" value="${searchCriteria.dateTo}"/>
                                        <input name="sortBy" type="hidden" value="${searchCriteria.sortBy}"/>

                                        <input name="enableCreatedDateSearch" type="hidden" value="${searchCriteria.enableCreatedDateSearch}"/>

                                    </form>


                                </div>
                                <!-- End Of Pagination-->



                            </c:when>
                            <c:otherwise>
                                <h4 align="center">No Records Found.</h4>
                            </c:otherwise>
                        </c:choose>

                    </c:when>
                </c:choose>


                <!--Div Content End-->
                <br/><br/><br/><br/>
            </div>
            <div class="clear"></div>
        </div>
        <%@ include file="/WEB-INF/jsp/includes/footerSecondLast.jsp" %>    
    </body>
    <%@ include file="/WEB-INF/jsp/includes/footerLast.jsp" %>        


    <%--<%=machineMode%>--%>
