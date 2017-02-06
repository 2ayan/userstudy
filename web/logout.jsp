<%-- 
    Document   : logout
    Created on : 12 Jul, 2016, 1:41:34 PM
    Author     : AYANAVA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<a href='logout.jsp'>Log out</a>
<%
session.setAttribute("user_id", null);
session.invalidate();

response.sendRedirect("index_start.jsp");
%>