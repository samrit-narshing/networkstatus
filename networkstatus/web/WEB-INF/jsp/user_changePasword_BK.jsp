<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
    <body>
        <h1>Title : ${title}</h1>
        <h1>Message : ${message}</h1>

        <c:url value="/logout" var="logoutUrl" />
        <form action="${logoutUrl}" method="post" id="logoutForm">
            <input type="hidden" name="${_csrf.parameterName}"
                   value="${_csrf.token}" />
        </form>
       <script>
//            function formSubmit() {
//                document.getElementById("logoutForm").submit();
//            }
            function formSubmit(formId) {
                //  alert(formId);
                document.getElementById(formId).submit();
            }
        </script>

        <c:if test="${pageContext.request.userPrincipal.name != null}">
            <h2>
                Welcome : ${pageContext.request.userPrincipal.name} | <a
                    href="javascript:formSubmit('logoutForm')"> Logout</a>
            </h2>
        </c:if>


        <form name="form1" method="post" action="${initParam.PROJECT_ROOT_PATH}/userman/submitChangePasswordUser">
            <input type="hidden" name="${_csrf.parameterName}"
                   value="${_csrf.token}" />
            <input name="id" type="hidden" value="${user.id}">
            <input name="userName" type="hidden" value="${user.userName}">

            <table border="0" style="width: 100%">
                <caption><strong>Change Password:</strong></caption>
                <tr class="odd">
                    <td>Username:</td>
                    <td>${user.userName}</td>
                    <td><font color="red"><c:out value="${user.errUserName}"/></font></td>
                </tr>

                <tr class="odd">
                    <td>Current Password:</td>
                    <td><input name="currentPassword" type="password"></td>
                    <td><font color="red"><c:out value="${user.errCurrentPassword}"/></font></td>
                </tr>

                <tr class="odd">
                    <td>New Password:</td>
                    <td><input name="password" type="password"></td>
                    <td><font color="red"><c:out value="${user.errPassword}"/></font></td>
                </tr>

                <tr class="odd">
                    <td>Confirm Password:</td>
                    <td><input name="confirmPassword" type="password"></td>
                    <td><font color="red"><c:out value="${user.errConfirmPassword}"/></font></td>
                </tr>

                <tr>
                    <td></td>
                    <td> <input name="Save" type="submit" value="Save Settings" id="button_style" style="width: 120px"></td>
                    <td></td>
                </tr>
            </table>
        </form>


  <%@ include file="/WEB-INF/jsp/resources/navigation.jsp" %>

    </body>
</html>