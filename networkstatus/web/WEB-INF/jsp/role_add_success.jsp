<%@ include file="/WEB-INF/jsp/includes/tagLibs.jsp" %> 
<%@page session="true"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <%@ include file="/WEB-INF/jsp/includes/headerFirst.jsp" %> 
    <body class="noheader">
        <%@ include file="/WEB-INF/jsp/includes/headerSecond.jsp" %> 
        <div id="body">
            <div id="breadcrumbs">
                <span>You are here:</span>
                <strong>Home</strong>
            </div>
            <div id="content">
                <!--Div Content Start-->




                <div class="centerbar">
                    <ul>	
                        <li>
                            <h4 class="h4-green"><span>Role Data Entry Status</span></h4>
                            <ul>
                                <li>
                                    <p style="margin: 0;">

                                        <br/>



                                        <h1>Role Added Successfully.</h1>

                                        <br/>
                                        <h5>Status : Success</h5>

                                        <h5>
                                            Role Name : ${roleResource.name}
                                        </h5>

                                        <h5>
                                            Role Description : ${roleResource.description}
                                        </h5>



                                        <p>
                                            <h3><a href="${initParam.PROJECT_ROOT_PATH}/roleman/role/add"> Add New Role</a> &nbsp; &nbsp; &nbsp;



                                                <script>
                                                    var loginURL = window.location.origin + "${initParam.PROJECT_ROOT_PATH}/login";
                                                    document.getElementById('url').value = loginURL;

                                                </script>

<!--                                                <a href="${initParam.PROJECT_ROOT_PATH}/professorman/professor/add"> Send Email</a></p>-->

                                            </h3>

                                        </p>


                                    </p>
                                </li>
                            </ul>
                        </li>

                    </ul> 
                </div>



                <!--Div Content End-->
                <br/><br/><br/><br/>
            </div>
            <div class="clear"></div>
        </div>
        <%@ include file="/WEB-INF/jsp/includes/footerSecondLast.jsp" %>    
    </body>
    <%@ include file="/WEB-INF/jsp/includes/footerLast.jsp" %>       


