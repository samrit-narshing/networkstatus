<%@ include file="/WEB-INF/jsp/includes/tagLibs.jsp" %> 
<%@page session="true"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <%@ include file="/WEB-INF/jsp/includes/headerFirst.jsp" %> 
    <body class="noheader">
        <%@ include file="/WEB-INF/jsp/includes/headerSecond.jsp" %> 
        <div id="body">
            <div id="breadcrumbs">
                <span>You are here:</span>
                <a href="${initParam.PROJECT_ROOT_PATH}/home">Home</a> &raquo;
                <a href="#">User Management</a> &raquo;
                <strong>Network Chart Test Page</strong>
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


                <script type="text/javascript">
                    function confirmRemove()
                    {
                        var agree = confirm("Are you sure you want to Remove?");
                        if (agree)
                            return true;
                        else
                            return false;
                    }


                    function confirmChangeStatus()
                    {
                        var agree = confirm("Are you sure you want to change Status?");
                        if (agree)
                            return true;
                        else
                            return false;
                    }


                    function messageforadmin(uname)
                    {
                        //                if (uname == 'admin')
                        //                {
                        //                    alert("Warning!!! This action is restricted for this user.");
                        //
                        //                    return false;
                        //                }
                        //                else
                        {
                            return confirmRemove();
                        }
                    }


                </script>



                <fieldset>
                    <legend>Network Chart Test Page</legend>



                    <style type="text/css">
                         #chartcontainer {
                            width: 100%;
                            height: 50%;
                            margin: 0;
                            padding: 0;
                        }
                    </style>


                    <script>

                        anychart.onDocumentReady(function () {
                            anychart.data.loadJsonFile("http://localhost:8084/networkstatus-webservice/rest/device/test/chartData2", function (data) {

                                // create a chart from the loaded data
                                var chart = anychart.graph(data);

                                // set the title
                                chart.title("Network Graph For TestPurpose");

                                // access nodes
                                var nodes = chart.nodes();

                                // set the size of nodes
                                nodes.normal().height(30);
                                nodes.hovered().height(45);
                                nodes.selected().height(45);

                                // set the stroke of nodes
                                nodes.normal().stroke(null);
                                nodes.hovered().stroke("#333333", 3);
                                nodes.selected().stroke("#333333", 3);

                                // enable the labels of nodes
                                chart.nodes().labels().enabled(true);

                                // configure the labels of nodes
                                chart.nodes().labels().format("{%id}");
                                chart.nodes().labels().fontSize(12);
                                chart.nodes().labels().fontWeight(600);

                                // draw the chart
                                chart.container("chartcontainer").draw();

                            });
                        });

                    </script>
                    <div id="chartcontainer"></div>
                </fieldset>

                <h3>NODE STATUS</h3>





                <table width="100%">
                    <tbody>

                        <tr>
                            <th align="center">Name</th>
                            <th align="center">Height</th>
                            <th align="center">Alert</th>
                            <th align="center">Link</th>



                        </tr>


                        <c:forEach items="${chartData.nodes}"
                                   var="traveler"
                                   varStatus="status"
                                   begin="0"
                                   end="1999"
                                   step="1">


                            <tr>
                                <td align="center"><c:out value="${traveler.id}" /></td>

                                <td align="center">

                                    ${traveler.height} 

                                    <br>
                                </td>

                                <td align="center">
                                    ${traveler.alert}


                                    <c:choose>
                                        <c:when test="${traveler.alert}">
                                            <img src="${initParam.PROJECT_ROOT_PATH}/resources/icons/warning_32x32.png" alt="Checked" border="0" width="20" height="20" />
                                        </c:when>
                                        <c:otherwise>
                                            <img src="${initParam.PROJECT_ROOT_PATH}/resources/icons/round_ok_32x32.png" alt="Checked" border="0" width="20" height="20" />
                                        </c:otherwise>
                                    </c:choose>


                                    <br>
                                </td>


                                <td align="center">
                                    <a href="${traveler.link}"> Link </a>
                                    <br>
                                </td>








                            </tr>

                        </c:forEach>

                    </tbody>

                </table>

                <br/>  <br/>  <br/>










                <!--Div Content End-->
                <br/><br/><br/><br/>
            </div>
            <div class="clear"></div>
        </div>
        <%@ include file="/WEB-INF/jsp/includes/footerSecondLast.jsp" %>    
    </body>
    <%@ include file="/WEB-INF/jsp/includes/footerLast.jsp" %>        


    <%--<%=machineMode%>--%>
