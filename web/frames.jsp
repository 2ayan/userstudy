<%-- 
    Document   : frames
    Created on : 8 Aug, 2016, 3:18:04 PM
    Author     : AYANAVA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Frames</title>
    </head>
    
    <%
        //out.println("<form name='form1' method='post' action='index.jsp'  >");
        %>
  
        
        <frameset cols="70%,*">
    
        <frameset rows="80%,*">
            <frame src="summary.jsp" name="frame-a1" scrolling="OFF">
                
            </frame>
            <frame src="" name="frame-a2" scrolling="OFF">
                 
            </frame>
           
        </frameset>
    
    <frame src="final_relevance.jsp"/>
    
  </frameset>
        
        <%
  //out.println("<p>"+"<INPUT TYPE='SUBMIT' NAME='GO To Search' VALUE='GO To Search'>"+"</p>");  
  //out.println("<form/>");
  
  %>
  
</html>
