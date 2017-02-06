<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> QUERY MATCHER </title>
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
    
    <script
        var myLayout;

            $(document).ready(function () {

                myLayout = $('body').layout({
                        west__size:			500 /* use this to re-size the west pane */
                ,	west__spacing_closed:		20
                ,	west__togglerLength_closed:	100
                ,	west__togglerAlign_closed:	"top"
                ,	west__togglerContent_closed:"M<BR>E<BR>N<BR>U"
                ,	west__togglerTip_closed:	"Open & Pin Menu"
                ,	west__sliderTip:			"Slide Open Menu"
                ,	west__slideTrigger_open:	"mouseover"
                ,	center__maskContents:		true // IMPORTANT - enable iframe masking
                });

                $("#accordion").accordion({
                        heightStyle: "content"
                    }
                );

                
            });
            //---End: Layout code
            
              
        </script>
        
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
        
 <FRAMESET cols="20%, 80%">
  <FRAMESET rows="100, 200">
  
     
      <iframe id="mainFrame" name="mainFrame" class="ui-layout-center"
              width="100%" height="600" frameborder="0" spellcheck="true"
        src="search.jsp">            
</iframe>
      
      
      
    </frameset>
     
     <a href='logout.jsp'> Log out </a>

 </frameset>
</body>
</html>