<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <%@ include file="/WEB-INF/jsp/includes/headerFirst_1.jsp" %> 

    <body onload='init();'>
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
                        <h1>Control Panel</h1>
                    </div>
                    <div class="span6">
                        <ul class="breadcrumb pull-right">
                            <li><a href="#">Settings</a> <span class="divider">/</span></li>
                            <li class="active">Control Panel</li>
                        </ul>
                    </div>
                </div>
            </div>
        </section>
        <!-- / .title -->   

        <section class="container">


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


                function confirmContiune()
                {
                    var agree = confirm("Are you sure you want to contiune?");
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







            <script type="text/javascript">


//                function appServerPhysicalMemoryStatustimer() {
//
//                    var appserver_progressbarphysicalram = $("#appserver_progressbarphysicalram");
//                    var appserver_progresslabelphysicalram = $(".progress-label-physical-ram");
//
//                    appserver_progressbarphysicalram.progressbar({
//                        value: false,
//                        change: function () {
//                            if (appserver_progressbarphysicalram.progressbar("value") === false)
//                            {
//                                appserver_progresslabelphysicalram.text("Reloading Data.");
//                            } else
//                            {
//                                appserver_progresslabelphysicalram.text($("#appserver_usedphysicalram").val() + "/" + $("#appserver_totalphysicalram").val() + " (" + appserver_progressbarphysicalram.progressbar("value") + "%)");
//                            }
//
//                        }
//                    });
//                    //            var currentdatetime = jQuery('#appserver_jvmcpustatus');
//                    $.ajax({
//                        type: "GET",
//                        cache: false,
//                        url: '${pageContext.request.contextPath}/system/control_panel/ajaxPhysicalMemoryStatus',
//                        data: "name=",
//                        success: function (data) {
//                            //alert(data);
//                            if (data.indexOf('SamritNarshingAmatya@samrit_narshing@hotmail.com9841249759') >= 0) {
//                                window.location.assign('${pageContext.request.contextPath}/login');
//                            }
//
//                            var dataarray = data.split(';');
//
//                            $("#appserver_usedphysicalram").val(dataarray[1]);
//                            $("#appserver_totalphysicalram").val(dataarray[2]);
//                            // alert('333');
//                            appserver_progressbarphysicalram.progressbar("option", {
//                                //                            value: Math.floor(Math.random() * 100)
//                                value: Math.floor(dataarray[0])
//                            });
//
//                            //                    currentdatetime.val(displaymessage);
//                        },
//                        error: function (e) {
//                            //alert('Error: ' + e);
//                        }
//                    });
//                }
//
//                var intervalId = 0;
//                intervalId = setInterval(appServerPhysicalMemoryStatustimer, 3000);
                
                
                
                
                
                
                
                             function appServerPhysicalMemoryStatustimer() {
                    var currentdatetime = jQuery('#appserver_progressbarphysicalram');

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

                            var displaymessage = 'Free Memory :' + dataarray[0] + 'MB. Used Memory :' + dataarray[1] + '. Total Memory :' + dataarray[2];

                            currentdatetime.val(displaymessage);
                        },
                        error: function (e) {
                            //  alert('Error: ' + e);
                        }
                    });
                }

                var intervalId = 0;
                intervalId = setInterval(appServerPhysicalMemoryStatustimer, 3000);
                
                
                
            </script>

            <script type="text/javascript">

//                function appServerSwapMemoryStatustimer() {

