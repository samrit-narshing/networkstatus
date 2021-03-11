


<!--Header-->
<header class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>
            <a id="logo" class="pull-left" href="${initParam.PROJECT_ROOT_PATH}/home_1"></a>
            <div class="nav-collapse collapse pull-right">
                <ul class="nav">
                    <li><a href="${initParam.PROJECT_ROOT_PATH}/home" style="color: #006dcc">BACK TO OLD THEME</a></li>
                    <li><a href="${initParam.PROJECT_ROOT_PATH}/home_1">Home</a></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">User Management <i class="icon-angle-down"></i></a>
                        <ul class="dropdown-menu">
                            <li><a href="${initParam.PROJECT_ROOT_PATH}/userman/user/add"> Add System User</a></li>
                            <li><a href="${initParam.PROJECT_ROOT_PATH}/userman/user/search"> List System Users</a></li>
                             <li><a href="javascript:formSubmitById('changePasswordUserFormId_${pageContext.request.userPrincipal.name}')"> Change Password </a></li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Setup <i class="icon-angle-down"></i></a>
                        <ul class="dropdown-menu">
                            <li><a href="${initParam.PROJECT_ROOT_PATH}/nodeman/node/add">Add Nodes</a></li>
                            <li><a href="${initParam.PROJECT_ROOT_PATH}/nodeman/node/search">List Nodes</a></li>
                            <li><a href="${initParam.PROJECT_ROOT_PATH}/edgeman/edge/add">Add Edges</a></li>
                            <li><a href="${initParam.PROJECT_ROOT_PATH}/edgeman/edge/search">List Edges</a></li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Network System <i class="icon-angle-down"></i></a>
                        <ul class="dropdown-menu">
                            <li><a href="${initParam.PROJECT_ROOT_PATH}/networkman/static_chart/display"> Network Chart 1</a></li>
                            <li><a href="${initParam.PROJECT_ROOT_PATH}/networkman/dynamic_chart/display"> Network Chart 2</a></li>
<!--                            <li><a href="${initParam.PROJECT_ROOT_PATH}/networkman/charttest/search"> Network Chart Demo 1</a></li>
                            <li><a href="${initParam.PROJECT_ROOT_PATH}/networkman/charttest2/search">  Network Chart Demo 2</a></li>-->
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Archive <i class="icon-angle-down"></i></a>
                        <ul class="dropdown-menu">
                            <li><a href="${initParam.PROJECT_ROOT_PATH}/nodealertinfoman/nodealertinfo/search">Network Alert Info</a></li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Settings <i class="icon-angle-down"></i></a>
                        <ul class="dropdown-menu">
                            <li><a href="${initParam.PROJECT_ROOT_PATH}/system/control_panel_1">Control Panel</a></li>
                            <li><a href="${initParam.PROJECT_ROOT_PATH}/schedulerman/schedule/display">Sync By Scheduler</a></li>
                            <li><a href="${initParam.PROJECT_ROOT_PATH}/applicationlogman/applicationlog_1/search">Log Messages</a></li>
                        </ul>
                    </li>
                </ul>        
            </div>
        </div>
    </div>
</header>
<!-- /header -->






<form action="${initParam.PROJECT_ROOT_PATH}/userman/user_1/changePassword" method="post" id="changePasswordUserFormId_${pageContext.request.userPrincipal.name}">
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


<%
//    if(license.LicenseManagement.isExpired(request))
//    {
//        response.sendRedirect(request.getContextPath()+"/redirectLicenseConfig.jsp");
//    }
%>
