<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <%@ include file="/WEB-INF/jsp/includes/headerFirst_1.jsp" %> 

    <body>
        <style type="text/css">
            fieldset p label  {
                float: left;
                width: 200px;
            }


        </style>
        <%@ include file="/WEB-INF/jsp/includes/headerSecond_1.jsp" %> 

        <section class="title">
            <div class="container">
                <div class="row-fluid">
                    <div class="span6">
                        <h1>HOME</h1>
                    </div>
                    <div class="span6">
                        <ul class="breadcrumb pull-right">
                            <li><a href="${initParam.PROJECT_ROOT_PATH}/home_1">Home</a> <span class="divider">/</span></li>
                            <li class="active">Welcome Page</li>
                        </ul>
                    </div>
                </div>
            </div>
        </section>
        <!-- / .title -->  


<!--        <section class="main-info">
            <div class="container">
                <div class="row-fluid">
                    <div class="span12">


                    </div>

                </div>
            </div>
        </section>-->


        <!--    <section id="services">
                <div class="container">
                    <div class="center gap">
                        <h3>WELCOME ADMIN</h3>
                        <p class="lead">WELCOME ADMIN</p>
                    </div>
                    
        
                </div>
            </section>-->
        
<!--<div class="gap"></div>-->
        <!--Work-->
        <section class="container">
            <div class="row-fluid" style="width: 100%">
                <div class="span12">



                    <table width="100%">
                        <tbody>

                            <tr>
                                <th align="center"> <h1>Welcome To ${initParam.PROJECT_NAME}.</h1></th>

                            </tr>
                        </tbody>
                        <tr>
                            <td style="text-align: center">


                                <br/>
                                <h3> The Network Status Monitoring System </h3>

                                <p class="lead"><span> <h3>Welcome ${pageContext.request.userPrincipal.name}</h3></span></p>

                                <p style="margin: 0;">

                                    <br/>



                                </p>
                                <br/>
                                <h5>Status : ${message}</h5>

                        <sec:authorize access="hasAnyRole('ROLE_USER','ROLE_ADMIN')">
                            <c:if test="${pageContext.request.userPrincipal.name != null}">
                                <h5>
                                    UserName : ${pageContext.request.userPrincipal.name}
                                </h5>
                            </c:if>
                        </sec:authorize>

                        <br/>

<!--                                        <form action="${initParam.PROJECT_ROOT_PATH}/userman/user/testDownloadReport" method="post">
                                            
                                            <input type="submit" value="Download Report">
                                            
                                        </form>-->
                        </td>

                        </tr>

                    </table>    







                </div>  
                <div class="gap"></div>



            </div>
        </section>



<!--        <section class="main-info">
            <div class="container">
                <div class="row-fluid">
                    <div class="span12">


                    </div>

                </div>
            </div>
        </section>-->

        <%@ include file="/WEB-INF/jsp/includes/footerSecondLast_1.jsp" %>    

    </body>
    <%@ include file="/WEB-INF/jsp/includes/footerLast_1.jsp" %>      