//                    var appserver_progressbarswapram = $("#appserver_progressbarswapram");
//                    var appserver_progresslabelswapram = $(".progress-label-swap-ram");
//
//                    appserver_progressbarswapram.progressbar({
//                        value: false,
//                        change: function () {
//
//                            if (appserver_progressbarswapram.progressbar("value") === false)
//                            {
//                                appserver_progresslabelswapram.text("Reloading Data.");
//                            } else
//                            {
//                                appserver_progresslabelswapram.text($("#appserver_usedswapram").val() + "/" + $("#appserver_totalswapram").val() + " (" + appserver_progressbarswapram.progressbar("value") + "%)");
//                            }
//                        }
//                    });
//                    //            var currentdatetime = jQuery('#appserver_jvmcpustatus');
//                    $.ajax({
//                        type: "GET",
//                        cache: false,
//                        url: '${pageContext.request.contextPath}/system/control_panel/ajaxSwapMemoryStatus',
//                        data: "name=",
//                        success: function (data) {
//                            //alert(data);
//                            if (data.indexOf('SamritNarshingAmatya@samrit_narshing@hotmail.com9841249759') >= 0) {
//                                window.location.assign('${pageContext.request.contextPath}/login');
//                            }
//
//
//                            var dataarray = data.split(';');
//
//                            $("#appserver_usedswapram").val(dataarray[1]);
//                            $("#appserver_totalswapram").val(dataarray[2]);
//
//
//                            appserver_progressbarswapram.progressbar("option", {
//                                //                            value: Math.floor(Math.random() * 100)
//                                value: Math.floor(dataarray[0])
//
//
//                            });
//
//                            //                    currentdatetime.val(displaymessage);
//                        },
//                        error: function (e) {
//                            // alert('Error: ' + e);
//                        }
//                    });
//                }
//
//                var intervalId = 0;
//                intervalId = setInterval(appServerSwapMemoryStatustimer, 3000);





                function appServerSwapMemoryStatustimer() {
                    var currentdatetime = jQuery('#appserver_progressbarswapram');

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

                            var displaymessage = 'Free Memory :' + dataarray[0] + 'MB. Used Memory :' + dataarray[1] + '. Total Memory :' + dataarray[2];

                            currentdatetime.val(displaymessage);
                        },
                        error: function (e) {
                            //  alert('Error: ' + e);
                        }
                    });
                }

                var intervalId = 0;
                intervalId = setInterval(appServerSwapMemoryStatustimer, 3000);
            </script>

            <script type="text/javascript">


//                function webServerPhysicalMemoryStatustimer() {

//                    var webserver_progressbarphysicalram = $("#webserver_progressbarphysicalram");
//                    var webserver_progresslabelphysicalram = $(".webserver_progress-label-physical-ram");
//
//                    webserver_progressbarphysicalram.progressbar({
//                        value: false,
//                        change: function () {
//                            if (webserver_progressbarphysicalram.progressbar("value") === false)
//                            {
//                                webserver_progresslabelphysicalram.text("Reloading Data.");
//                            } else
//                            {
//                                webserver_progresslabelphysicalram.text($("#webserver_usedphysicalram").val() + "/" + $("#webserver_totalphysicalram").val() + " (" + webserver_progressbarphysicalram.progressbar("value") + "%)");
//                            }
//
//                        }
//                    });
//                    $.ajax({
//                        type: "GET",
//                        cache: false,
//                        url: '${webServerDomainURL}/rest/web/info/control_panel/unsecure/ajax_physicalmemory_status',
//                        data: "name=",
//                        success: function (data) {
//                            //alert(data);
//                            if (data.indexOf('SamritNarshingAmatya@samrit_narshing@hotmail.com9841249759') >= 0) {
//                                window.location.assign('${pageContext.request.contextPath}/login');
//                            }
//
//                            var dataarray = data.split(';');
//
//                            $("#webserver_usedphysicalram").val(dataarray[1]);
//                            $("#webserver_totalphysicalram").val(dataarray[2]);
//                            // alert('333');
//                            webserver_progressbarphysicalram.progressbar("option", {
//                                //                            value: Math.floor(Math.random() * 100)
//                                value: Math.floor(dataarray[0])
//                            });
//
//                            //                    currentdatetime.val(displaymessage);
//                        },
//                        error: function (e) {
//                            //alert('Error: ' + e);
//                        }
//                    });
//                }
//
//                var intervalId = 0;
//                intervalId = setInterval(webServerPhysicalMemoryStatustimer, 3000);
                
                
                
                
                
                
                  function webServerPhysicalMemoryStatustimer() {
                    var currentdatetime = jQuery('#webserver_progressbarphysicalram');

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

                            var displaymessage = 'Free Memory :' + dataarray[0] + 'MB. Used Memory :' + dataarray[1] + '. Total Memory :' + dataarray[2];

                            currentdatetime.val(displaymessage);
                        },
                        error: function (e) {
                            //  alert('Error: ' + e);
                        }
                    });
                }

                var intervalId = 0;
                intervalId = setInterval(webServerPhysicalMemoryStatustimer, 3000);
                
                
                
                
            </script>



            <script type="text/javascript">

