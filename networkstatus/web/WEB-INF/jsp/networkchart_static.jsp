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
                <a href="#">Network Management</a> &raquo;
                <strong>Network Static Chart</strong>
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
                    <legend>Network Static Chart</legend>



                    <style type="text/css">
                        #chartcontainer {
                            width: 100%;
                            height: 100%;
                            margin: 0;
                            padding: 0;
                        }
                    </style>


                    <script>

                        anychart.onDocumentReady(function () {
                            anychart.data.loadJsonFile('${webServerDomainURL}/rest/web/network/node/chartData1', function (data) {

                                // create a chart from the loaded data
                                var chart = anychart.graph(data);

                                // set the title
                                chart.title("Network Static Graph");

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

                    <br>
                    <div style="height: 700px">
                        <div id="fullscreen" style="height: 100%">
                            <!--                                <button onclick="openFullscreen();">Open Fullscreen</button>
                                                            <button onclick="closeFullscreen();">Close Fullscreen</button>-->
                            <input name="send" class="formbutton" value="Open Fullscreen" type="button" onclick="openFullscreen();" />
                            <input name="send" class="formbutton" value="Close Fullscreen" type="button"  onclick="closeFullscreen();"/>

                            <strong>Note:</strong> Internet Explorer 10 and earlier versions do not support the "FullScreen" mode.


                            <div style="height: 90%;" >
                                <div id="chartcontainer"></div>
                            </div>
                            
                            
                            <div style="height: 10%;align-content: center; text-align: center" >
                                <br>
                                <a href="../dynamic_chart/display" style="background: black;color: white;font-size: 15;padding: 3px"> &nbsp&nbsp FOR ADDITIONAL FUNCTION GO TO DYNAMIC CHART &nbsp&nbsp</a>
                            </div>
                        </div>

                    </div>
                    <style>
                                /* Safari syntax */
                                :-webkit-full-screen {
                                    background-color: #ffffff;
                                }

                                /* IE11 */
                                :-ms-fullscreen {
                                    background-color: #ffffff;
                                }

                                /* Standard syntax */
                                :fullscreen {
                                    background-color: #ffffff;
                                }

                                /* Style the button */
                                /*button {
                                  padding: 20px;
                                  font-size: 20px;
                                }*/
                            </style>
                    <script>

                        var elem = document.getElementById("fullscreen");
                        function openFullscreen() {
                            if (elem.requestFullscreen) {
                                elem.requestFullscreen();
                            } else if (elem.webkitRequestFullscreen) { /* Safari */
                                elem.webkitRequestFullscreen();
                            } else if (elem.msRequestFullscreen) { /* IE11 */
                                elem.msRequestFullscreen();
                            }
                        }

                        function closeFullscreen() {
                            if (document.exitFullscreen) {
                                document.exitFullscreen();
                            } else if (document.webkitExitFullscreen) { /* Safari */
                                document.webkitExitFullscreen();
                            } else if (document.msExitFullscreen) { /* IE11 */
                                document.msExitFullscreen();
                            }
                        }
                    </script>




                    <br/><br/><br/>
                </fieldset>









                <fieldset style="display: none">

                    <br/>  <br/>  <br/>



                    <h3>NODE STATUS</h3>


                    <table width="100%" id="sundayResults">
                        <tbody>

                            <tr>
                                <th align="center">Name</th>
                                <th align="center">Type</th>
                                <th align="center">Description</th>
                                <th align="center">Alert</th>
                                <th align="center">Link</th>



                            </tr>


                            <c:forEach items="${tableData.nodeResources}"
                                       var="object"
                                       varStatus="status"
                                       begin="0"
                                       end="1999"
                                       step="1">


                                <tr>
                                    <td align="center"><c:out value="${object.label}" /></td>

                                    <td align="center"><c:out value="${object.type}" /></td>

                                    <td align="center">

                                        ${object.alert.description} 

                                        <br>
                                    </td>

                                    <td align="center">

                                        <c:choose>
                                            <c:when test="${object.alert.type!=1}">
                                                <img src="${initParam.PROJECT_ROOT_PATH}/resources/icons/warning_32x32.png" alt="Checked" border="0" width="20" height="20" />
                                            </c:when>
                                            <c:otherwise>
                                                <img src="${initParam.PROJECT_ROOT_PATH}/resources/icons/round_ok_32x32.png" alt="Checked" border="0" width="20" height="20" />
                                            </c:otherwise>
                                        </c:choose>


                                        <br>
                                    </td>


                                    <td align="center">

                                        &nbsp;

                                        <a href="javascript:formSubmitById('updateNodeAlertToResetFormId_${object.nodeID}')" onclick="return confirmChangeStatus();"  title="Change Status Of Node">

                                            RESOLVE_IT
                                        </a>

                                        <a href="${object.redirectingURLLink}" target="_blank"> RE-DIRECT_LINK </a>
                                        <br>


                                            <form action="${initParam.PROJECT_ROOT_PATH}/nodeman/node/node_alert_to_reset/submit" method="post" id="updateNodeAlertToResetFormId_${object.nodeID}">
                                                <input type="hidden" name="${_csrf.parameterName}"
                                                       value="${_csrf.token}" />
                                                <input type="hidden" name="id"
                                                       value="${object.nodeID}" />
                                            </form>



                                            &nbsp;

                                            <a href="javascript:formSubmitById('updateNodeAlertToDummyInsertFormId_${object.nodeID}')" onclick="return confirmChangeStatus();"  title="Change Status Of Node">

                                                DUMMY_INSERT
                                            </a>


                                            <br>


                                                <form action="${initParam.PROJECT_ROOT_PATH}/nodeman/node/node_alert_to_dummy_insert/submit" method="post" id="updateNodeAlertToDummyInsertFormId_${object.nodeID}">
                                                    <input type="hidden" name="${_csrf.parameterName}"
                                                           value="${_csrf.token}" />
                                                    <input type="hidden" name="id"
                                                           value="${object.nodeID}" />
                                                </form>

                                                </td>








                                                </tr>

                                            </c:forEach>

                                            </tbody>

                                            </table>

                                            <br>
                                                <div id="nodePageNavPosition" align="center"></div>
                                                <script type="text/javascript">
                                                    var nodePager = new Pager('sundayResults', 20);
                                                    nodePager.init();
                                                    nodePager.showPageNav('nodePager', 'nodePageNavPosition');
                                                    nodePager.showPage(1);
                                                </script>

                                                <br/>  <br/>  <br/>





                                                </fieldset>




                                                <!--Div Content End-->
                                                <br/><br/><br/><br/>
                                                </div>
                                                <div class="clear"></div>
                                                </div>
                                                <%@ include file="/WEB-INF/jsp/includes/footerSecondLast.jsp" %>    
                                                </body>
                                                <%@ include file="/WEB-INF/jsp/includes/footerLast.jsp" %>        


                                                <%--<%=machineMode%>--%>
