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
                <a href="#">Setup</a> &raquo;
                <strong>Add Network Group Edge</strong>
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
                    <legend>Add New Network Edge</legend>
                    <form  name="form1" method="post" action="${initParam.PROJECT_ROOT_PATH}/networkgroupman/networkgroup/edge/add/submit">
                        <br/>


                        <p><label for="fromNodeInNetworkGroupResource">From Node :</label>

                            <select name="fromNodeInNetworkGroupResource.nodeInNetworkGroupID" size="1" >
                                <c:forEach var="node" items="${networkGroupResource.nodeInNetworkGroupResources}" varStatus="loop">

                                    <option value="${node.nodeInNetworkGroupID}" ${node.nodeInNetworkGroupID == edgeInNetworkGroupResource.fromNodeInNetworkGroupResource.nodeInNetworkGroupID  ? 'selected': ''}> ${node.nodeResource.label} - [${node.nodeResource.title}]</option>

                                </c:forEach>
                            </select>

                            &nbsp;&nbsp;
                            <font color="red"><c:out value="${edgeInNetworkGroupResource.errFromNode}"/><c:out value="${edgeInNetworkGroupResource.errNode}"/></font> 
                            <br />

                        </p>



                        <p><label for="toNodeInNetworkGroupResource">To Node :</label>

                            <select name="toNodeInNetworkGroupResource.nodeInNetworkGroupID" size="1" >
                                <c:forEach var="node" items="${networkGroupResource.nodeInNetworkGroupResources}" varStatus="loop">

                                    <option value="${node.nodeInNetworkGroupID}" ${node.nodeInNetworkGroupID == edgeInNetworkGroupResource.toNodeInNetworkGroupResource.nodeInNetworkGroupID  ? 'selected': ''}> ${node.nodeResource.label} - [${node.nodeResource.title}]</option>


                                </c:forEach>
                            </select>
                            &nbsp;&nbsp;
                            <font color="red"><c:out value="${edgeInNetworkGroupResource.errToNode}"/><c:out value="${edgeInNetworkGroupResource.errNode}"/></font> 
                            <br />

                        </p>



                        <p>
                            <label for="edge_length">Edge Length : </label>

                            <select name="edge_length" size="1" >
                                <option value="10" ${edgeInNetworkGroupResource.edge_length == 10 ? 'selected': ''}> Low </option>
                                <option value="15" ${edgeInNetworkGroupResource.edge_length == 15 ? 'selected': ''}> Medium </option>
                                <option value="20" ${edgeInNetworkGroupResource.edge_length == 20 ? 'selected': ''}> High </option>
                            </select>

                            <br/>
                        </p>

                        <p>
                            <label for="edge_value">Edge Value : </label>

                            <select name="edge_value" size="1" >
                                <option value="10" ${edgeInNetworkGroupResource.edge_value == 10 ? 'selected': ''}> Low </option>
                                <option value="15" ${edgeInNetworkGroupResource.edge_value == 15 ? 'selected': ''}> Medium </option>
                                <option value="20" ${edgeInNetworkGroupResource.edge_value == 20 ? 'selected': ''}> High </option>
                            </select>

                            <br/>
                        </p>


                        <p>
                            <label for="arrows">Arrow : </label>

                            <select name="arrows" size="1" >
                                <option value="" ${edgeInNetworkGroupResource.arrows == '' ? 'selected': ''}> None </option>
                                <option value="from" ${edgeInNetworkGroupResource.arrows == 'from' ? 'selected': ''}> From </option>
                                <option value="to" ${edgeInNetworkGroupResource.arrows == 'to' ? 'selected': ''}> To </option>
                                <option value="to, from" ${edgeInNetworkGroupResource.arrows == 'to, from' ? 'selected': ''}> From And To </option>
                            </select>

                            <br/>
                        </p>

                        <p>
                            <label for="dashes">Dashes Line :</label>
                            <label><input type="checkbox" name="dashes" id="dashes" <c:if test="${edgeInNetworkGroupResource.dashes}">checked</c:if> /> </label>
                                <br/>
                            </p>	

                            <p><label for="enabled">Status:</label>
                                <label><input type="checkbox" name="enabled" id="enabled" checked="true" /> Enabled</label>
                                <br/>
                            </p>

                            <p> <input name="send" class="formbutton" value="Send" type="submit" /> </p>

                            <input name="networkGroupID" type="hidden" value="${networkGroupID}"/>
                        <input name="networkGroupCode" type="hidden" value="${networkGroupCode}"/>

                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}" />
                    </form>
                </fieldset>





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