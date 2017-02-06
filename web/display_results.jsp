<%-- 
    Document   : display_results
    Created on : 29 Jul, 2016, 5:05:08 PM
    Author     : AYANAVA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="retrievability.UserQueryIndex" %>
<%@page import="retrievability.Pagination" %>
<%@ page import="java.util.*" %>
<%@ page import="servelet.Jave_serv" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Display Results</title>
    </head>
    <body>
        <% 
            HashMap <Integer, String> hashed_values=new HashMap<Integer, String>();
            hashed_values=Jave_serv.copytofile();
            Pagination.getHashMap(hashed_values);
            
            String user_id=(session.getAttribute("user_id")).toString();
            String query=Jave_serv.query_return();
            UserQueryIndex.indexing(query,user_id);

            %>
            
            <a href='div.jsp'> Display The Results </a>
</html>
