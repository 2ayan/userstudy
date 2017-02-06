<%-- 
    Document   : search
    Created on : 30-Nov-2015, 23:39:51
    Author     : Debasis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="http://fonts.googleapis.com/css?family=Open+Sans&subset=latin,cyrillic" rel="stylesheet"
              type="text/css">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href='https://fonts.googleapis.com/css?family=Architects+Daughter' rel='stylesheet' type='text/css'>
        
        <!--link rel="stylesheet" type="text/css" href="css/stylesheet.css" media="screen"/-->
        <!--link rel="stylesheet" type="text/css" href="css/pygment_trac.css" media="screen"/-->
        <!--link rel="stylesheet" type="text/css" href="css/print.css" media="print"/-->
        
        <link href="jquery/themes/ui-lightness/jquery-ui.css" rel="stylesheet">

        <script src="jquery/jquery-2.1.4.js"></script>
        <script src="jquery/jquery-ui.js"></script>
        <script src="jquery/spin.js"></script>
        <script src="jquery/jquery.twbsPagination.js" type="text/javascript"></script>
        
        <script>
            var opts = {
              lines: 30 // The number of lines to draw
            , length: 28 // The length of each line
            , width: 14 // The line thickness
            , radius: 42 // The radius of the inner circle
            , scale: 1 // Scales overall size of the spinner
            , corners: 1 // Corner roundness (0..1)
            , color: '#000' // #rgb or #rrggbb or array of colors
            , opacity: 0.25 // Opacity of the lines
            , rotate: 0 // The rotation offset
            , direction: 1 // 1: clockwise, -1: counterclockwise
            , speed: 1 // Rounds per second
            , trail: 60 // Afterglow percentage
            , fps: 20 // Frames per second when using setTimeout() as a fallback for CSS
            , zIndex: 2e9 // The z-index (defaults to 2000000000)
            , className: 'spinner' // The CSS class to assign to the spinner
            , top: '50%' // Top position relative to parent
            , left: '50%' // Left position relative to parent
            , shadow: false // Whether to render a shadow
            , hwaccel: false // Whether to use hardware acceleration
            , position: 'absolute' // Element positioning
            };
            
        </script>
    </head>
    <body>
        <center>
        <br>    
        <div  id="containerdiv">
            
            <form method ="post" action="Jave_serv">
            <input type="text" id="query" name="query" size="50">
               
            
            <button id="srchBttn">Search</button>
            </form>
        </div>
            
        </center>
    
        
    
        <!-- Pagination component -->
        <div align="center">
            <ul id="serp" class="pagination-sm"></ul>
        </div>
    
        <!-- Document Viewer dialog box -->
        <div id="documentSubmitDlg" title="Submit this Document?">
        </div>        
        
    </body>
</html>
