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
                <strong>List Nodes</strong>
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
                        var agree = confirm("Warning!! If you are disabling it then ,all classes related to this node will also be disabled automatically!\nAre you sure you want to change Status?");

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




                <h3>Add Nodes</h3>








                <div>










                    <style>
                        * {
                            box-sizing: border-box;
                        }

                        #myInput {
                            background-image: url('/css/searchicon.png');
                            background-position: 10px 10px;
                            background-repeat: no-repeat;
                            width: 100%;
                            font-size: 16px;
                            padding: 12px 20px 12px 40px;
                            border: 1px solid #ddd;
                            margin-bottom: 12px;
                        }

                        #myTable {
                            border-collapse: collapse;
                            width: 100%;
                            border: 1px solid #ddd;
                            font-size: 18px;
                        }

                        #myTable th, #myTable td {
                            text-align: left;
                            padding: 12px;
                        }

                        #myTable tr {
                            border-bottom: 1px solid #ddd;
                        }

                        #myTable tr.header, #myTable tr:hover {
                            background-color: #f1f1f1;
                        }
                    </style>
                    <script>
                        function myFunction() {
                            var input, filter, table, tr, td, i, txtValue;
                            input = document.getElementById("myInput");
                            filter = input.value.toUpperCase();
                            table = document.getElementById("sundayResults");
                            tr = table.getElementsByTagName("tr");
                            for (i = 0; i < tr.length; i++) {
                                td = tr[i].getElementsByTagName("td")[1];
                                if (td) {
                                    txtValue = td.textContent || td.innerText;
                                    if (txtValue.toUpperCase().indexOf(filter) > -1) {
                                        tr[i].style.display = "";
                                    } else {
                                        tr[i].style.display = "none";
                                    }
                                }
                            }
                        }
                    </script>



                    <p><label for="title">Node Name :</label>
                        <input type="text" id="myInput" onkeyup="myFunction()" placeholder="Search for names.." title="Type in a name"> 
                    </p>	




                    <form:form method="post" action="${initParam.PROJECT_ROOT_PATH}/networkgroupman/networkgroup/node/add/submit" modelAttribute="nodeListResource" width="100%"  >


                        <br>
                            <p><input name="send" class="formbutton" value="Add Or Update Node Entries Into Node Group" type="submit" style="width: 100%" /></p>

                            <input name="id" type="hidden" value="${networkGroupID}"/>
                            <input name="code" type="hidden" value="${networkGroupCode}"/>
                            <input type="hidden" name="${_csrf.parameterName}"
                                   value="${_csrf.token}" />
                            <br>

                                <table id="sundayResults" class="table table-striped table-bordered table-sm">
                                    <thead>

                                        <tr>
                                            <th align="center">ID</th>
                                            <th align="center">Name</th>
                                            <th align="center">Type</th>
                                            <th align="center">Description</th>
                                            <th align="center">Enabled</th>
                                            <th align="center">Selected</th>
                                            <th align="center" style="display: none" >Edges</th>
                                        </tr>
                                    </thead>
                                    <tbody>

                                        <c:forEach items="${nodeListResource.nodeResources}" var="object" varStatus="status">

                                            <tr>
                                                <td align="center"> ${object.nodeID} <input name="nodeResources[${status.index}].nodeID" type="text" value="${object.nodeID}" size="2" style="border: 0px" hidden="true"/></td> 

                                                <td align="center">  ${object.label} <input name="nodeResources[${status.index}].label" type="text" value="${object.label}" hidden="true"/>    


                                                </td> 
                                                <td align="center">   ${object.type}<input name="nodeResources[${status.index}].type" type="text" value="${object.type}" hidden="true"/>

                                                </td> 





                                                <td align="center">   ${object.description}<input name="nodeResources[${status.index}].description" type="text" value="${object.description}" hidden="true"></td>  


                                                <td align="center"><c:out value="${object.enabled}" /></td>


                                                <td align="center">

                                                    <input type="checkbox" name="nodeResources[${status.index}].selected" <c:if test="${object.selected}" >checked</c:if> /> 



                                                    </td>


                                                    <td align="center" style="display: none" >   <input name="nodeResources[${status.index}].edgesStr" type="text" value="${object.edgesStr}"  size="3"/>
                                                    <br />
                                                    <font color="red"><c:out value="${object.errEdgesStr}"/></font>
                                                </td> 

                                            </tr>



                                        </c:forEach>

                                    </tbody>
                                </table>


                            </form:form>
                            </div>   




                            <br>
                                <!--                        <div id="nodePageNavPosition" align="center"></div>
                                                        <script type="text/javascript">
                                                            var nodePager = new Pager('sundayResults', 5);
                                                            nodePager.init();
                                                            nodePager.showPageNav('nodePager', 'nodePageNavPosition');
                                                            nodePager.showPage(1);
                                                        </script>-->



                                <div style="text-align: center">



                                    <a href="javascript:formSubmitById('navigateBackToClassGroupForm')"  title="Back To ClassGroup">
                                        <img src="${initParam.PROJECT_ROOT_PATH}/resources/icons/left_arrow_32x32.png" alt="Checked" border="0" width="20" height="20" />
                                    </a>
                                    &nbsp;

                                    <div style="display: none">
                                        <form action="${initParam.PROJECT_ROOT_PATH}/networkgroupman/networkgroup/edit" method="post" id="navigateBackToClassGroupForm">
                                            <input type="hidden" name="${_csrf.parameterName}"
                                                   value="${_csrf.token}" />
                                            <input name="id" type="hidden" value="${networkGroupID}"/>
                                            <input name="code" type="hidden" value="${networkGroupCode}"/>


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


                                <%--<%=machineMode%>--%>
