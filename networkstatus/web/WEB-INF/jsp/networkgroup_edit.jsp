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

    <script type="text/javascript">
        function confirmSubmit()
        {
            var agree = confirm("Are you sure you want to restore?");
            if (agree)
                return true;
            else
                return false;
        }

        function confirmSubmit_Backup()
        {
            var agree = confirm("Are you sure you want to backup?");
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

        function confirmDownloadSubmit()
        {
            var agree = confirm("Are you sure you want to Download?");
            if (agree)
                return true;
            else
                return false;
        }

        function confirmDeleteSubmit()
        {
            var agree = confirm("Are you sure you want to Delete?");
            if (agree)
                return true;
            else
                return false;
        }


        function confirmFixSubmit()
        {
            var agree = confirm("Are you sure you want to Fix it? \nddd\nsssddd");
            if (agree)
                return true;
            else
                return false;
        }

        function displayMD5Status(original, current)
        {
            alert("Expected value : " + original + "\nFound value : " + current);

        }

    </script>

    <body class="noheader">
        <%@ include file="/WEB-INF/jsp/includes/headerSecond.jsp" %> 
        <div id="body">
            <div id="breadcrumbs">
                <span>You are here:</span>
                <a href="${initParam.PROJECT_ROOT_PATH}/home">Home</a> &raquo;
                <a href="#">Network Group Management</a> &raquo;
                <strong>Add New Network Group</strong>
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
                    <legend>Manage Network Group</legend>
                    <form  name="form1" method="post" action="${initParam.PROJECT_ROOT_PATH}/networkgroupman/networkgroup/edit/submit">
                        <br/>


                        <p><label for="code">Network Group Code</label>
                            <input name="code" type="text" value="${networkGroupResource.code}" size="40" readonly="true">
                                &nbsp;&nbsp;
                                <br />
                        </p>	


                        <p><label for="name">Network Group Name:</label>
                            <input name='name' type="text" value="${networkGroupResource.name}" size="40">
                                &nbsp;&nbsp;
                                <font color="red"><c:out value="${networkGroupResource.errName}"/></font> 
                                <br />
                        </p>		

                        <p><label for="description">Description: </label>
                            <input name="description" id="description" value="${networkGroupResource.description}"  size="40">
                                &nbsp;&nbsp;
                                <font color="red"><c:out value="${networkGroupResource.errDescription}"/></font>
                                <br />
                        </p>

                        <p><label for="enabled">Display In Navigation Bar: </label>
                            <label><input type="checkbox" name="enabled" id="enabled" <c:if test="${networkGroupResource.enabled}">checked</c:if> /> Enabled</label>
                                <br/>
                            </p>
                            <p><label for="role">Roles:</label>

                            <c:forEach var="role" items="${roleResource}" varStatus="loop">

                                <c:if test="${loop.index!=0}"> <label> &nbsp;&nbsp; </label> </c:if>
                                <input type="checkbox" name="selectedRoles" value="${role.roleID}" <c:if test="${role.selected}">checked</c:if>/> ${role.description}


                                <c:if test="${loop.index==0}"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">  <c:out value="${networkGroupResource.errRole}"/></font>  </c:if>

                                    <br />
                            </c:forEach>

                            <!--                            &nbsp;&nbsp;
                                                        <font color="red"><c:out value="${departmentUserResource.errRole}"/></font>
                                                        <br />-->
                        </p>



                        <p> <input name="send" class="formbutton" value="Send" type="submit" ></p>



                        <input name="id" type="hidden" value="${networkGroupResource.networkGroupID}"/>
                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}" />
                    </form>
                </fieldset>



                <br/><br/><br/>
                <a name="nodesSearch"></a>
                <fieldset>
                    <legend>Nodes</legend>
                    <br/>

                    <form action="${initParam.PROJECT_ROOT_PATH}/networkgroupman/networkgroup/node/search" method="post">

                        <p>
                            <label for="newListener">Add New Node : </label>
                            <input name="newListener" class="formbutton" style="margin:  0px" value="Add Node" type="submit"/>
                            <input type="hidden" name="id"
                                   value="${networkGroupResource.networkGroupID}" />
                            <input type="hidden" name="code"
                                   value="${networkGroupResource.code}" />
                            <input type="hidden" name="${_csrf.parameterName}"
                                   value="${_csrf.token}" />
                            <br/>
                        </p>	
                    </form>


                    <c:choose>
                        <c:when test="${networkGroupResource.nodeInNetworkGroupResources != null}">
                            <c:choose>
                                <c:when test="${fn:length(networkGroupResource.nodeInNetworkGroupResources) != 0}">

                                    <table width="100%" id="nodeResults">

                                        <caption><strong> Nodes In Network Group </strong></caption>

                                        <tbody>
                                            <thead>
                                                <tr>
                                                    <th align="center">Node Label</th>
                                                    <th align="center">Title</th>
                                                    <th align="center">Type</th>
                                                    <!--<th align="center">Description</th>-->
                                                    <th align="center">Enabled</th>
                                                    <th align="center">Commands</th>
                                                </tr>
                                            </thead>

                                            <c:forEach items="${networkGroupResource.nodeInNetworkGroupResources}"
                                                       var="node"
                                                       varStatus="status"
                                                       begin="0"
                                                       end="499"
                                                       step="1"
                                                       >
                                                <tr align="center">
                                                    <td><c:out value="${node.nodeResource.label}" /></td>
                                                    <td align="center">
                                                        ${node.nodeResource.title}
                                                    </td>
                                                    <td align="center">
                                                        ${node.nodeResource.type}
                                                    </td>
