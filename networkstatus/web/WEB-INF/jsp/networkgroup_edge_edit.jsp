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
                <strong>Edit Network Group Edge</strong>
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
                    <legend>Edit Network Group Edge</legend>
                    <form  name="form1" method="post" action="${initParam.PROJECT_ROOT_PATH}/networkgroupman/networkgroup/edge/edit/submit">
                        <br/>




                        <p><label for="fromNodeInNetworkGroupResource.nodeID">From Node : </label>
                            <input name="fromNodeInNetworkGroupResource.nodeResource.label"  value="${edgeInNetworkGroupResource.fromNodeInNetworkGroupResource.nodeResource.label}"  size="40"  readonly="true" style="border: 0"/>

                            <input name="fromNodeInNetworkGroupResource.nodeResource.nodeID"  value="${edgeInNetworkGroupResource.fromNodeInNetworkGroupResource.nodeResource.nodeID}"  size="40"  readonly="true" style="border: 0" type="hidden"/>

                            <br />
                        </p>


                        <p><label for="toNodeInNetworkGroupResource.nodeID">To Node : </label>
                            <input name="toNodeInNetworkGroupResource.nodeResource.label"  value="${edgeInNetworkGroupResource.toNodeInNetworkGroupResource.nodeResource.label}"  size="40"  readonly="true" style="border: 0"/>
                            <input name="toNodeInNetworkGroupResource.nodeResource.nodeID"  value="${edgeInNetworkGroupResource.toNodeInNetworkGroupResource.nodeResource.nodeID}"  size="40"  readonly="true" style="border: 0"type="hidden"/>

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

                            <input name="networkGroupId" type="hidden" value="${networkGroupId}"/>
                        <input name="networkGroupCode" type="hidden" value="${networkGroupCode}"/>
                        <input name="edgeInNetworkGroupId" type="hidden" value="${edgeInNetworkGroupResource.edgeInNetworkGroupID}"/>

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
                            <input name="id" type="hidden" value="${networkGroupId}"/>
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