<%-- 
    Document   : try_rel_docs
    Created on : 12 Aug, 2016, 12:30:33 PM
    Author     : AYANAVA
--%>

<%@page import="retrievability.Pagination"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Display selected Relevant Docs</title>
    </head>
    <body>
        
        <%
            HashMap <Integer, String> relevant_docs=new HashMap<Integer, String>();
            
            out.println("<form name='form1' method='post' action='try_rel_docs.jsp'  >");
            relevant_docs=Pagination.get_relevant_docs();
                
            System.out.println("Size of the rel docs in the second frame---"+relevant_docs.size());
                out.println("Relevant selected documents are-----");
                out.println("<table>");
                out.println("<tr>");
                for (int i = 0; i <relevant_docs.size(); i++) 
                {
                    out.println("<br/> ");
                    out.println("<div align='center'>");
                    out.println("<br/> ");
                    
                    out.println (relevant_docs.get(i));
                    out.println();
                }
                out.println("<table/>");
                out.println("<form/>");
        
        %>
    </body>
</html>
