<%@ include file="/WEB-INF/jsp/includes/tagLibs.jsp" %> 
<%@page session="true"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <%@ include file="/WEB-INF/jsp/includes/headerFirst.jsp" %> 
    <body class="noheader" onload='document.loginForm.username.focus();'>
        <%@ include file="/WEB-INF/jsp/includes/headerSecond.jsp" %> 
        <div id="body">
            <div id="breadcrumbs">
                <span>You are here:</span>
                <a href="index.html">Home</a> &raquo;
                <strong>Error Stack Page</strong>
            </div>


            <div id="content">
        
        
                <h1>Application Error</h1>

                <c:choose>
                    <c:when test="${empty username}">
                        <h2>Application Error, please contact support.</h2>
                    </c:when>
                    <c:otherwise>
                        <h2>Username : ${username} <br/>Application Error, please contact support.</h2>
                    </c:otherwise>
                </c:choose>

                


        <h3>Debug Information:</h3>

        Requested URL= ${url}<br><br>

        Exception= ${exception.message}<br><br>

        <strong>Exception Stack Trace</strong><br>
        <c:forEach items="${exception.stackTrace}" var="ste">
            ${ste}
        </c:forEach>

        
        
        
        
        
            
            
                <br/><br/><br/><br/>
            </div>





            <div class="clear"></div>
        </div>
        <%@ include file="/WEB-INF/jsp/includes/footerSecondLast.jsp" %>    
    </body>
    <%@ include file="/WEB-INF/jsp/includes/footerLast.jsp" %>       