<!--                                                    <td>${node.description}</td>-->
                                                    <td>${node.enabled}</td>
                                                    <td align="center">




                                                        &nbsp;
                                                        <a href="javascript:formSubmitById('deleteNodeInNetworkGroupFormId_${node.nodeInNetworkGroupID}')" onclick="return confirmDeleteSubmit();"  title="Remove Node In networkGroup">
                                                            <img src="${initParam.PROJECT_ROOT_PATH}/resources/icons/trash_32x32.png" alt="Checked" border="0" width="20" height="20" />
                                                        </a>
                                                        &nbsp;

                                                        <div style="display: none">
                                                            <form action="${initParam.PROJECT_ROOT_PATH}/networkgroupman/networkgroup/node/delete/submit" method="post" id="deleteNodeInNetworkGroupFormId_${node.nodeInNetworkGroupID}">
                                                                <input type="hidden" name="${_csrf.parameterName}"
                                                                       value="${_csrf.token}" />
                                                                <input type="hidden" name="nodeInNetworkGroupId"
                                                                       value="${node.nodeInNetworkGroupID}" />
                                                                <input type="hidden" name="networkGroupId"
                                                                       value="${networkGroupResource.networkGroupID}" />


                                                            </form>



                                                        </div>


                                                    </td>
                                                </tr>

                                            </c:forEach>

                                        </tbody>

                                    </table>
                                    <br>
                                        <div id="nodePageNavPosition" align="center"></div>
                                        <script type="text/javascript">
                                            var nodePager = new Pager('nodeResults', 5);
                                            nodePager.init();
                                            nodePager.showPageNav('nodePager', 'nodePageNavPosition');
                                            nodePager.showPage(1);
                                        </script>




                                    </c:when>
                                    <c:otherwise>
                                        <h4 align="center">No Nodes Found.</h4>
                                    </c:otherwise>
                                </c:choose>

                            </c:when>
                        </c:choose>





                </fieldset>






                <br/><br/><br/>
                <a name="edgeSearch"></a>


                <fieldset>
                    <legend>Edge</legend>
                    <br/>




                    <form name="form1" method="post" action="${initParam.PROJECT_ROOT_PATH}/networkgroupman/networkgroup/edge/clear_and_import_from_root/submit">

                        <p>
                            <label for="newListener">Import Edge From Root : </label>
                            <input name="send" class="formbutton" value="Import Edges Data According To Core Node Relationship" type="submit" onclick="return confirmContiune();" style="margin:  0px" /></p>
                        <input type="hidden" name="networkGroupID"
                               value="${networkGroupResource.networkGroupID}" />
                        <input type="hidden" name="networkGroupCode"
                               value="${networkGroupResource.code}" />
                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}" />

                        </p>	
                    </form>

                    <form action="${initParam.PROJECT_ROOT_PATH}/networkgroupman/networkgroup/edge/add" method="post">

                        <p>
                            <label for="newListener">Add New Edge : </label>
                            <input name="newListener" class="formbutton" style="margin:  0px" value="Add Edge" type="submit"/>
                            <input type="hidden" name="networkGroupID"
                                   value="${networkGroupResource.networkGroupID}" />
                            <input type="hidden" name="networkGroupCode"
                                   value="${networkGroupResource.code}" />
                            <input type="hidden" name="${_csrf.parameterName}"
                                   value="${_csrf.token}" />
                            <br/>
                        </p>	
                    </form>


                    <c:choose>
                        <c:when test="${networkGroupResource.edgeInNetworkGroupResources != null}">
                            <c:choose>
                                <c:when test="${fn:length(networkGroupResource.edgeInNetworkGroupResources) != 0}">

                                    <table width="100%" id="edgeResults">

                                        <caption><strong> Edges In Network Group </strong></caption>

                                        <tbody>
                                            <thead>
                                                <tr>
                                                    <th align="center">From</th>
                                                    <th align="center">To</th>
                                                    <th align="center">Arrows</th>
                                                    <th align="center">Dashes</th>
                                                    <th align="center">Enabled</th>
                                                    <th align="center">Commands</th>

                                                </tr>
                                            </thead>

                                            <c:forEach items="${networkGroupResource.edgeInNetworkGroupResources}"
                                                       var="edge"
                                                       varStatus="status"
                                                       begin="0"
                                                       end="1999"
                                                       step="1">


                                                <tr align="center">
                                                    <td align="center">

                                                        <c:out value="${edge.fromNodeInNetworkGroupResource.nodeResource.label}" />

                                                        <br>
                                                    </td>

                                                    <td align="center">

                                                        <c:out value="${edge.toNodeInNetworkGroupResource.nodeResource.label}" />

                                                        <br>
                                                    </td>

                                                    <td align="center">${edge.arrows}</td>

                                                    <td align="center"><c:out value="${edge.dashes}" /></td>


                                                    <td align="center"><c:out value="${edge.enabled}" /></td>

                                                    <td align="center">




                                                        &nbsp;
                                                        <a href="javascript:formSubmitById('edgeEdgeInNetworkGroupFormId_${edge.edgeInNetworkGroupID}')" title="Edit Edge"> 
                                                            <img src="${initParam.PROJECT_ROOT_PATH}/resources/icons/edit_32x32.png" alt="Checked" border="0" width="20" height="20" />
                                                        </a>


                                                        &nbsp;
                                                        <a href="javascript:formSubmitById('deleteEdgeInNetworkGroupFormId_${edge.edgeInNetworkGroupID}')" onclick="return confirmDeleteSubmit();"  title="Remove Edge In networkGroup">
                                                            <img src="${initParam.PROJECT_ROOT_PATH}/resources/icons/trash_32x32.png" alt="Checked" border="0" width="20" height="20" />
                                                        </a>
                                                        &nbsp;
                                                        <div style="display: none">
                                                            <form action="${initParam.PROJECT_ROOT_PATH}/networkgroupman/networkgroup/edge/delete/submit" method="post" id="deleteEdgeInNetworkGroupFormId_${edge.edgeInNetworkGroupID}">
                                                                <input type="hidden" name="${_csrf.parameterName}"
                                                                       value="${_csrf.token}" />

                                                                <input type="hidden" name="networkGroupId"
                                                                       value="${networkGroupResource.networkGroupID}" />

                                                                <input type="hidden" name="networkGroupCode"
                                                                       value="${networkGroupResource.code}" />

                                                                <input type="hidden" name="edgeInNetworkGroupId"
                                                                       value="${edge.edgeInNetworkGroupID}" />


                                                            </form>


                                                            <form action="${initParam.PROJECT_ROOT_PATH}/networkgroupman/networkgroup/edge/edit" method="post" id="edgeEdgeInNetworkGroupFormId_${edge.edgeInNetworkGroupID}">
                                                                <input type="hidden" name="${_csrf.parameterName}"
                                                                       value="${_csrf.token}" />
                                                                <input type="hidden" name="networkGroupId"
                                                                       value="${networkGroupResource.networkGroupID}" />

                                                                <input type="hidden" name="networkGroupCode"
                                                                       value="${networkGroupResource.code}" />

                                                                <input type="hidden" name="edgeInNetworkGroupId"
                                                                       value="${edge.edgeInNetworkGroupID}" />

                                                            </form>



                                                        </div>


                                                    </td>
                                                </tr>

                                            </c:forEach>

                                        </tbody>

                                    </table>
                                    <br>
                                        <div id="edgePageNavPosition" align="center"></div>
                                        <script type="text/javascript">
                                            var edgePager = new Pager('edgeResults', 5);
                                            edgePager.init();
                                            edgePager.showPageNav('edgePager', 'edgePageNavPosition');
                                            edgePager.showPage(1);
                                        </script>




                                    </c:when>
                                    <c:otherwise>
                                        <h4 align="center">No Edge Found.</h4>
                                    </c:otherwise>
                                </c:choose>

                            </c:when>
                        </c:choose>





                </fieldset>

                <br><br>

                        <div style="text-align: center">



                            <a href="javascript:formSubmitById('navigateBackToClassGroupForm')"  title="Back To ClassGroup">
                                <img src="${initParam.PROJECT_ROOT_PATH}/resources/icons/left_arrow_32x32.png" alt="Checked" border="0" width="20" height="20" />
                            </a>
                            &nbsp;

                            <div style="display: none">
                                <form action="${initParam.PROJECT_ROOT_PATH}/networkgroupman/networkgroup/search" method="post" id="navigateBackToClassGroupForm">
                                    <input type="hidden" name="${_csrf.parameterName}"
                                           value="${_csrf.token}" />
                                </form>

                            </div>
                        </div>

                        <!--Div Content End-->
                        <br/><br/><br/><br/>
                        </div>
                        <div class="clear"></div>
                        </div>
                        <%@ include file="/WEB-INF/jsp/includes/footerSecondLast.jsp" %>    
                        </body>
                        <%@ include file="/WEB-INF/jsp/includes/footerLast.jsp" %>       