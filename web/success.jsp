<%-- 
    Document   : success
    Created on : 12 Jul, 2016, 1:41:06 PM
    Author     : AYANAVA
--%>

<%
    if ((session.getAttribute("user_id") == null) || (session.getAttribute("user_id") == "")) {
%>
<br>You are not logged in<br/>
<a href="index_start.jsp">Please Login</a>
<%} else {
%>
Welcome <%=session.getAttribute("user_id")%>
<a href='logout.jsp'>Log out</a>
<%response.sendRedirect("index.jsp");%>
<%
    }
%>
