<%@ include file="/WEB-INF/jsp/includes/tagLibs.jsp" %> 
<%@page session="true"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <%@ include file="/WEB-INF/jsp/includes/headerFirst.jsp" %> 
    <body class="noheader">
        <%@ include file="/WEB-INF/jsp/includes/headerSecond.jsp" %> 
        <div id="body">
            <div id="breadcrumbs">
                <span>You are here:</span>
                <a href="${initParam.PROJECT_ROOT_PATH}/home">Home</a> &raquo;
                <a href="#">Setup</a> &raquo;
                <strong>Uploaded Nodes Reading CSV File For Node Entries</strong>
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


                    <br/>  <br/>  <br/>



                    <h3>Uploaded CSV Node Details</h3>


                    <table width="100%" id="sundayResults">

                        <tbody>

                            <tr>
                                <th align="center">S.No</th>
                                <th align="center">Label / Hostname</th>
                                <th align="center">Type</th>
                                <th align="center">Title / IP Address</th>
                                <th align="center">Description / System</th>
                                <th align="center">Redirect Link</th>
                                <th align="center">Selected</th>
                                <th align="center">Edges</th>



                            </tr>


                            <form:form method="post" action="${initParam.PROJECT_ROOT_PATH}/nodeman/node/add/upload_csv/upload/display/submit" modelAttribute="nodeListResource" width="100%"  >


                                <c:forEach items="${tableData.nodeResources}" var="object" varStatus="status">
<!--                                    ${status.count}&nbsp;&nbsp;-->
                                    <tr style="text-align: center;  <c:if test="${object.errLabel!='' || object.errTitle!=''|| object.errType!='' || object.errRedirectingURLLink!=''|| object.errEdgesStr!=''}">background: bisque</c:if>">
                                        <td>  <input name="nodeResources[${status.index}].nodeID" type="text" value="${object.nodeID}" size="2" style="border: 0px" readonly="true"/></td> 

                                        <td>  <input name="nodeResources[${status.index}].label" type="text" value="${object.label}"/>    
                                            <br/>
                                            <font color="red"><c:out value="${object.errLabel}"/></font>
                                        </td> 
                                        <td>   <input name="nodeResources[${status.index}].type" type="text" value="${object.type}" />
                                            <br/>
                                            <font color="red"><c:out value="${object.errType}"/></font>
                                        </td> 
                                        <td>   <input name="nodeResources[${status.index}].title" type="text" value="${object.title}" />
                                            <br/>
                                            <font color="red"><c:out value="${object.errTitle}"/></font>
                                        </td> 




                                        <td>   <input name="nodeResources[${status.index}].description" type="text" value="${object.description}"/></td>  


                                        <td>   <input name="nodeResources[${status.index}].redirectingURLLink" type="text" value="${object.redirectingURLLink}" />
                                            <br/>
                                            <font color="red"><c:out value="${object.errRedirectingURLLink}"/></font>
                                        </td> 

                                        <td> <input type="checkbox" name="nodeResources[${status.index}].enabled" <c:if test="${object.enabled}" >checked</c:if> /> </td>
                                       
                                            <td>   <input name="nodeResources[${status.index}].edgesStr" type="text" value="${object.edgesStr}"  size="3"/>
                                            <br />
                                            <font color="red"><c:out value="${object.errEdgesStr}"/></font>
                                        </td> 
                                    </tr>
                                        




                                </c:forEach>

                                <p><input name="send" class="formbutton" value="Save Or Update Node Entries Into Database" type="submit" style="width: 100%" /></p>

                                <input type="hidden" name="${_csrf.parameterName}"
                                       value="${_csrf.token}" />

                            </form:form>

                        </tbody>

                    </table>

                    <br>
                        <div id="nodePageNavPosition" align="center"></div>
                        <script type="text/javascript">
                            var nodePager = new Pager('sundayResults', 20);
                            nodePager.init();
                            nodePager.showPageNav('nodePager', 'nodePageNavPosition');
                            nodePager.showPage(1);
                        </script>

                        <br/>  <br/>  <br/>





                </fieldset>




                <!--Div Content End-->
                <br/><br/><br/><br/>
            </div>
            <div class="clear"></div>
        </div>
        <%@ include file="/WEB-INF/jsp/includes/footerSecondLast.jsp" %>    
    </body>
    <%@ include file="/WEB-INF/jsp/includes/footerLast.jsp" %>        


    <%--<%=machineMode%>--%>
