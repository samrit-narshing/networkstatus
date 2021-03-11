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
                <a href="#">Department Role Management</a> &raquo;
                <strong>List Department Roles</strong>
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
                        //                    alert("Warning!!! This action is restricted for this role.");
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
                    <legend>Search Department Role</legend>

                    <form name="form1" method="post" action="${initParam.PROJECT_ROOT_PATH}/roleman/role/search">
                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}" />

                        <br/>
                        <p><label for="searchText">Search Text:</label>

                            <input type="text" name="searchText" id="searchText" value="${searchCriteria.searchText}"/><br /></p>	
                        <p> <font color="red"><c:out value="${searchCriteria.errSearchText}"/></font> </p>

                        <p><input name="send" class="formbutton" value="Search" type="submit" /></p>

                    </form>
                </fieldset>

                <h3>Department Roles</h3>

                <table width="100%">
                    <tbody>

                        <tr>
                            <th align="center">Name</th>
                            <th align="center">Description</th>
                            <th align="center">Status</th>
                            <th align="center">Commands</th>

                        </tr>


                        <c:forEach items="${roleListResource.roles}"
                                   var="role"
                                   varStatus="status"
                                   begin="0"
                                   end="1999"
                                   step="1">


                            <tr>
                                <td align="center"><c:out value="${role.name}" /></td>
                                <td align="center">

                                    ${role.description}

                                    <br>
                                </td>

                                <td align="center">

                                    <c:choose>
                                        <c:when test="${role.enabled}">
                                            <img src="${initParam.PROJECT_ROOT_PATH}/resources/icons/unlock_32x32.png" alt="Checked" border="0" width="20" height="20" />
                                        </c:when>
                                        <c:otherwise>
                                            <img src="${initParam.PROJECT_ROOT_PATH}/resources/icons/lock_32x32.png" alt="Checked" border="0" width="20" height="20" />
                                        </c:otherwise>
                                    </c:choose>

                                </td>

                                <td align="center">



                                    &nbsp;
                                    <a href="javascript:formSubmitById('editFormId_${role.roleID}')" title="Edit Role"> 
                                        <img src="${initParam.PROJECT_ROOT_PATH}/resources/icons/edit_32x32.png" alt="Checked" border="0" width="20" height="20" />
                                    </a>



                                    <a href="javascript:formSubmitById('updateEnabledFormId_${role.roleID}')" onclick="return confirmChangeStatus();"  title="Change Status">
                                        <c:choose>
                                            <c:when test="${role.enabled}">
                                                <img src="${initParam.PROJECT_ROOT_PATH}/resources/icons/unlock_32x32.png" alt="Checked" border="0" width="20" height="20" />
                                            </c:when>
                                            <c:otherwise>
                                                <img src="${initParam.PROJECT_ROOT_PATH}/resources/icons/lock_32x32.png" alt="Checked" border="0" width="20" height="20" />
                                            </c:otherwise>
                                        </c:choose>
                                    </a>


                                    <form action="${initParam.PROJECT_ROOT_PATH}/roleman/role/changeenablestatus/submit" method="post" id="updateEnabledFormId_${role.roleID}">
                                        <input type="hidden" name="${_csrf.parameterName}"
                                               value="${_csrf.token}" />
                                        <input type="hidden" name="id"
                                               value="${role.roleID}" />
                                    </form>


                                    <form action="${initParam.PROJECT_ROOT_PATH}/roleman/role/editRole" method="post" id="editFormId_${role.roleID}">
                                        <input type="hidden" name="${_csrf.parameterName}"
                                               value="${_csrf.token}" />
                                        <input type="hidden" name="id"
                                               value="${role.roleID}" />
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

                    <c:if test="${searchCriteria.pageNo+1<=roletListResource.totalPages}">
                        <a href="javascript:formSubmitById('paginationNextForm')" title="Next"> 
                            ${searchCriteria.pageNo+1}
                        </a>
                    </c:if>

                    &nbsp;

                    <c:if test="${searchCriteria.pageNo+2<=roletListResource.totalPages}">
                        <a href="javascript:formSubmitById('paginationLastForm')" title="Last"> 
                            <img src="${initParam.PROJECT_ROOT_PATH}/resources/icons/right_arrow_32x32.png" alt="Checked" border="0" width="20" height="10" />
                        </a>
                    </c:if>
                    <br/>
                    <br/>



                    <form action="${initParam.PROJECT_ROOT_PATH}/roleman/role/search" method="post" id="paginationPreviousForm">
                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}" />
                        <input name="name" type="hidden" value="${searchCriteria.searchText}"/>
                        <input name="pageNo" type="hidden" value="${searchCriteria.pageNo-1}"/>
                    </form>

                    <form action="${initParam.PROJECT_ROOT_PATH}/roleman/role/search" method="post" id="paginationNextForm">
                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}" />
                        <input name="name" type="hidden" value="${searchCriteria.searchText}"/>
                        <input name="pageNo" type="hidden" value="${searchCriteria.pageNo+1}"/>
                    </form>


                    <form action="${initParam.PROJECT_ROOT_PATH}/roleman/role/search" method="post" id="paginationFirstForm">
                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}" />
                        <input name="name" type="hidden" value="${searchCriteria.searchText}"/>
                        <input name="pageNo" type="hidden" value="1"/>
                    </form>

                    <form action="${initParam.PROJECT_ROOT_PATH}/roleman/role/search" method="post" id="paginationLastForm">
                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}" />
                        <input name="searchText" type="hidden" value="${searchCriteria.searchText}"/>
                        <input name="pageNo" type="hidden" value="${roletListResource.totalPages}"/>
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
