<%-- 
    Document   : login
    Created on : 12 Jul, 2016, 1:40:18 PM
    Author     : AYANAVA
--%>

<%@ page import ="java.sql.*" %>

<%
    String userid = request.getParameter("uname");    
    String pwd = request.getParameter("pass");
    Class.forName("com.ibm.db2.jcc.DB2Driver");
    Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/User_Information",
            "root", "root");
    Statement st = con.createStatement();
    ResultSet rs;
    rs = st.executeQuery("select * from User_Information where user_id='" + userid + "' and pwd='" + pwd + "'");
    if (rs.next()) {
        session.setAttribute("user_id", userid);
        //out.println("welcome " + userid);
        //out.println("<a href='logout.jsp'>Log out</a>");
        response.sendRedirect("success.jsp");
    } else {
        out.println("Invalid password <a href='index_start.jsp'> Please try again </a>");
    }
%>
