<%-- 
    Document   : relevance
    Created on : 23 Jul, 2016, 2:20:34 PM
    Author     : AYANAVA
--%>

<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="servelet.Jave_serv" %>
<%@page import="retrievability.UserQueryIndex" %>
<%@page import="retrievability.Pagination" %>

<!DOCTYPE html>
<html>
    <head>
        <style>
            body {background-color: powderblue;}
            h1   {  color: blue;
                    font-family: verdana;
                    font-size: 200%;
                    alignment-adjust: center
            }
            p    {  color: red;
                    font-family: courier;
                    font-size: 160%;}
</style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Relevant Documents</title>
    </head>
    <body>
        <h1>  <b>  Relevant Documents selected by the user </b> </h1>
        
        <%
            HashMap <Integer, String> selected_docs=new HashMap<Integer, String>();
            
            selected_docs=Pagination.get_relevant_docs();
            
            out.println("<form name='form1' method='post' action='frames.jsp'  >");
            out.println("<div align='center'>");
            out.println("<table>");
            
            for (int i = 0; i <selected_docs.size(); i++) 
                {
                    out.println("<br/> ");
                    out.println("<div align='center'>");
                    out.println("<br/> ");
                    
                    out.println ("<b>"+selected_docs.get(i)+"<b/>");
                    out.println();
                }
            
                
            out.println("<p>"+"<INPUT TYPE='SUBMIT' NAME='SUMMARY' VALUE='Go To SUMMARY'>"+"</p>");
            out.println("<table/>");
            out.println("<form/>");
            
            
            
        
        
            
        %>
    </body>
</html>
