<%@ include file="/WEB-INF/jsp/includes/tagLibs.jsp" %> 
<%@page session="true"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <%@ include file="/WEB-INF/jsp/includes/headerFirst.jsp" %> 
    <body class="noheader" onload='document.loginForm.username.focus();'>
        <%@ include file="/WEB-INF/jsp/includes/headerSecond.jsp" %> 
        <div id="body">
            <div id="breadcrumbs">
                <span>You are here:</span>
                <a href="${initParam.PROJECT_ROOT_PATH}/home">Home</a> &raquo;
                <a href="#">User Management</a> &raquo;
                <a href="${initParam.PROJECT_ROOT_PATH}/userman/listUser/1/0"> List Users</a> &raquo;
                <strong>Edit User</strong>
            </div>
            <div id="content">
                <!--Div Content Start-->

                <div class="box">
                    <h2>Examples</h2>

                    <h1>Heading H1</h1>
                    <h2>Heading H2</h2>
                    <h3>Heading H3</h3>
                    <h4>Heading H4</h4>

                    <h5>Heading H5</h5>
                    <p>&nbsp;</p>


                    <h3>Lists</h3>
                    <ul>
                        <li>List item</li>
                        <li>List item</li>
                        <li>List item</li>
                    </ul>

                    <ol>
                        <li>List item</li>
                        <li>List item</li>
                        <li>List item</li>
                    </ol>

                    <p>&nbsp;</p>


                    <h3>Code and blockquote</h3>
                    <code>&lt;? echo('Hello world'); ?&gt;</code>

                    <blockquote><p>Mauris sit amet tortor in urna tincidunt aliquam. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas</p></blockquote>
                    <p>&nbsp;</p>



                    <h3>Table</h3>
                    <table>
                        <tbody>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Age</th>
                            </tr>
                            <tr>
                                <td>1</td>
                                <td>John Smith</td>
                                <td>28</td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>Fred James</td>
                                <td>49</td>
                            </tr>
                            <tr>
                                <td>3</td>
                                <td>Rachel Johnson</td>
                                <td>19</td>
                            </tr>
                        </tbody>

                    </table>
                    <p>&nbsp;</p>

                    <h3>Form</h3>
                    <fieldset>
                        <legend>Form legend</legend>
                        <form action="#" method="get">
                            <p><label for="name">Name:</label>
                                <input name="name" id="name" value="" type="text" /><br /></p>		
                            <p><label for="email">Email:</label>
                                <input name="email" id="email" value="" type="text" /><br /></p>
                            <p><label for="message">Message:</label>	
                                <textarea cols="60" rows="11" name="message" id="message"></textarea><br /></p>
                            <p><input name="send" class="formbutton" value="Send" type="submit" /></p>
                        </form>
                    </fieldset>

                </div>
            </div>

            <div class="sidebar">
                <ul>	
                    <li>
                        <h4><span>Navigate</span></h4>
                        <ul class="blocklist">
                            <li><a href="index.html">Home</a></li>
                            <li><a href="examples.html">Examples</a></li>
                            <li><a href="#">Products</a></li>
                            <li><a href="#">Solutions</a></li>
                            <li><a href="#">Contact</a></li>
                        </ul>
                    </li>

                    <li>
                        <h4><span>About</span></h4>
                        <ul>
                            <li>
                                <p style="margin: 0;">Aenean nec massa a tortor auctor sodales sed a dolor. Duis vitae lorem sem. Proin at velit vel arcu pretium luctus.</p>
                            </li>
                        </ul>
                    </li>

                    <li>
                        <h4 class="h4-green"><span>Cool Sites</span></h4>
                        <ul>
                            <li><a href="http://www.themeforest.net/?ref=spykawg" title="premium templates"><strong>ThemeForest</strong></a> - premium HTML templates, WordPress themes and PHP scripts</li>
                            <li><a href="http://www.dreamhost.com/r.cgi?259541" title="web hosting"><strong>Web hosting</strong></a> - 50 dollars off when you use promocode <strong>awesome50</strong></li>
                            <li><a href="http://www.4templates.com/?aff=spykawg" title="4templates"><strong>4templates</strong></a> - brilliant premium templates</li>
                        </ul>
                    </li>

                </ul> 
            </div>
            <div class="clear"></div>
            <!--Div Content End-->
            <br/><br/><br/><br/>
        </div>
        <div class="clear"></div>
        </div>
        <%@ include file="/WEB-INF/jsp/includes/footerSecondLast.jsp" %>    
    </body>
    <%@ include file="/WEB-INF/jsp/includes/footerLast.jsp" %>     