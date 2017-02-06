<%-- 
    Document   : index_start
    Created on : 12 Jul, 2016, 1:25:01 PM
    Author     : AYANAVA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>QUERY MATCHER</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!--link href="http://fonts.googleapis.com/css?family=Open+Sans&subset=latin,cyrillic" rel="stylesheet"
              type="text/css"-->
        <!--link href="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
        <link href='https://fonts.googleapis.com/css?family=Architects+Daughter' rel='stylesheet' type='text/css'-->
        <!--link rel="stylesheet" type="text/css" href="css/stylesheet.css" media="screen"/>
        <link rel="stylesheet" type="text/css" href="css/pygment_trac.css" media="screen"/>
        <link rel="stylesheet" type="text/css" href="css/print.css" media="print"-->
        <link rel="stylesheet" href="css/uilayout.css" />
        <link rel="stylesheet" href="css/displayres.css" />
        <link href="jquery/themes/ui-lightness/jquery-ui.css" rel="stylesheet">

        <script src="jquery/jquery-2.1.4.js"></script>
        <script src="jquery/jquery-ui.js"></script>
	<script type="text/javascript" src="jquery/jquery.layout.js"></script>
    
    </head>
    <body>
    <div class="ui-layout-west">
    <center>
        <table>
        <tr>
        <td>
        <img src="images/detective.png" alt="ADAPT Centre, DCU"
             border="0" style="max-width: 100px; max-height:100px;">
        </td>
        <td>
        <h2> QUERY MATCHER </h2>
        </td>
        
        </tr>
        </table>
    </center>
    </div>
        
        <form method="post" action="login.jsp">
            <center>
            <table border="1" width="30%" cellpadding="3">
                <thead>
                    <tr>
                        <th colspan="2">Login Here</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>User Name</td>
                        <td><input type="text" name="uname" value="" /></td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td><input type="password" name="pass" value="" /></td>
                    </tr>
                    <tr>
                        <td><input type="submit" value="Login" /></td>
                        <td><input type="reset" value="Reset" /></td>
                    </tr>
                    <tr>
                        <td colspan="2">Not Registered!! <a href="reg.jsp">Register Here</a></td>
                    </tr>
                </tbody>
            </table>
            </center>
        </form>
    
    </body>
    
    
    
</html>
