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
                <strong>Edit Node</strong>
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
                    <legend>Edit Node</legend>
                    <form  name="form1" method="post" action="${initParam.PROJECT_ROOT_PATH}/nodeman/node/edit/submit">
                        <br/>

                        <p><label for="label">Label :</label>
                            <input name="label" type="text" value="${nodeResource.label}" size="40"  readonly="true" style="border: 0"/>

                            <font color="red"><c:out value="${nodeResource.errLabel}"/></font> 
                            <br />
                        </p>	

                        <p><label for="title">Title :</label>
                            <input name="title" type="text" value="${nodeResource.title}" size="40"/>
                            <font color="red"><c:out value="${nodeResource.errTitle}"/></font> 
                            <br />
                        </p>	

                        <p><label for="redirectingURLLink">Redirect Link : </label>
                            <input name="redirectingURLLink" id="code" value="${nodeResource.redirectingURLLink}"  size="40">
                                &nbsp;&nbsp;
                                <font color="red"><c:out value="${nodeResource.errRedirectingURLLink}"/></font> 
                                <br />
                        </p>

                        <!--                        <p><label for="fill.src">Image Link : </label>
                                                    <input name="fill.src" id="code" value="${nodeResource.fill.src}"  size="40">
                        
                                                        <br />
                                                </p>-->

                        <p>
                            <label for="gender">Type : </label>

                            <select name="type" size="1" >
<!--                                <option value="type-router" ${nodeResource.type == 'type-router' ? 'selected': ''}> Router </option>
                                <option value="type-computer" ${nodeResource.type == 'type-computer' ? 'selected': ''}> Computer </option>
                                <option value="type-server" ${nodeResource.type == 'type-server' ? 'selected': ''}> Server </option>
                                <option value="type-database" ${nodeResource.type == 'type-database' ? 'selected': ''}> Database </option>
                                <option value="type-hub" ${nodeResource.type == 'type-hub' ? 'selected': ''}> Hub </option>
                                <option value="type-printer" ${nodeResource.type == 'type-printer' ? 'selected': ''}> Printer </option>
                                <option value="type-switch" ${nodeResource.type == 'type-switch' ? 'selected': ''}> Switch </option>-->

                                <option value="${NODE_TYPE.TYPE_ROUTER.type()}" ${nodeResource.type == NODE_TYPE.TYPE_ROUTER.type()  ? 'selected': ''}> Router </option>
                                <option value="${NODE_TYPE.TYPE_COMPUTER.type()}" ${nodeResource.type == NODE_TYPE.TYPE_COMPUTER.type() ? 'selected': ''}> Computer </option>
                                <option value="${NODE_TYPE.TYPE_SERVER.type()}" ${nodeResource.type == NODE_TYPE.TYPE_SERVER.type() ? 'selected': ''}> Server </option>
                                <option value="${NODE_TYPE.TYPE_DATABASE.type()}" ${nodeResource.type == NODE_TYPE.TYPE_DATABASE.type() ? 'selected': ''}> Database </option>
                                <option value="${NODE_TYPE.TYPE_HUB.type()}" ${nodeResource.type == NODE_TYPE.TYPE_HUB.type() ? 'selected': ''}> Hub </option>
                                <option value="${NODE_TYPE.TYPE_PRINTER.type()}" ${nodeResource.type == NODE_TYPE.TYPE_PRINTER.type() ? 'selected': ''}> Printer </option>
                                <option value="${NODE_TYPE.TYPE_SWITCH.type()}" ${nodeResource.type == NODE_TYPE.TYPE_SWITCH.type() ? 'selected': ''}> Switch </option>
                            </select>

                            <br/>
                        </p>

                        <p><label for="description">Description :</label>
                            <input name="description" type="text" value="${nodeResource.description}" size="40"/>

                            <br />
                        </p>	


                        <p><label for="enabled">Status:</label>
                            <label><input type="checkbox" name="enabled" id="enabled" <c:if test="${nodeResource.enabled}">checked</c:if> /> Enabled</label>
                                <br/>
                            </p>	

                            <p> <input name="send" class="formbutton" value="Send" type="submit" /> </p>

                            <input name="id" type="hidden" value="${nodeResource.nodeID}"/>

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
                                <form action="${initParam.PROJECT_ROOT_PATH}/nodeman/node/search" method="post" id="navigateBack">
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