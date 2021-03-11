<%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.project.util.enums.NODE_TYPE"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<sec:authentication property="principal.authorities" var="authorities" />
<ul class="sf-menu dropdown">
    <!--    <li class="selected"><a href="login.html">Login</a></li>-->
    <!--<li><a href="${initParam.PROJECT_ROOT_PATH}/home_1" style="color: #006dcc">TRY NEW THEME (DEMO)</a></li>-->
    <li><a href="${initParam.PROJECT_ROOT_PATH}/home"> Home</a></li>
    <li><a class="has_submenu" href="#">User Management</a>
        <ul>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <li><a href="javascript:formSubmitById('changePasswordUserFormId_${pageContext.request.userPrincipal.name}')"> Change Password </a></li>
            </c:if>
        </ul>
    </li>



    <li><a class="has_submenu" href="#">Network System</a>
        <ul>
            <c:import var="dataJson" url="${webServerDomainURL}/rest/web/networkgroup/find_all_enabled_networkgroupid"/>
            <%
                String ngIdsWithNamesResponseString = (String) pageContext.getAttribute("dataJson");

                if (ngIdsWithNamesResponseString != null && !ngIdsWithNamesResponseString.trim().equals("")) {
                    String ngIdsWithNames[] = ngIdsWithNamesResponseString.split(",");
                    for (String ngIdWithName : ngIdsWithNames) {
                        boolean isValidRoleToDisplay = false;
                        String ngIdsWithName[] = ngIdWithName.split(":");
                        try {
                            String allowedRoles[] = ngIdsWithName[2].split("#");
                            List<String> allowedRoleList = Arrays.asList(allowedRoles);
                            HashSet<SimpleGrantedAuthority> authorizedRoles = (HashSet<SimpleGrantedAuthority>) pageContext.getAttribute("authorities");

                            for (SimpleGrantedAuthority allowedAuthorizedRole : authorizedRoles) {
                                if (allowedRoleList.contains(allowedAuthorizedRole.getAuthority())) {
                                    isValidRoleToDisplay = true;
                                    break;
                                }

                            }

                            if (isValidRoleToDisplay) {
            %>
            <li><a href="${initParam.PROJECT_ROOT_PATH}/networkman/dynamic_chart/networkgroup/<%=ngIdsWithName[0]%>/display"> <%=ngIdsWithName[1]%></a></li>
                <%
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                %>

        </ul>
    </li>




    <li><a class="has_submenu" href="#">Settings</a>
        <ul>
            <li><a href="${initParam.PROJECT_ROOT_PATH}/system/control_panel">Control Panel</a></li>

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

<!--       <sec:authentication property="principal.authorities" var="authorities" />
<c:forEach items="${authorities}" var="authority" varStatus="vs">
    <p>${authority.authority}</p>
</c:forEach>-->

<!--<c:forEach items="${allowedRolesList}" var="text" varStatus="vs">
                <p>${text}</p>
            </c:forEach>-->