//                function webServerSwapMemoryStatustimer() {
//
//                    var webserver_progressbarswapram = $("#webserver_progressbarswapram");
//                    var webserver_progresslabelswapram = $(".webserver_progress-label-swap-ram");
//
//                    webserver_progressbarswapram.progressbar({
//                        value: false,
//                        change: function () {
//
//                            if (webserver_progressbarswapram.progressbar("value") === false)
//                            {
//                                webserver_progresslabelswapram.text("Reloading Data.");
//                            } else
//                            {
//                                webserver_progresslabelswapram.text($("#webserver_usedswapram").val() + "/" + $("#webserver_totalswapram").val() + " (" + webserver_progressbarswapram.progressbar("value") + "%)");
//                            }
//                        }
//                    });
//                    //            var currentdatetime = jQuery('#appserver_jvmcpustatus');
//                    $.ajax({
//                        type: "GET",
//                        cache: false,
//                        url: '${webServerDomainURL}/rest/web/info/control_panel/unsecure/ajax_swapmemory_status',
//                        data: "name=",
//                        success: function (data) {
//                            //alert(data);
//                            if (data.indexOf('SamritNarshingAmatya@samrit_narshing@hotmail.com9841249759') >= 0) {
//                                window.location.assign('${pageContext.request.contextPath}/login');
//                            }
//
//
//                            var dataarray = data.split(';');
//
//                            $("#webserver_usedswapram").val(dataarray[1]);
//                            $("#webserver_totalswapram").val(dataarray[2]);
//
//
//                            webserver_progressbarswapram.progressbar("option", {
//                                //                            value: Math.floor(Math.random() * 100)
//                                value: Math.floor(dataarray[0])
//
//
//                            });
//
//                            //                    currentdatetime.val(displaymessage);
//                        },
//                        error: function (e) {
//                            // alert('Error: ' + e);
//                        }
//                    });
//                }
//
//                var intervalId = 0;
//                intervalId = setInterval(webServerSwapMemoryStatustimer, 3000);
                
                
                
                
                
                
                      function webServerSwapMemoryStatustimer() {
                    var currentdatetime = jQuery('#webserver_progressbarswapram');

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

                            var displaymessage = 'Free Memory :' + dataarray[0] + 'MB. Used Memory :' + dataarray[1] + '. Total Memory :' + dataarray[2];

                            currentdatetime.val(displaymessage);
                        },
                        error: function (e) {
                            //  alert('Error: ' + e);
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




            <div class="row-fluid" style="width: 100%">

                <h3>Control Panel</h3>

                <div class="span12" >

                    <!--Div Content Start-->

                    <c:if test="${not empty pageMessage}">
                        <div class="status alert alert-success" style="display: block">${pageMessage}</div>
                    </c:if>

                    <c:if test="${not empty pageError}">
                        <div class="status alert alert-error" style="display: block">${pageError}</div>

                    </c:if>

                    <br/>

                </div>

                <div class="span12">    
                    <br>





                    <table>

                        <tr>

                            <td style="width: 30%">
                                <label for="username"><b>Username :</b></label>
                            </td>
                            <td>
                                <input name="username" type="text" value="${webServerControlPanel.username}" readonly="true" size="80" height="">
                            </td>
                        </tr>

                        <tr>

                            <td>
                                <label for="userLicenseExpiryDate"><b>User's License Expiry Date :</b></label>

                            </td>
                            <td>
                                <input name="userLicenseExpiryDate" type="text" value="${webServerControlPanel.userLicenseExpiryDate}" readonly="true" size="80" height="">
                            </td>
                        </tr>

                        <tr>

                            <td>
                                <label for="appServerVersion"><b>Application Server's Version :</b></label>
                            </td>
                            <td>
                                <input name="appServerVersion" type="text" value="${appServerControlPanel.appVersion}" readonly="true" size="80" height="">
                            </td>
                        </tr>

                        <tr>

                            <td>
                                <label for="appServerMacAddress"><b>Application Server's Mac Address :</b></label>

                            </td>
                            <td>
                                <input name="appServerMacAddress" type="text" value="${appServerControlPanel.macAddress}" readonly="true" size="80" height="">
                            </td>
                        </tr>

                        <tr>

                            <td>
                                <label for="appServerHostId"><b>Application Server's Host ID :</b></label>

                            </td>
                            <td>
                                <input name="appServerHostId" type="text" value="${appServerControlPanel.hostId}" readonly="true" size="80">
                            </td>
                        </tr>

                        <tr>

                            <td>
                                <label for="appServerLicenseExpiryDate"><b>Application Server's License Expiry Date :</b></label>

                            </td>
                            <td>
                                <input name="appServerLicenseExpiryDate" type="text" value="${appServerControlPanel.licenseExpiryDate}" readonly="true" size="80">
                            </td>
                        </tr>

                        <tr>

                            <td>
                                <label for="appserver_progressbarphysicalram"><b>Application Server's Physical Memory :</b></label>

                            </td>
                            <td>
                                
                                 <input type="text" readonly="true" id="appserver_progressbarphysicalram" name="appserver_progressbarphysicalram" value="Loading..." class="progress-label-jvm" style="text-align: center;width:60%" size="390">

                                
<!--                                <div id="appserver_progressbarphysicalram" style="width: 599px"><div class="progress-label-physical-ram"  >Loading...</div></div>
                                <input type="text" id="appserver_usedphysicalram"/>
                                <input type="text" id="appserver_totalphysicalram"/>-->
                            </td>
                        </tr>

                        <tr>

                            <td>
                                <label for="appserver_progressbarswapram"><b>Application Server's Swap Memory :</b></label>

                            </td>
                            <td>
                                <input type="text" readonly="true" id="appserver_progressbarswapram" name="appserver_progressbarswapram" value="Loading..." class="progress-label-jvm" style="text-align: center;width:60%" size="390">

                                <!--                                <div id="appserver_progressbarswapram" style="width: 599px"><div class="progress-label-swap-ram">Loading...</div> </div>
                                                                <input type="hidden" id="appserver_usedswapram"/>
                                                                <input type="hidden" id="appserver_totalswapram"/>-->
                            </td>
                        </tr>

                        <tr>

                            <td>
                                <label for="appserver_jvmcpustatus"><b>Application Server's JVM Memory :</b></label>


                            </td>
                            <td>
                                <input type="text" readonly="true" id="appserver_jvmcpustatus" name="appserver_jvmcpustatus" value="Loading..." class="progress-label-jvm" style="text-align: center;width:60%" size="390">

                            </td>
                        </tr>

                        <tr>

                            <td>
                                <label for="form1"><b>Application Server's Java Force GC Request :</b></label>


                            </td>
                            <td> 
                                <form name="form1" method="post" action="${initParam.PROJECT_ROOT_PATH}/system/control_panel_1/submitForceGarbageCollection">
                                    <!--<input name="clean" class="formbutton" value="Call JVM's GC" type="submit" style="margin-left: 0px" onClick="return confirmSubmit()" />-->

                                    <button  style="margin:12px" name="clean" type="submit" class="btn btn-primary btn-large pull-left" onClick="return confirmSubmit()">Call JVM's GC</button>
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                </form>
                            </td>
                        </tr>





                        <tr>

                            <td>
                                <label for="webServerVersion"><b>Web-Service Server's Version :</b></label>

                            </td>
                            <td>
                                <input name="webServerVersion" type="text" value="${webServerControlPanel.appVersion}" readonly="true" size="80" height="">

                            </td>
                        </tr>


                        <tr>

                            <td>
                                <label for="webServerMacAddress"><b>Web-Service Server's Mac Address :</b></label>

                            </td>
                            <td>
                                <input name="webServerMacAddress" type="text" value="${webServerControlPanel.macAddress}" readonly="true" size="80" height="">
                            </td>
                        </tr>


                        <tr>

                            <td>
                                <label for="webServerHostId"><b>Web-Service Server's Host ID :</b></label>

                            </td>
                            <td>
                                <input name="webServerHostId" type="text" value="${webServerControlPanel.hostId}" readonly="true" size="80">
                            </td>
                        </tr>


                        <tr>

                            <td>
                                <label for="webServerLicenseExpiryDate"><b>Web-Service Server's License Expiry Date :</b></label>

                            </td>
                            <td>
                                <input name="webServerLicenseExpiryDate" type="text" value="${webServerControlPanel.licenseExpiryDate}" readonly="true" size="80">
                            </td>
                        </tr>


                        <tr>

                            <td>
                                <label for="webserver_progressbarphysicalram"><b>Web-Service Server's Physical Memory :</b></label>

                            </td>
                            <td>
                                
                                  <input type="text" readonly="true" id="webserver_progressbarphysicalram" name="webserver_progressbarphysicalram" value="Loading..." class="progress-label-jvm" style="text-align: center;width:60%" size="390">

                                  
<!--                                <div id="webserver_progressbarphysicalram" style="width: 599px"><div class="webserver_progress-label-physical-ram"  >Loading...</div></div>
                                <input type="hidden" id="webserver_usedphysicalram"/>
                                <input type="hidden" id="webserver_totalphysicalram"/>-->
                            </td>
                        </tr>

                        <tr>

                            <td>
                                <label for="webserver_progressbarswapram"><b>Web-Service Server's Swap Memory :</b></label>

                            </td>
                            <td>
                                 <input type="text" readonly="true" id="webserver_progressbarswapram" name="webserver_progressbarswapram" value="Loading..." class="progress-label-jvm" style="text-align: center;width:60%" size="390">

<!--                                <div id="webserver_progressbarswapram" style="width: 599px"><div class="webserver_progress-label-swap-ram">Loading...</div> </div>
                                <input type="hidden" id="webserver_usedswapram"/>
                                <input type="hidden" id="webserver_totalswapram"/>-->
                            </td>
                        </tr>


                        <tr>

                            <td>
                                <label for="webserver_jvmcpustatus"><b>Web-Service Server's JVM Memory :</b></label>



                            </td>
                            <td>
                                <input type="text" readonly="true" id="webserver_jvmcpustatus" name="webserver_jvmcpustatus" value="Loading..." class="webserver_progress-label-jvm" style="text-align: center;width:60%" size="90">

                            </td>
                        </tr>

                        <tr>

                            <td>
                                <label for="form1"><b>Web-Service Server's Java Force GC Request :</b></label>

                            </td>
                            <td>
                                <div class="cssreset">
                                    <form name="form1" method="post" action="${initParam.PROJECT_ROOT_PATH}/system/control_panel_1/submitForceGarbageCollectionForWebServer">


                                        <button  style="margin:12px" name="clean" type="submit" class="btn btn-primary btn-large pull-left" onClick="return confirmSubmit()">Call JVM's GC</button>
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                    </form>
                                </div>
                            </td>
                        </tr>
                    </table>









                </div>         





            </div>
            <hr>

        </section>




        <%@ include file="/WEB-INF/jsp/includes/footerSecondLast_1.jsp" %>    

    </body>
    <%@ include file="/WEB-INF/jsp/includes/footerLast_1.jsp" %>      
