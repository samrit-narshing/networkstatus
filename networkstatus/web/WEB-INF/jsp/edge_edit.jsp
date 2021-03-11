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
                <strong>Edit Edge</strong>
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
                    <legend>Edit Edge</legend>
                    <form  name="form1" method="post" action="${initParam.PROJECT_ROOT_PATH}/edgeman/edge/edit/submit">
                        <br/>



                        <p><label for="fromNodeResource.nodeID">From Node : </label>
                            <input name="fromNodeResource.label"  value="${edgeResource.fromNodeResource.label}"  size="40"  readonly="true" style="border: 0">

                                <input name="fromNodeResource.nodeID"  value="${edgeResource.fromNodeResource.nodeID}"  size="40"  readonly="true" style="border: 0" type="hidden">

                                    <br />
                                    </p>


                                    <p><label for="toNodeResource.nodeID">To Node : </label>
                                        <input name="toNodeResource.label"  value="${edgeResource.toNodeResource.label}"  size="40"  readonly="true" style="border: 0">
                                            <input name="toNodeResource.nodeID"  value="${edgeResource.toNodeResource.nodeID}"  size="40"  readonly="true" style="border: 0"type="hidden">

                                                <br />
                                                </p>


                                                <p><label for="label">Label :</label>
                                                    <input name="label" type="text" value="${edgeResource.label}" size="40"/>
                                                    &nbsp;&nbsp;
                                                    <font color="red"><c:out value="${edgeResource.errLabel}"/></font> 

                                                    <br />
                                                </p>	


                                                <p><label for="title">Title :</label>
                                                    <input name="title" type="text" value="${edgeResource.title}" size="40"/>
                                                    &nbsp;&nbsp;
                                                    <font color="red"><c:out value="${edgeResource.errTitle}"/></font> 

                                                    <br />
                                                </p>	

                                                <p>
                                                    <label for="edge_length">Edge Length : </label>

                                                    <select name="edge_length" size="1" >
                                                        <option value="10" ${edgeResource.edge_length == 10 ? 'selected': ''}> Low </option>
                                                        <option value="15" ${edgeResource.edge_length == 15 ? 'selected': ''}> Medium </option>
                                                        <option value="20" ${edgeResource.edge_length == 20 ? 'selected': ''}> High </option>
                                                    </select>

                                                    <br/>
                                                </p>

                                                <p>
                                                    <label for="edge_value">Edge Value : </label>

                                                    <select name="edge_value" size="1" >
                                                        <option value="10" ${edgeResource.edge_value == 10 ? 'selected': ''}> Low </option>
                                                        <option value="15" ${edgeResource.edge_value == 15 ? 'selected': ''}> Medium </option>
                                                        <option value="20" ${edgeResource.edge_value == 20 ? 'selected': ''}> High </option>
                                                    </select>

                                                    <br/>
                                                </p>

                                                <p>
                                                    <label for="arrows">Arrow : </label>

                                                    <select name="arrows" size="1" >
                                                        <option value="" ${edgeResource.arrows == '' ? 'selected': ''}> None </option>
                                                        <option value="from" ${edgeResource.arrows == 'from' ? 'selected': ''}> From </option>
                                                        <option value="to" ${edgeResource.arrows == 'to' ? 'selected': ''}> To </option>
                                                        <option value="to, from" ${edgeResource.arrows == 'to, from' ? 'selected': ''}> From And To </option>
                                                    </select>

                                                    <br/>
                                                </p>

                                                <p>
                                                    <label for="dashes">Dashes Line :</label>
                                                    <label><input type="checkbox" name="dashes" id="dashes" <c:if test="${edgeResource.dashes}">checked</c:if> /> </label>
                                                        <br/>
                                                    </p>	


                                                    <input type="hidden" name="enabled" id="enabled" value="${edgeResource.enabled}" /> 

                                                <p> <input name="send" class="formbutton" value="Send" type="submit" /> </p>
                                                <input name="id" type="hidden" value="${edgeResource.edgeID}"/>
                                                <input type="hidden" name="${_csrf.parameterName}"
                                                       value="${_csrf.token}" />
                                                </form>
                                                </fieldset>



                                                <br><br>
                                                        <div style="text-align: center">

                                                            <a href="javascript:formSubmitById('navigateBack')"  title="Back">
                                                                <img src="${initParam.PROJECT_ROOT_PATH}/resources/icons/left_arrow_32x32.png" alt="Checked" border="0" width="20" height="20" />
                                                            </a>
                                                            &nbsp;

                                                            <div style="display: none">
                                                                <form action="${initParam.PROJECT_ROOT_PATH}/edgeman/edge/search" method="post" id="navigateBack">
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