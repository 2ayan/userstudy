<%-- 
    Document   : dynamic
    Created on : 29 Jul, 2016, 2:40:00 PM
    Author     : AYANAVA
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="servelet.Serv2 " %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>dynamic</title>
    </head>
    <body>
         <%
             int[]temp=Serv2.pagination();
             out.println(temp);
            
            
        
        %>
        
         <div class="menu">
   <button onclick="myFunction()">Reload page</button>
   <script>
        function myFunction() {
        location.reload();
        
    }
</script>
  </div>
        
  
    
       
        
    </body>
</html>
