<%@page import="com.project.util.enums.NODE_TYPE"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<ul class="sf-menu dropdown">
    <!--    <li class="selected"><a href="login.html">Login</a></li>-->
    <!--<li><a href="${initParam.PROJECT_ROOT_PATH}/home_1" style="color: #006dcc">TRY NEW THEME (DEMO)</a></li>-->
    <li><a href="${initParam.PROJECT_ROOT_PATH}/home"> Home</a></li>
    <li><a class="has_submenu" href="#">User Management</a>
        <ul>
            <li><a href="${initParam.PROJECT_ROOT_PATH}/userman/user/add"> Add System User</a></li>
            <li><a href="${initParam.PROJECT_ROOT_PATH}/userman/user/search"> List System Users</a></li>

            <li><a href="${initParam.PROJECT_ROOT_PATH}/roleman/role/add"> Add Department Role</a></li>
            <li><a href="${initParam.PROJECT_ROOT_PATH}/roleman/role/search"> List Department Roles</a></li>

            <li><a href="${initParam.PROJECT_ROOT_PATH}/departmentuserman/departmentuser/add"> Add Department User</a></li>
            <li><a href="${initParam.PROJECT_ROOT_PATH}/departmentuserman/departmentuser/search"> List Department User</a></li>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <li><a href="javascript:formSubmitById('changePasswordUserFormId_${pageContext.request.userPrincipal.name}')"> Change Password </a></li>
            </c:if>
        </ul>
    </li>

    <li><a class="has_submenu" href="#">Setup</a>
        <ul>

            <li><a href="${initParam.PROJECT_ROOT_PATH}/nodeman/node/add">Add Nodes</a></li>
            <li><a href="${initParam.PROJECT_ROOT_PATH}/nodeman/node/search">List Nodes</a></li>
            <li><a href="${initParam.PROJECT_ROOT_PATH}/edgeman/edge/add">Add Edges</a></li>
            <li><a href="${initParam.PROJECT_ROOT_PATH}/edgeman/edge/search">List Edges</a></li>
            <li><a href="${initParam.PROJECT_ROOT_PATH}/networkgroupman/networkgroup/add">Add Network Group</a></li>
            <li><a href="${initParam.PROJECT_ROOT_PATH}/networkgroupman/networkgroup/search">List Network Group</a></li>
            <li><a href="${initParam.PROJECT_ROOT_PATH}/nodeman/node/add/upload_csv">Import Nodes (CSV)</a></li>
        </ul>
    </li>

    <li><a class="has_submenu" href="#">Network System</a>
        <ul>
            <li><a href="${initParam.PROJECT_ROOT_PATH}/networkman/static_chart/display"> Network Static Chart</a></li>
            <li><a href="${initParam.PROJECT_ROOT_PATH}/networkman/dynamic_chart/display"> Network Dynamic Chart</a></li>
<!--            <li><a href="${initParam.PROJECT_ROOT_PATH}/networkman/charttest/search"> Dummy Demo 1</a></li>
            <li><a href="${initParam.PROJECT_ROOT_PATH}/networkman/charttest2/search"> Dummy Demo 2</a></li>-->


            <c:import var="dataJson" url="${webServerDomainURL}/rest/web/networkgroup/find_all_enabled_networkgroupid"/>
            <%
                String ngIdsWithNamesResponseString = (String) pageContext.getAttribute("dataJson");
                System.out.println("MOTO -  " + ngIdsWithNamesResponseString);
                if (ngIdsWithNamesResponseString != null && !ngIdsWithNamesResponseString.trim().equals("")) {
                    String ngIdsWithNames[] = ngIdsWithNamesResponseString.split(",");
                    for (String ngIdWithName : ngIdsWithNames) {
                        String ngIdsWithName[] = ngIdWithName.split(":");
            %>
            <li><a href="${initParam.PROJECT_ROOT_PATH}/networkman/dynamic_chart/networkgroup/<%=ngIdsWithName[0]%>/display"> <%=ngIdsWithName[1]%></a></li>
                <%
                        }
                    }
                %>

        </ul>
    </li>


    <li><a class="has_submenu" href="#">Archives</a>
        <ul>
            <li><a href="${initParam.PROJECT_ROOT_PATH}/nodealertinfoman/nodealertinfo/search">Network Alert Info</a></li>
            <!--<li><a href="${initParam.PROJECT_ROOT_PATH}/nodealertinfoman/nodealertinfo_1/search">Network Alert Info New</a></li>-->
        </ul>
    </li>

    <li><a class="has_submenu" href="#">Settings</a>
        <ul>
            <li><a href="${initParam.PROJECT_ROOT_PATH}/system/control_panel">Control Panel</a></li>
            <li><a href="${initParam.PROJECT_ROOT_PATH}/schedulerman/schedule/display">Sync By Scheduler</a></li>
            <li><a href="${initParam.PROJECT_ROOT_PATH}/applicationlogman/applicationlog/search">Log Messages</a></li>
        </ul>
    </li>





    <%--<%=NODE_TYPE.TYPE_PRINTER.type()%>--%>
    <!--${NODE_TYPE.TYPE_PRINTER.type()}-->

</ul>


<form action="${initParam.PROJECT_ROOT_PATH}/userman/user/changePassword" method="post" id="changePasswordUserFormId_${pageContext.request.userPrincipal.name}">
    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}" />
    <input type="hidden" name="userName"
           value="${pageContext.request.userPrincipal.name}" />
</form> 



<form action="${initParam.PROJECT_ROOT_PATH}/usermanpublic/changePasswordUser" method="post" id="changePasswordPublicUserFormId_${pageContext.request.userPrincipal.name}">
    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}" />
    <input type="hidden" name="userName"
           value="${pageContext.request.userPrincipal.name}" />
</form> 


<%//    if(license.LicenseManagement.isExpired(request))
//    {
//        response.sendRedirect(request.getContextPath()+"/redirectLicenseConfig.jsp");
//    }
%>
<!--${pageContext.request.userPrincipal.name}-->

<!--<sec:authentication property="principal.authorities" var="authorities" />
<c:forEach items="${authorities}" var="authority" varStatus="vs">
<p>${authority.authority}</p>
</c:forEach>-->