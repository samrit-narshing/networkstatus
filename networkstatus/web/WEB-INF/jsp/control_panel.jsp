<%@ include file="/WEB-INF/jsp/includes/tagLibs.jsp" %> 
<%@page session="true"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <%@ include file="/WEB-INF/jsp/includes/headerFirst.jsp" %> 





    <style>



        .ui-progressbar-value {
            background-color: #ccc;
            background: #ccc;
        }


        .ui-progressbar {
            position: relative;
        }
        .progress-label-physical-ram {
            position: absolute;
            /*--left: 50%;*/
            left: 40%;
            top: 4px;
            font-weight: bold;
            text-shadow: 1px 1px 0 #fff;
            color: green;


        }
        .progress-label-swap-ram {
            position: absolute;
            /*--left: 50%;*/
            left: 40%;
            top: 4px;
            font-weight: bold;
            text-shadow: 1px 1px 0 #fff;
            color: green;

        }

        .progress-label-overall-cpu {
            position: absolute;
            /*--left: 50%;*/
            left: 40%;
            top: 4px;
            font-weight: bold;
            text-shadow: 1px 1px 0 #fff;
            color: green;
            /*color: #ffffff;*/
            /*background-color: #f2dbdb;*/
            /*background-color: #d99594;*/

        }

        .button_style {
            color: #9999CC;
            /*color: #ffffff;*/
            /*background-color: #f2dbdb;*/
            /*background-color: #d99594;*/
            background-color: #fff;
            font-family: Tahoma, Geneva, sans-serif;
        }


        .progress-label-jvm {
            position: relative;
            /*        left: 2%;
            */

            /*padding: 10px;*/
            top: 2px;
            /*            font-weight: bold;*/
            text-shadow: 1px 1px 0 #fff;

            color: green;
            border-color: #9999CC;
            border-width: 0.1px;
        }

        /*        #appserver_progressbarphysicalram
                {
                    position: relative;
                    min-height: 20px;
                   
                }*/


        .webserver_progress-label-physical-ram {
            position: absolute;
            /*--left: 50%;*/
            left: 40%;
            top: 4px;
            font-weight: bold;
            text-shadow: 1px 1px 0 #fff;
            color: green;


        }

        .webserver_progress-label-swap-ram {
            position: absolute;
            /*--left: 50%;*/
            left: 40%;
            top: 4px;
            font-weight: bold;
            text-shadow: 1px 1px 0 #fff;
            color: green;

        }

        .webserver_progress-label-jvm {
            position: relative;
            /*        left: 2%;
            */

            /*padding: 10px;*/
            top: 2px;
            /*            font-weight: bold;*/
            text-shadow: 1px 1px 0 #fff;

            color: green;
            border-color: #9999CC;
            border-width: 0.1px;
        }

    </style>


    <body class="noheader" onload='init();'>
        <%@ include file="/WEB-INF/jsp/includes/headerSecond.jsp" %> 



        <script type="text/javascript">


            function appServerPhysicalMemoryStatustimer() {

                var appserver_progressbarphysicalram = $("#appserver_progressbarphysicalram");
                var appserver_progresslabelphysicalram = $(".progress-label-physical-ram");

                appserver_progressbarphysicalram.progressbar({
                    value: false,
                    change: function () {
                        if (appserver_progressbarphysicalram.progressbar("value") === false)
                        {
                            appserver_progresslabelphysicalram.text("Reloading Data.");
                        } else
                        {
                            appserver_progresslabelphysicalram.text($("#appserver_usedphysicalram").val() + "/" + $("#appserver_totalphysicalram").val() + " (" + appserver_progressbarphysicalram.progressbar("value") + "%)");
                        }

                    }
                });
                //            var currentdatetime = jQuery('#appserver_jvmcpustatus');
                $.ajax({
                    type: "GET",
                    cache: false,
                    url: '${pageContext.request.contextPath}/system/control_panel/ajaxPhysicalMemoryStatus',
                    data: "name=",
                    success: function (data) {
                        //alert(data);
                        if (data.indexOf('SamritNarshingAmatya@samrit_narshing@hotmail.com9841249759') >= 0) {
                            window.location.assign('${pageContext.request.contextPath}/login');
                        }

                        var dataarray = data.split(';');

                        $("#appserver_usedphysicalram").val(dataarray[1]);
                        $("#appserver_totalphysicalram").val(dataarray[2]);
                        // alert('333');
                        appserver_progressbarphysicalram.progressbar("option", {
                            //                            value: Math.floor(Math.random() * 100)
                            value: Math.floor(dataarray[0])
                        });

                        //                    currentdatetime.val(displaymessage);
                    },
                    error: function (e) {
                        //alert('Error: ' + e);
                    }
                });
            }

            var intervalId = 0;
            intervalId = setInterval(appServerPhysicalMemoryStatustimer, 3000);
        </script>

        <script type="text/javascript">

            function appServerSwapMemoryStatustimer() {

                var appserver_progressbarswapram = $("#appserver_progressbarswapram");
                var appserver_progresslabelswapram = $(".progress-label-swap-ram");

                appserver_progressbarswapram.progressbar({
                    value: false,
                    change: function () {

                        if (appserver_progressbarswapram.progressbar("value") === false)
                        {
                            appserver_progresslabelswapram.text("Reloading Data.");
                        } else
                        {
                            appserver_progresslabelswapram.text($("#appserver_usedswapram").val() + "/" + $("#appserver_totalswapram").val() + " (" + appserver_progressbarswapram.progressbar("value") + "%)");
                        }
                    }
                });
                //            var currentdatetime = jQuery('#appserver_jvmcpustatus');
                $.ajax({
                    type: "GET",
                    cache: false,
                    url: '${pageContext.request.contextPath}/system/control_panel/ajaxSwapMemoryStatus',
                    data: "name=",
                    success: function (data) {
                        //alert(data);
                        if (data.indexOf('SamritNarshingAmatya@samrit_narshing@hotmail.com9841249759') >= 0) {
                            window.location.assign('${pageContext.request.contextPath}/login');
                        }


                        var dataarray = data.split(';');

                        $("#appserver_usedswapram").val(dataarray[1]);
                        $("#appserver_totalswapram").val(dataarray[2]);


                        appserver_progressbarswapram.progressbar("option", {
                            //                            value: Math.floor(Math.random() * 100)
                            value: Math.floor(dataarray[0])


                        });

                        //                    currentdatetime.val(displaymessage);
                    },
                    error: function (e) {
                        // alert('Error: ' + e);
                    }
                });
            }

            var intervalId = 0;
            intervalId = setInterval(appServerSwapMemoryStatustimer, 3000);
        </script>

        <script type="text/javascript">


            function webServerPhysicalMemoryStatustimer() {

                var webserver_progressbarphysicalram = $("#webserver_progressbarphysicalram");
                var webserver_progresslabelphysicalram = $(".webserver_progress-label-physical-ram");

                webserver_progressbarphysicalram.progressbar({
                    value: false,
                    change: function () {
                        if (webserver_progressbarphysicalram.progressbar("value") === false)
                        {
                            webserver_progresslabelphysicalram.text("Reloading Data.");
                        } else
                        {
                            webserver_progresslabelphysicalram.text($("#webserver_usedphysicalram").val() + "/" + $("#webserver_totalphysicalram").val() + " (" + webserver_progressbarphysicalram.progressbar("value") + "%)");
                        }

                    }
                });
                $.ajax({
                    type: "GET",
                    cache: false,
                    url: '${webServerDomainURL}/rest/web/info/control_panel/unsecure/ajax_physicalmemory_status',
                    data: "name=",
                    success: function (data) {
                        //alert(data);
                        if (data.indexOf('SamritNarshingAmatya@samrit_narshing@hotmail.com9841249759') >= 0) {
                            window.location.assign('${pageContext.request.contextPath}/login');
                        }

                        var dataarray = data.split(';');

                        $("#webserver_usedphysicalram").val(dataarray[1]);
                        $("#webserver_totalphysicalram").val(dataarray[2]);
                        // alert('333');
                        webserver_progressbarphysicalram.progressbar("option", {
                            //                            value: Math.floor(Math.random() * 100)
                            value: Math.floor(dataarray[0])
                        });

                        //                    currentdatetime.val(displaymessage);
                    },
                    error: function (e) {
                        //alert('Error: ' + e);
                    }
                });
            }

            var intervalId = 0;
            intervalId = setInterval(webServerPhysicalMemoryStatustimer, 3000);
        </script>



        <script type="text/javascript">

            function webServerSwapMemoryStatustimer() {

                var webserver_progressbarswapram = $("#webserver_progressbarswapram");
                var webserver_progresslabelswapram = $(".webserver_progress-label-swap-ram");

                webserver_progressbarswapram.progressbar({
                    value: false,
                    change: function () {

                        if (webserver_progressbarswapram.progressbar("value") === false)
                        {
                            webserver_progresslabelswapram.text("Reloading Data.");
                        } else
                        {
                            webserver_progresslabelswapram.text($("#webserver_usedswapram").val() + "/" + $("#webserver_totalswapram").val() + " (" + webserver_progressbarswapram.progressbar("value") + "%)");
                        }
                    }
                });
                //            var currentdatetime = jQuery('#appserver_jvmcpustatus');
                $.ajax({
                    type: "GET",
                    cache: false,
                    url: '${webServerDomainURL}/rest/web/info/control_panel/unsecure/ajax_swapmemory_status',
                    data: "name=",
                    success: function (data) {
                        //alert(data);
                        if (data.indexOf('SamritNarshingAmatya@samrit_narshing@hotmail.com9841249759') >= 0) {
                            window.location.assign('${pageContext.request.contextPath}/login');
                        }


                        var dataarray = data.split(';');

                        $("#webserver_usedswapram").val(dataarray[1]);
                        $("#webserver_totalswapram").val(dataarray[2]);


                        webserver_progressbarswapram.progressbar("option", {
                            //                            value: Math.floor(Math.random() * 100)
                            value: Math.floor(dataarray[0])


                        });

                        //                    currentdatetime.val(displaymessage);
                    },
                    error: function (e) {
                        // alert('Error: ' + e);
                    }
                });
            }

            var intervalId = 0;
            intervalId = setInterval(webServerSwapMemoryStatustimer, 3000);
        </script>







        <style type="text/css">
            fieldset p label {
                float: left;
                width:350px;
            }

            #console_style {
                font-family: monospace;
                font-size: 130%;
                font-weight: normal;
                /*           line-height: 5px;*/
            }
        </style>


        <script type="text/javascript">
            function confirmSubmit()
            {
                var agree = confirm("Do you want to continue?");
                if (agree)
                    return true;
                else
                    return false;
            }
        </script>



        <script type="text/javascript">

            function init()
            {
                fireEvent(document.getElementById('linking'), 'click');
                appServerPhysicalMemoryStatustimer();
                appServerSwapMemoryStatustimer();
                appServerJvmStatusTimer();

                webServerPhysicalMemoryStatustimer();
                webServerSwapMemoryStatustimer();
                webServerJvmStatusTimer();
            }

            function fireEvent(obj, evt) {
                var fireOnThis = obj;
                if (document.createEvent) {
                    var evObj = document.createEvent('MouseEvents');
                    evObj.initEvent(evt, true, false);
                    fireOnThis.dispatchEvent(evObj);
                } else if (document.createEventObject) {
                    fireOnThis.fireEvent('on' + evt);
                }
            }


        </script>




        <script type="text/javascript">

            function appServerJvmStatusTimer() {
                var currentdatetime = jQuery('#appserver_jvmcpustatus');
                $.ajax({
                    type: "GET",
                    cache: false,
                    url: '${pageContext.request.contextPath}/system/control_panel/ajaxJVMStatus',
                    data: "name=",
                    success: function (data) {
                        //alert(data);
                        if (data.indexOf('SamritNarshingAmatya@samrit_narshing@hotmail.com9841249759') >= 0) {
                            window.location.assign('${pageContext.request.contextPath}/login');
                        }

                        var dataarray = data.split(';');
                        var displaymessage = 'Used Memory :' + dataarray[0] + 'MB. Free Memory :' + dataarray[1] + 'MB. Total Memory :' + dataarray[2] + '. Max Memory :' + dataarray[3] + 'MB';

                        currentdatetime.val(displaymessage);
                    },
                    error: function (e) {
                        //  alert('Error: ' + e);
                    }
                });
            }

            var intervalId = 0;
            intervalId = setInterval(appServerJvmStatusTimer, 3000);
        </script>


        <script type="text/javascript">

            function webServerJvmStatusTimer() {
                var currentdatetime = jQuery('#webserver_jvmcpustatus');
                $.ajax({
                    type: "GET",
                    cache: false,
                    url: '${webServerDomainURL}/rest/web/info/control_panel/unsecure/ajax_jvm_status',
                    data: "name=",
                    success: function (data) {
                        //alert(data);
                        if (data.indexOf('SamritNarshingAmatya@samrit_narshing@hotmail.com9841249759') >= 0) {
                            window.location.assign('${pageContext.request.contextPath}/login');
                        }

                        var dataarray = data.split(';');
                        var displaymessage = 'Used Memory :' + dataarray[0] + 'MB. Free Memory :' + dataarray[1] + 'MB. Total Memory :' + dataarray[2] + '. Max Memory :' + dataarray[3] + 'MB';

                        currentdatetime.val(displaymessage);
                    },
                    error: function (e) {
                        //  alert('Error: ' + e);
                    }
                });
            }

            var intervalId = 0;
            intervalId = setInterval(webServerJvmStatusTimer, 3000);
        </script>








        <a href="#${pagelink}" id="linking" style="display: none"></a>

        <div id="body">
            <div id="breadcrumbs">
                <span>You are here:</span>
                <a href="${initParam.PROJECT_ROOT_PATH}/home">Home</a> &raquo;
                <a href="#">System</a> &raquo;
                <strong>Control Panel</strong>
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



                <fieldset class="restore_default_form_element_style">


                    <legend>Control Panel</legend>
                    <br/>
                    <br/>

                    <p><label for="username"><b>Username :</b></label>
                        <input name="username" type="text" value="${webServerControlPanel.username}" readonly="true" size="80" height="">
                            <br />
                    </p>

                    <p><label for="userLicenseExpiryDate"><b>User's License Expiry Date :</b></label>
                        <input name="userLicenseExpiryDate" type="text" value="${webServerControlPanel.userLicenseExpiryDate}" readonly="true" size="80" height="">
                            <br />
                    </p>


                    <p><label for="appServerVersion"><b>Application Server's Version :</b></label>
                        <input name="appServerVersion" type="text" value="${appServerControlPanel.appVersion}" readonly="true" size="80" height="">
                            <br />
                    </p>

                    <p><label for="appServerMacAddress"><b>Application Server's Mac Address :</b></label>
                        <input name="appServerMacAddress" type="text" value="${appServerControlPanel.macAddress}" readonly="true" size="80" height="">
                            <br />
                    </p>

                    <p><label for="appServerHostId"><b>Application Server's Host ID :</b></label>
                        <input name="appServerHostId" type="text" value="${appServerControlPanel.hostId}" readonly="true" size="80">
                            <br />
                    </p>	

                    <p><label for="appServerLicenseExpiryDate"><b>Application Server's License Expiry Date :</b></label>
                        <input name="appServerLicenseExpiryDate" type="text" value="${appServerControlPanel.licenseExpiryDate}" readonly="true" size="80">
                            <br />
                    </p>

                    <p><label for="appserver_progressbarphysicalram"><b>Application Server's Physical Memory :</b></label>
                        <div id="appserver_progressbarphysicalram" style="width: 599px"><div class="progress-label-physical-ram"  >Loading...</div></div>
                        <input type="hidden" id="appserver_usedphysicalram"/>
                        <input type="hidden" id="appserver_totalphysicalram"/>
                    </p>



                    <p><label for="appserver_progressbarswapram"><b>Application Server's Swap Memory :</b></label>
                        <div id="appserver_progressbarswapram" style="width: 599px"><div class="progress-label-swap-ram">Loading...</div> </div>
                        <input type="hidden" id="appserver_usedswapram"/>
                        <input type="hidden" id="appserver_totalswapram"/>
                    </p>


                    <p><label for="appserver_jvmcpustatus"><b>Application Server's JVM Memory :</b></label>

                        <input type="text" readonly="true" id="appserver_jvmcpustatus" name="appserver_jvmcpustatus" value="Loading..." class="progress-label-jvm" style="text-align: center" size="90">
                            &nbsp;&nbsp; 
                            <br />
                    </p>




                    <p><label for="form1"><b>Application Server's Java Force GC Request :</b></label>
                        <div class="cssreset">
                            <form name="form1" method="post" action="${initParam.PROJECT_ROOT_PATH}/system/control_panel/submitForceGarbageCollection">
                                <input name="clean" class="formbutton" value="Call JVM's GC" type="submit" style="margin-left: 0px" onClick="return confirmSubmit()" />
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            </form>
                        </div>
                    </p>   





                    <p><label for="webServerVersion"><b>Web-Service Server's Version :</b></label>
                        <input name="webServerVersion" type="text" value="${webServerControlPanel.appVersion}" readonly="true" size="80" height="">
                            <br />
                    </p>

                    <p><label for="webServerMacAddress"><b>Web-Service Server's Mac Address :</b></label>
                        <input name="webServerMacAddress" type="text" value="${webServerControlPanel.macAddress}" readonly="true" size="80" height="">
                            <br />
                    </p>

                    <p><label for="webServerHostId"><b>Web-Service Server's Host ID :</b></label>
                        <input name="webServerHostId" type="text" value="${webServerControlPanel.hostId}" readonly="true" size="80">
                            <br />
                    </p>	

                    <p><label for="webServerLicenseExpiryDate"><b>Web-Service Server's License Expiry Date :</b></label>
                        <input name="webServerLicenseExpiryDate" type="text" value="${webServerControlPanel.licenseExpiryDate}" readonly="true" size="80">
                            <br />
                    </p>


                    <p><label for="webserver_progressbarphysicalram"><b>Web-Service Server's Physical Memory :</b></label>
                        <div id="webserver_progressbarphysicalram" style="width: 599px"><div class="webserver_progress-label-physical-ram"  >Loading...</div></div>
                        <input type="hidden" id="webserver_usedphysicalram"/>
                        <input type="hidden" id="webserver_totalphysicalram"/>
                    </p>

                    <p><label for="webserver_progressbarswapram"><b>Web-Service Server's Swap Memory :</b></label>
                        <div id="webserver_progressbarswapram" style="width: 599px"><div class="webserver_progress-label-swap-ram">Loading...</div> </div>
                        <input type="hidden" id="webserver_usedswapram"/>
                        <input type="hidden" id="webserver_totalswapram"/>
                    </p>


                    <p><label for="webserver_jvmcpustatus"><b>Web-Service Server's JVM Memory :</b></label>

                        <input type="text" readonly="true" id="webserver_jvmcpustatus" name="webserver_jvmcpustatus" value="Loading..." class="webserver_progress-label-jvm" style="text-align: center" size="90">
                            &nbsp;&nbsp; 
                            <br />
                    </p>


                    <p><label for="form1"><b>Web-Service Server's Java Force GC Request :</b></label>
                        <div class="cssreset">
                            <form name="form1" method="post" action="${initParam.PROJECT_ROOT_PATH}/system/control_panel/submitForceGarbageCollectionForWebServer">
                                <input name="clean" class="formbutton" value="Call JVM's GC" type="submit" style="margin-left: 0px" onClick="return confirmSubmit()" />
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            </form>
                        </div>
                    </p> 


                    <c:if test="${isAdmin}"> 


                        <p><label for="form1"><b>Reset All Data Request :</b></label>
                            <div class="cssreset">
                                <form name="form1" method="post" action="${initParam.PROJECT_ROOT_PATH}/system/control_panel/reset_data/submit">
                                    <input name="clean" class="formbutton" value="Delete All" type="submit" style="margin-left: 0px" onClick="return confirmSubmit()" />
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                </form>
                            </div>
                        </p> 
                    </c:if>

                </fieldset>







                <!--Div Content End-->
                <br/><br/><br/><br/>
            </div>
            <div class="clear"></div>
        </div>
        <%@ include file="/WEB-INF/jsp/includes/footerSecondLast.jsp" %>    
    </body>
    <%@ include file="/WEB-INF/jsp/includes/footerLast.jsp" %>       